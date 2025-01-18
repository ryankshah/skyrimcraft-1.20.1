package com.ryankshah.skyrimcraft.network.spell;

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

public record ReplenishMagicka(float amount)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "replenishmagicka");

    public ReplenishMagicka(final FriendlyByteBuf buffer) {
        this(buffer.readFloat());
    }

    public static ReplenishMagicka decode(FriendlyByteBuf buf) {
        return new ReplenishMagicka(buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(amount);
    }

    public static void handle(PacketContext<ReplenishMagicka> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<ReplenishMagicka> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);

        float newMagicka = character.getMagicka() + character.getMaxMagicka();
        newMagicka = Math.min(newMagicka, character.getMaxMagicka());
        character.setMagicka(newMagicka);

        final ReplenishMagicka sendToClient = new ReplenishMagicka(newMagicka);
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<ReplenishMagicka> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            character.setMagicka(context.message().amount);
        });
    }
}