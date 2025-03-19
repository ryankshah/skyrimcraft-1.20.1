package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.network.character.AddVisitedChunk;
import com.ryankshah.skyrimcraft.network.character.UpdateExtraCharacter;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;

public class FabricChunkEvents {

    public static void init() {
        // Register chunk load event
        ServerChunkEvents.CHUNK_LOAD.register((world, chunk) -> {

        });
        ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> {
//            if (!(chunk instanceof LevelChunk levelChunk)) {
//                return;
//            }

            if (!chunk.getLevel().isClientSide()) {
                return;
            }

            // Get the client player
            Player player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }

            // Get the extra character data
            ExtraCharacter extraChar = Services.PLATFORM.getExtraCharacter(player);
            if (extraChar == null) {
                return;
            }

            // Add the chunk to visited chunks
            ChunkPos pos = chunk.getPos();
            extraChar.getMapData().addVisitedChunk(pos);
            Dispatcher.sendToServer(new AddVisitedChunk(pos));
        });

//        // Optional: Add tick event to update chunks around player periodically
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            Player player = client.player;
//            if (player == null || !player.level().isClientSide()) {
//                return;
//            }
//
//            ExtraCharacter extraChar = Services.PLATFORM.getExtraCharacter(player);
//            if (extraChar == null) {
//                return;
//            }
//
//            // Get player's current chunk
//            ChunkPos playerChunk = new ChunkPos(player.blockPosition());
//
//            // Add currently loaded chunks around player
////            int radius = 2; // Adjust radius as needed
////            for (int x = -radius; x <= radius; x++) {
////                for (int z = -radius; z <= radius; z++) {
////                    ChunkPos pos = new ChunkPos(playerChunk.x + x, playerChunk.z + z);
////                    if (player.level().hasChunk(pos.x, pos.z)) {
////                        extraChar.getMapData().addVisitedChunk(pos);
////                    }
////                }
////            }
////            ChunkPos playerChunk = new ChunkPos(player.blockPosition());
//            extraChar.getMapData().addVisitedChunk(playerChunk);
//
//            Dispatcher.sendToServer(new AddVisitedChunk(playerChunk));
//        });
    }

    // Call this in your mod's client initializer
    public static void register() {
        init();
    }
}