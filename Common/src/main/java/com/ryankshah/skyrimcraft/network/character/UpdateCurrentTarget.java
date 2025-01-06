package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record UpdateCurrentTarget(int target)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updatecurrenttarget");

    public UpdateCurrentTarget(final FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public static UpdateCurrentTarget decode(FriendlyByteBuf buf) {
        return new UpdateCurrentTarget(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(target);
    }

    public static void handle(PacketContext<UpdateCurrentTarget> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateCurrentTarget> context) {
        ServerPlayer player = (ServerPlayer) context.sender();
        Character character = Character.get(player);

        character.addTarget(context.message().target);

        final UpdateCurrentTarget sendToClient = new UpdateCurrentTarget(context.message().target);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateCurrentTarget> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);

            character.addTarget(context.message().target);
        });
    }
}