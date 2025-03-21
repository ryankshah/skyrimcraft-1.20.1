package com.ryankshah.skyrimcraft.character.lockpicking;

import com.ryankshah.skyrimcraft.util.Lockable;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.IntTag;

import java.util.Observer;

public interface ILockableHandler extends Observer
{
    int nextId();

    Int2ObjectMap<Lockable> getLoaded();

    Int2ObjectMap<Lockable> getInChunk(BlockPos pos);

    boolean add(Lockable lkb);

    boolean remove(int id);
}