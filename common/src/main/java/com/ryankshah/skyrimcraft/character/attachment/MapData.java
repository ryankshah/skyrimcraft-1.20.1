package com.ryankshah.skyrimcraft.character.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.world.level.ChunkPos;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Stores map data for a player including visited chunks
 */
public class MapData
{
    public static final Codec<MapData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.LONG.listOf().fieldOf("visited_chunks").forGetter(data -> data.visitedChunks.stream().toList())
    ).apply(instance, list -> {
        MapData data = new MapData();
        data.visitedChunks.addAll(list);
        return data;
    }));

    private final Set<Long> visitedChunks = new HashSet<>();
    private static final String VISITED_CHUNKS_TAG = "VisitedChunks";

    /**
     * Mark a chunk as visited
     */
    public void addVisitedChunk(ChunkPos pos) {
        visitedChunks.add(pos.toLong());
    }

    /**
     * Check if a chunk has been visited
     */
    public boolean hasVisitedChunk(ChunkPos pos) {
        return visitedChunks.contains(pos.toLong());
    }

    /**
     * Get all visited chunks (immutable)
     */
    public Set<Long> getVisitedChunks() {
        return Collections.unmodifiableSet(visitedChunks);
    }

    /**
     * Clear all visited chunks
     */
    public void clearVisitedChunks() {
        visitedChunks.clear();
    }

    /**
     * Save map data to NBT
     */
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        ListTag chunksList = new ListTag();

        for (Long chunkPos : visitedChunks) {
            chunksList.add(LongTag.valueOf(chunkPos));
        }

        tag.put(VISITED_CHUNKS_TAG, chunksList);
        return tag;
    }

    /**
     * Load map data from NBT
     */
    public void load(CompoundTag tag) {
        visitedChunks.clear();
        ListTag chunksList = tag.getList(VISITED_CHUNKS_TAG, 4); // 4 is the ID for LongTag

        for (int i = 0; i < chunksList.size(); i++) {
            LongTag longTag = (LongTag) chunksList.get(i);
            visitedChunks.add(longTag.getAsLong());
        }
    }
}