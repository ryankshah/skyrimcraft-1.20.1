package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.PlayerQuests;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdatePlayerQuests(PlayerQuests quests)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updateplayerquests");

    public UpdatePlayerQuests(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(PlayerQuests.CODEC));
    }

    public static UpdatePlayerQuests decode(FriendlyByteBuf buf) {
        return new UpdatePlayerQuests(buf.readJsonWithCodec(PlayerQuests.CODEC));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(PlayerQuests.CODEC, quests);
    }

    public static void handle(PacketContext<UpdatePlayerQuests> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdatePlayerQuests> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setQuestData(player, context.message().quests);
        final UpdatePlayerQuests sendToClient = new UpdatePlayerQuests(Services.PLATFORM.getQuests(player));
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdatePlayerQuests> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setQuestData(minecraft.player, context.message().quests);
        });
    }
}