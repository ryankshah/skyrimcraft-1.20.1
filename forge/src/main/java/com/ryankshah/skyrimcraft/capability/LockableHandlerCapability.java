package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LockableHandlerCapability implements ICapabilitySerializable<Tag>
{
    public static final Capability<LockableHandlerCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "lockable_handler_capability");
    private final LazyOptional<LockableHandlerCapability> holder = LazyOptional.of(() -> this);

    private LockableHandler lh;

    public LockableHandlerCapability(Level level) {
        lh = new LockableHandler(level);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public Tag serializeNBT() {
        return LockableHandler.CODEC.encodeStart(NbtOps.INSTANCE, lh).result().orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        LockableHandler.CODEC.parse(NbtOps.INSTANCE, nbt).result().ifPresent(this::setLockableHandler);
    }

    public LockableHandler getLockableHandler() {
        return lh;
    }

    public void setLockableHandler(LockableHandler lh) {
        this.lh = lh;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
