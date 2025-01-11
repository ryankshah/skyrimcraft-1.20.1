package com.ryankshah.skyrimcraft.block.entity;

import com.ryankshah.skyrimcraft.registry.BlockEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RuneStoneBlockEntity extends BlockEntity {
    public RuneStoneBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegistry.RUNE_STONE.get(), pos, state);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
    }

    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }
}