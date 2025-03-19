package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.network.character.AddVisitedChunk;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ChunkEvents
{
//    @SubscribeEvent
//    public static void onChunkLoad(ChunkEvent.Load event) {
//        if (!(event.getChunk() instanceof LevelChunk chunk)) {
//            return;
//        }
//
//        // Get the extra character data
//        ExtraCharacter extraChar = Services.PLATFORM.getExtraCharacter(event.get());
//        if (extraChar == null) {
//            return;
//        }
//
//        // Add the chunk to visited chunks
//        ChunkPos pos = chunk.getPos();
//        extraChar.getMapData().addVisitedChunk(pos);
//
//        Dispatcher.sendToClient(new AddVisitedChunk(pos), player);
//    }
}