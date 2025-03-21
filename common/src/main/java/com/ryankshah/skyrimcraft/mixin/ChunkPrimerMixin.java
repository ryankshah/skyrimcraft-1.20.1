package com.ryankshah.skyrimcraft.mixin;

import com.ryankshah.skyrimcraft.util.ILockableProvider;
import com.ryankshah.skyrimcraft.util.Lockable;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.List;

@Mixin(ChunkAccess.class)
public class ChunkPrimerMixin implements ILockableProvider {
    private final List<Lockable> lockableList = new ArrayList<>();

    @Override
    public List<Lockable> getLockables() {
        return this.lockableList;
    }
}