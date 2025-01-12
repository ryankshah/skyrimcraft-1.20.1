package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
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

public class LevelUpdatesCapability implements ICapabilitySerializable<Tag>
{
    public static final Capability<LevelUpdatesCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "level_updates_capability");
    private final LazyOptional<LevelUpdatesCapability> holder = LazyOptional.of(() -> this);

    private LevelUpdates levelUpdates = new LevelUpdates();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public Tag serializeNBT() {
        return LevelUpdates.CODEC.encodeStart(NbtOps.INSTANCE, levelUpdates).result().orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        LevelUpdates.CODEC.parse(NbtOps.INSTANCE, nbt).result().ifPresent(this::setLevelUpdates);
    }

    public int getLevelUpdates() {
        return levelUpdates.getLevelUpdates();
    }

    public LevelUpdates getLevelUpdatesAttachment() {return levelUpdates;}

    public void setLevelUpdates(LevelUpdates character) {
        this.levelUpdates = character;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
