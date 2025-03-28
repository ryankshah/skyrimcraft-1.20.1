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

public record AddToTargetingEntities(int entityId)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "addtotargetingentities");

    public AddToTargetingEntities(final FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public static AddToTargetingEntities decode(FriendlyByteBuf buf) {
        return new AddToTargetingEntities(buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
    }

    public static void handle(PacketContext<AddToTargetingEntities> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<AddToTargetingEntities> context) {
        ServerPlayer player = (ServerPlayer) context.sender();
        Character character = Character.get(player);

        character.addTarget(context.message().entityId);

        final AddToTargetingEntities sendToClient = new AddToTargetingEntities(context.message().entityId);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<AddToTargetingEntities> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            character.addTarget(context.message().entityId);
        });
    }
}

