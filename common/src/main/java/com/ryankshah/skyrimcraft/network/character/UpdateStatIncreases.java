package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.AttributeRegistry;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

public record UpdateStatIncreases(StatIncreases statIncreases)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updatestatincreases");

    public UpdateStatIncreases(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(StatIncreases.CODEC));
    }

    public static UpdateStatIncreases decode(FriendlyByteBuf buf) {
        return new UpdateStatIncreases(buf.readJsonWithCodec(StatIncreases.CODEC));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(StatIncreases.CODEC, statIncreases);
    }

    public static void handle(PacketContext<UpdateStatIncreases> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateStatIncreases> context) {
        ServerPlayer player = context.sender();
        UpdateStatIncreases data = context.message();

        StatIncreases oldStats = Services.PLATFORM.getStatIncreases(player); // player.getData(PlayerAttachments.STAT_INCREASES);
        if(data.statIncreases.getHealthIncrease() > oldStats.getHealthIncrease()) {
            AttributeRegistry.setMaxHealth(player, data.statIncreases.getHealthIncrease(), AttributeModifier.Operation.MULTIPLY_TOTAL); // TODO: Verify add_multiplied_total is same as multiply_total
        } else if(data.statIncreases.getMagickaIncrease() > oldStats.getMagickaIncrease()) {
            AttributeRegistry.setMaxMagicka(player, data.statIncreases.getMagickaIncrease(), AttributeModifier.Operation.MULTIPLY_TOTAL);
        }

        Services.PLATFORM.setStatIncreases(player, data.statIncreases);
        Services.PLATFORM.setLevelUpdates(player, new LevelUpdates(Services.PLATFORM.getLevelUpdates(player).getLevelUpdates()-1));

        final UpdateLevelUpdates levelUpdates = new UpdateLevelUpdates(new LevelUpdates(Services.PLATFORM.getLevelUpdates(player).getLevelUpdates()-1));
        Dispatcher.sendToClient(levelUpdates, player);
//        PacketDistributor.PLAYER.with(player).send(levelUpdates);

        final UpdateStatIncreases sendToClient = new UpdateStatIncreases(Services.PLATFORM.getStatIncreases(player));
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateStatIncreases> context) {
        Minecraft minecraft = Minecraft.getInstance();
        UpdateStatIncreases data = context.message();
        minecraft.execute(() -> {
            Player player = minecraft.player;

            StatIncreases oldStats = Services.PLATFORM.getStatIncreases(player);
            if(data.statIncreases.getHealthIncrease() > oldStats.getHealthIncrease()) {
                AttributeRegistry.setMaxHealth(player, data.statIncreases.getHealthIncrease(), AttributeModifier.Operation.MULTIPLY_TOTAL);
            } else if(data.statIncreases.getMagickaIncrease() > oldStats.getMagickaIncrease()) {
                AttributeRegistry.setMaxMagicka(player, data.statIncreases.getMagickaIncrease(), AttributeModifier.Operation.MULTIPLY_TOTAL);
            }

            Services.PLATFORM.setStatIncreases(player, data.statIncreases); //.setData(PlayerAttachments.STAT_INCREASES, data.statIncreases);
        });
    }
}