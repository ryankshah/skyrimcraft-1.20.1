package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SelectionCapability implements ICapabilitySerializable<Tag>
{
    public static final Capability<SelectionCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "selection_capability");
    private final LazyOptional<SelectionCapability> holder = LazyOptional.of(() -> this);

    private Selection selection = new Selection();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public Tag serializeNBT() {
        return Selection.CODEC.encodeStart(NbtOps.INSTANCE, selection).result().orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        Selection.CODEC.parse(NbtOps.INSTANCE, nbt).result().ifPresent(this::setSelection);
    }

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
