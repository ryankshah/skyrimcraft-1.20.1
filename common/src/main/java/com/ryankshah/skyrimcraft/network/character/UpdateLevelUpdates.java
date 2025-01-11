package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdateLevelUpdates(LevelUpdates levelUpdates)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updatelevelupdates");

    public UpdateLevelUpdates(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(LevelUpdates.CODEC));
    }

    public static UpdateLevelUpdates decode(FriendlyByteBuf buf) {
        return new UpdateLevelUpdates(buf.readJsonWithCodec(LevelUpdates.CODEC));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(LevelUpdates.CODEC, levelUpdates);
    }

    public static void handle(PacketContext<UpdateLevelUpdates> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateLevelUpdates> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setLevelUpdates(player, context.message().levelUpdates);

        final UpdateLevelUpdates sendToClient = new UpdateLevelUpdates(context.message().levelUpdates);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateLevelUpdates> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setLevelUpdates(minecraft.player, context.message().levelUpdates);
        });
    }
}