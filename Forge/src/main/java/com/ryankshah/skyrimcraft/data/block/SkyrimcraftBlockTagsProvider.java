package com.ryankshah.skyrimcraft.data.block;

import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class SkyrimcraftBlockTagsProvider extends BlockTagsProvider
{
    public SkyrimcraftBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        //ores
        tag(TagsRegistry.BlockTagsInit.CORUNDUM_ORE_TAG).add(BlockRegistry.CORUNDUM_ORE.get());
        tag(TagsRegistry.BlockTagsInit.EBONY_ORE_TAG).add(BlockRegistry.EBONY_ORE.get());
        tag(TagsRegistry.BlockTagsInit.MALACHITE_ORE_TAG).add(BlockRegistry.MALACHITE_ORE.get());
        tag(TagsRegistry.BlockTagsInit.MOONSTONE_ORE_TAG).add(BlockRegistry.MOONSTONE_ORE.get());
        tag(TagsRegistry.BlockTagsInit.ORICHALCUM_ORE_TAG).add(BlockRegistry.ORICHALCUM_ORE.get());
        tag(TagsRegistry.BlockTagsInit.QUICKSILVER_ORE_TAG).add(BlockRegistry.QUICKSILVER_ORE.get());
        tag(TagsRegistry.BlockTagsInit.SILVER_ORE_TAG).add(BlockRegistry.SILVER_ORE.get());

        tag(BlockTags.DAMPENS_VIBRATIONS).add(
                BlockRegistry.DWEMER_WOOL_BLACK.get(), BlockRegistry.DWEMER_WOOL_BLACK_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_BROWN.get(), BlockRegistry.DWEMER_WOOL_BROWN_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_WHITE.get(), BlockRegistry.DWEMER_WOOL_WHITE_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_GREY.get(), BlockRegistry.DWEMER_WOOL_GREY_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_LIGHT_GREY.get(), BlockRegistry.DWEMER_WOOL_LIGHT_GREY_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_ORANGE.get(), BlockRegistry.DWEMER_WOOL_ORANGE_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_YELLOW.get(), BlockRegistry.DWEMER_WOOL_YELLOW_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_BLUE.get(), BlockRegistry.DWEMER_WOOL_BLUE_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_LIGHT_BLUE.get(), BlockRegistry.DWEMER_WOOL_LIGHT_BLUE_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_CYAN.get(), BlockRegistry.DWEMER_WOOL_CYAN_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_GREEN.get(), BlockRegistry.DWEMER_WOOL_GREEN_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_LIME_GREEN.get(), BlockRegistry.DWEMER_WOOL_LIME_GREEN_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_RED.get(), BlockRegistry.DWEMER_WOOL_RED_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_PINK.get(), BlockRegistry.DWEMER_WOOL_PINK_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_PURPLE.get(), BlockRegistry.DWEMER_WOOL_PURPLE_CARPET.get(),
                BlockRegistry.DWEMER_WOOL_MAGENTA.get(), BlockRegistry.DWEMER_WOOL_MAGENTA_CARPET.get()
        );

        tag(BlockTags.WALLS).add(
                BlockRegistry.DWEMER_METAL_BRICK_WALL.get(),
                BlockRegistry.DWEMER_STONE_BRICK_WALL.get()
        );

        tag(BlockTags.WALL_POST_OVERRIDE).add(
                BlockRegistry.DWEMER_METAL_TORCH.get(),
                BlockRegistry.DWEMER_REDSTONE_TORCH.get(),
                BlockRegistry.DWEMER_SOUL_TORCH.get(),
                BlockRegistry.DWEMER_STONE_PRESSURE_PLATE.get()
        );

        tag(BlockTags.STAIRS).add(
                BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS.get(),
                BlockRegistry.DWEMER_METAL_TILE_STAIRS.get(),
                BlockRegistry.DWEMER_METAL_BRICK_STAIRS.get(),
                BlockRegistry.DWEMER_STONE_BRICK_STAIRS.get()
        );

        // deepslate ores
        tag(TagsRegistry.BlockTagsInit.DEEPSLATE_CORUNDUM_ORE_TAG).add(BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get());
        tag(TagsRegistry.BlockTagsInit.DEEPSLATE_EBONY_ORE_TAG).add(BlockRegistry.DEEPSLATE_EBONY_ORE.get());
        tag(TagsRegistry.BlockTagsInit.DEEPSLATE_MALACHITE_ORE_TAG).add(BlockRegistry.DEEPSLATE_MALACHITE_ORE.get());
        tag(TagsRegistry.BlockTagsInit.DEEPSLATE_MOONSTONE_ORE_TAG).add(BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get());
        tag(TagsRegistry.BlockTagsInit.DEEPSLATE_ORICHALCUM_ORE_TAG).add(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get());
        tag(TagsRegistry.BlockTagsInit.DEEPSLATE_QUICKSILVER_ORE_TAG).add(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get());
        tag(TagsRegistry.BlockTagsInit.DEEPSLATE_SILVER_ORE_TAG).add(BlockRegistry.DEEPSLATE_SILVER_ORE.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(BlockRegistry.CORUNDUM_ORE.get())
                .add(BlockRegistry.EBONY_ORE.get())
                .add(BlockRegistry.MALACHITE_ORE.get())
                .add(BlockRegistry.MOONSTONE_ORE.get())
                .add(BlockRegistry.ORICHALCUM_ORE.get())
                .add(BlockRegistry.QUICKSILVER_ORE.get())
                .add(BlockRegistry.SILVER_ORE.get())
                .add(BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get())
                .add(BlockRegistry.DEEPSLATE_EBONY_ORE.get())
                .add(BlockRegistry.DEEPSLATE_MALACHITE_ORE.get())
                .add(BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get())
                .add(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get())
                .add(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get())
                .add(BlockRegistry.DEEPSLATE_SILVER_ORE.get())
                .add(BlockRegistry.SHOUT_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(BlockRegistry.CORUNDUM_ORE.get())
                .add(BlockRegistry.MALACHITE_ORE.get())
                .add(BlockRegistry.MOONSTONE_ORE.get())
                .add(BlockRegistry.ORICHALCUM_ORE.get())
                .add(BlockRegistry.QUICKSILVER_ORE.get())
                .add(BlockRegistry.SILVER_ORE.get())
                .add(BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get())
                .add(BlockRegistry.DEEPSLATE_MALACHITE_ORE.get())
                .add(BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get())
                .add(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get())
                .add(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get())
                .add(BlockRegistry.DEEPSLATE_SILVER_ORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(BlockRegistry.EBONY_ORE.get())
                .add(BlockRegistry.DEEPSLATE_EBONY_ORE.get());
    }
}