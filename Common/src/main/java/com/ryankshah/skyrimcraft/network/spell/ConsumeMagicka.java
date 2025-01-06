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

public record ConsumeMagicka(float amount)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "consumemagicka");

    public static ConsumeMagicka decode(FriendlyByteBuf buf) {
        return new ConsumeMagicka(buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(amount);
    }

    public ConsumeMagicka(final FriendlyByteBuf buffer) {
        this(buffer.readFloat());
    }

    public static void handle(PacketContext<ConsumeMagicka> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<ConsumeMagicka> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);
        ConsumeMagicka data = context.message();

        float currentMagicka = character.getMagicka();
        float newMagicka = Math.max(0, currentMagicka - data.amount);
        character.setMagicka(newMagicka);

        final ConsumeMagicka sendToClient = new ConsumeMagicka(newMagicka);
        Dispatcher.sendToClient(sendToClient, player);
    }

    public static void handleClient(PacketContext<ConsumeMagicka> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            character.setMagicka(context.message().amount);
        });
    }
}