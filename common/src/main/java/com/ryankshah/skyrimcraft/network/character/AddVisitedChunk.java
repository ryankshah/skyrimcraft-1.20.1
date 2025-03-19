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
import net.minecraft.world.level.ChunkPos;

public record AddVisitedChunk(ChunkPos chunkPos)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "addvisitedchunk");

    public AddVisitedChunk(final FriendlyByteBuf buffer) {
        this(new ChunkPos(buffer.readLong()));
    }

    public static AddVisitedChunk decode(FriendlyByteBuf buf) {
        return new AddVisitedChunk(new ChunkPos(buf.readLong()));
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeLong(chunkPos.toLong());
    }

    public static void handle(PacketContext<AddVisitedChunk> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<AddVisitedChunk> context) {
        ServerPlayer player = context.sender();
        ExtraCharacter extraChar = Services.PLATFORM.getExtraCharacter(player);
        if(extraChar != null) {
            extraChar.getMapData().addVisitedChunk(context.message().chunkPos);
            Services.PLATFORM.setExtraCharacterData(player, extraChar);
//            Dispatcher.sendToClient(new UpdateExtraCharacter(extraChar), player);
        }
    }

    public static void handleClient(PacketContext<AddVisitedChunk> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            ExtraCharacter extraChar = Services.PLATFORM.getExtraCharacter(minecraft.player);
            if(extraChar != null) {
                extraChar.getMapData().addVisitedChunk(context.message().chunkPos);
                Services.PLATFORM.setExtraCharacterData(minecraft.player, extraChar);
            }
        });
    }
}