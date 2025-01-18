package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public record UpdateExtraCharacter(ExtraCharacter character)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updateextracharacter");

    public UpdateExtraCharacter(final FriendlyByteBuf buffer) {
        this(buffer.readJsonWithCodec(ExtraCharacter.CODEC));
    }

    public static UpdateExtraCharacter decode(FriendlyByteBuf buf) {
        return new UpdateExtraCharacter(buf.readJsonWithCodec(ExtraCharacter.CODEC));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeJsonWithCodec(ExtraCharacter.CODEC, character);
    }

    public static void handle(PacketContext<UpdateExtraCharacter> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateExtraCharacter> context) {
        ServerPlayer player = context.sender();
        Services.PLATFORM.setExtraCharacterData(player, context.message().character);
        final UpdateExtraCharacter sendToClient = new UpdateExtraCharacter(Services.PLATFORM.getExtraCharacter(player));
        Dispatcher.sendToClient(sendToClient, player);
//        PacketDistributor.PLAYER.with(player).send(sendToClient);
    }

    public static void handleClient(PacketContext<UpdateExtraCharacter> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Services.PLATFORM.setExtraCharacterData(minecraft.player, context.message().character);
        });
    }
}