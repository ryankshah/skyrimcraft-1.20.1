package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
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

public class StatIncreasesCapability extends StatIncreases implements ICapabilitySerializable<Tag>
{
    public static final Capability<StatIncreasesCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "stat_increases_capability");
    private final LazyOptional<StatIncreasesCapability> holder = LazyOptional.of(() -> this);

    private StatIncreases character = new StatIncreases();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public Tag serializeNBT() {
        return StatIncreases.CODEC.encodeStart(NbtOps.INSTANCE, character).result().orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        StatIncreases.CODEC.parse(NbtOps.INSTANCE, nbt).result().ifPresent(this::setStatIncreases);
    }

    public StatIncreases getStatIncreases() {
        return character;
    }

    public void setStatIncreases(StatIncreases character) {
        this.character = character;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
