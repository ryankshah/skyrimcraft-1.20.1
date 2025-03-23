package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LockableStorageCapability implements ICapabilitySerializable<Tag>
{
    public static final Capability<LockableStorageCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "lockable_storage_capability");
    private final LazyOptional<LockableStorageCapability> holder = LazyOptional.of(() -> this);

    private LockableStorage lh;

    public LockableStorageCapability(LevelChunk chunk) {
        lh = new LockableStorage(chunk);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public Tag serializeNBT() {
        return LockableStorage.CODEC.encodeStart(NbtOps.INSTANCE, lh).result().orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        LockableStorage.CODEC.parse(NbtOps.INSTANCE, nbt).result().ifPresent(this::setLockableStorage);
    }

    public LockableStorage getLockableStorage() {
        return lh;
    }

    public void setLockableStorage(LockableStorage lh) {
        this.lh = lh;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
