package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConjureFamiliarCapability extends Character implements ICapabilitySerializable<CompoundTag>
{
    public static final Capability<ConjureFamiliarCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "player_quests_capability");
    private final LazyOptional<ConjureFamiliarCapability> holder = LazyOptional.of(() -> this);

    private long familiarId = 0L;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLong("id", familiarId);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        setFamiliarId(nbt.getLong("id"));
    }

    public long getFamiliarId() {
        return familiarId;
    }

    public void setFamiliarId(long id) {
        this.familiarId = id;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
