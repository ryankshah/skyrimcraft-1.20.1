package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdateCharacter(Character character)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updatecharacter");

    public UpdateCharacter(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(Character.CODEC));
    }

    public static UpdateCharacter decode(FriendlyByteBuf buf) {
        return new UpdateCharacter(buf.readJsonWithCodec(Character.CODEC));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(Character.CODEC, character);
    }

    public static void handle(PacketContext<UpdateCharacter> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateCharacter> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setCharacterData(player, context.message().character);
        final UpdateCharacter sendToClient = new UpdateCharacter(Services.PLATFORM.getCharacter(player));
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateCharacter> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setCharacterData(minecraft.player, context.message().character);
        });
    }
}