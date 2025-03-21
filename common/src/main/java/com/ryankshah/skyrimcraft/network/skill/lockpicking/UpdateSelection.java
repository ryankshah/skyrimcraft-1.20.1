package com.ryankshah.skyrimcraft.network.skill.lockpicking;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdateSelection(Selection selection)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updateselection");

    public UpdateSelection(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(Selection.CODEC));
    }

    public static UpdateSelection decode(FriendlyByteBuf buf) {
        return new UpdateSelection(buf.readJsonWithCodec(Selection.CODEC));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(Selection.CODEC, selection);
    }

    public static void handle(PacketContext<UpdateSelection> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateSelection> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setSelection(player, context.message().selection);
        final UpdateSelection sendToClient = new UpdateSelection(Services.PLATFORM.getSelection(player));
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateSelection> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setSelection(minecraft.player, context.message().selection);
        });
    }
}