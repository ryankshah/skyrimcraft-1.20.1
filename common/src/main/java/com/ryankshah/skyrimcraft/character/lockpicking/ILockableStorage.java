package com.ryankshah.skyrimcraft.character.lockpicking;

import com.ryankshah.skyrimcraft.util.Lockable;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public interface ILockableStorage
{
    Int2ObjectMap<Lockable> get();

    void add(Lockable lkb);

    void remove(int id);
}