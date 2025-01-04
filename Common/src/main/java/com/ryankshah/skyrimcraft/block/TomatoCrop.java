package com.ryankshah.skyrimcraft.block;

import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class TomatoCrop extends CropBlock
{
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public TomatoCrop(Properties properties) {
        super(properties);
    }

    @Override
    public int getMaxAge() {
        return 5;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ItemRegistry.TOMATO_SEEDS.get();
    }
}