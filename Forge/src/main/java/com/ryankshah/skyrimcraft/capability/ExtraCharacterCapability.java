package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
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

public class ExtraCharacterCapability extends ExtraCharacter implements ICapabilitySerializable<Tag>
{
    public static final Capability<ExtraCharacterCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "extra_character_capability");
    private final LazyOptional<ExtraCharacterCapability> holder = LazyOptional.of(() -> this);

    private ExtraCharacter character = new ExtraCharacter();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public Tag serializeNBT() {
        return ExtraCharacter.CODEC.encodeStart(NbtOps.INSTANCE, character).result().orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        ExtraCharacter.CODEC.parse(NbtOps.INSTANCE, nbt).result().ifPresent(this::setCharacter);
    }

    public ExtraCharacter getCharacter() {
        return character;
    }

    public void setCharacter(ExtraCharacter character) {
        this.character = character;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
