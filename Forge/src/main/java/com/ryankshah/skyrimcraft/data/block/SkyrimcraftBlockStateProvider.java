package com.ryankshah.skyrimcraft.data.block;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class SkyrimcraftBlockStateProvider extends BlockStateProvider
{
    public SkyrimcraftBlockStateProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockData.addBlockStateModels(this);
    }
}
