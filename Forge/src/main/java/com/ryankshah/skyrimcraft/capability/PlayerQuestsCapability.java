package com.ryankshah.skyrimcraft.capability;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.PlayerQuests;
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

public class PlayerQuestsCapability extends PlayerQuests implements ICapabilitySerializable<Tag>
{
    public static final Capability<PlayerQuestsCapability> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
    public static final ResourceLocation ID = new ResourceLocation(Constants.MODID, "player_quests_capability");
    private final LazyOptional<PlayerQuestsCapability> holder = LazyOptional.of(() -> this);

    private PlayerQuests character = new PlayerQuests();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, this.holder);
    }

    @Override
    public Tag serializeNBT() {
        return PlayerQuests.CODEC.encodeStart(NbtOps.INSTANCE, character).result().orElseGet(CompoundTag::new);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        PlayerQuests.CODEC.parse(NbtOps.INSTANCE, nbt).result().ifPresent(this::setPlayerQuests);
    }

    public PlayerQuests getPlayerQuests() {
        return character;
    }

    public void setPlayerQuests(PlayerQuests character) {
        this.character = character;
    }

    public void onInvalidate() {
        this.holder.invalidate();
    }
}
