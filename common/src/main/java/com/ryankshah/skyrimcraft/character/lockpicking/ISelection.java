package com.ryankshah.skyrimcraft.character.lockpicking;

import net.minecraft.core.BlockPos;

public interface ISelection
{
    BlockPos get();

    void set(BlockPos pos);
}