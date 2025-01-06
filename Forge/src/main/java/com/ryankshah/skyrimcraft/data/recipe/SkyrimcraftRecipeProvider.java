package com.ryankshah.skyrimcraft.data.recipe;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SkyrimcraftRecipeProvider extends RecipeProvider
{
    private static final List<ItemLike> CORUNDUM_ORE_SMELTABLE = List.of(BlockRegistry.CORUNDUM_ORE.get(), BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get());
    private static final List<ItemLike> EBONY_ORE_SMELTABLE = List.of(BlockRegistry.EBONY_ORE.get(), BlockRegistry.DEEPSLATE_EBONY_ORE.get());
    private static final List<ItemLike> MALACHITE_ORE_SMELTABLE = List.of(BlockRegistry.MALACHITE_ORE.get(), BlockRegistry.DEEPSLATE_MALACHITE_ORE.get());
    private static final List<ItemLike> MOONSTONE_ORE_SMELTABLE = List.of(BlockRegistry.MOONSTONE_ORE.get(), BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get());
    private static final List<ItemLike> ORICHALCUM_ORE_SMELTABLE = List.of(BlockRegistry.ORICHALCUM_ORE.get(), BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get());
    private static final List<ItemLike> QUICKSILVER_ORE_SMELTABLE = List.of(BlockRegistry.QUICKSILVER_ORE.get(), BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get());
    private static final List<ItemLike> SILVER_ORE_SMELTABLE = List.of(BlockRegistry.SILVER_ORE.get(), BlockRegistry.DEEPSLATE_SILVER_ORE.get());


    public SkyrimcraftRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(p_248933_, lookupProvider); //, lookupProvider
    }

    @Override
    protected void buildRecipes() {
        oreSmelting(CORUNDUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.CORUNDUM_INGOT.get(), 0.25f, 200, "corundum_ingot");
        oreBlasting(CORUNDUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.CORUNDUM_INGOT.get(), 0.25f, 100, "corundum_ingot");

        oreSmelting(EBONY_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.EBONY_INGOT.get(), 0.25f, 200, "ebony_ingot");
        oreBlasting(EBONY_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.EBONY_INGOT.get(), 0.25f, 100, "ebony_ingot");

        oreSmelting(MALACHITE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MALACHITE_INGOT.get(), 0.25f, 200, "malachite_ingot");
        oreBlasting(MALACHITE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MALACHITE_INGOT.get(), 0.25f, 100, "malachite_ingot");

        oreSmelting(MOONSTONE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MOONSTONE_INGOT.get(), 0.25f, 200, "moonstone_ingot");
        oreBlasting(MOONSTONE_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.MOONSTONE_INGOT.get(), 0.25f, 100, "moonstone_ingot");

        oreSmelting(ORICHALCUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.ORICHALCUM_INGOT.get(), 0.25f, 200, "orichalcum_ingot");
        oreBlasting(ORICHALCUM_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.ORICHALCUM_INGOT.get(), 0.25f, 100, "orichalcum_ingot");

        oreSmelting(QUICKSILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.QUICKSILVER_INGOT.get(), 0.25f, 200, "quicksilver_ingot");
        oreBlasting(QUICKSILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.QUICKSILVER_INGOT.get(), 0.25f, 100, "quicksilver_ingot");

        oreSmelting(SILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), 0.25f, 200, "silver_ingot");
        oreBlasting(SILVER_ORE_SMELTABLE, RecipeCategory.MISC, ItemRegistry.SILVER_INGOT.get(), 0.25f, 100, "silver_ingot");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)).requires(Items.LEATHER).unlockedBy("has_leather", has(Items.LEATHER)).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.FLOUR.get()).requires(Items.WHEAT).requires(Items.BOWL).unlockedBy("has_wheat", has(Items.WHEAT)).save(pWriter);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ItemRegistry.BUTTER.get()).requires(Items.MILK_BUCKET).requires(Items.MAGMA_CREAM).unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET)).save(pWriter);
        // blocks
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.OVEN.get()).define('s', Blocks.STONE).define('b', Blocks.STONE_BRICKS).pattern(" b ").pattern("b b").pattern("sss").unlockedBy("has_stone_brick", has(Blocks.STONE_BRICKS)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.ALCHEMY_TABLE.get()).define('p', ItemTags.PLANKS).define('g', Items.GLASS_BOTTLE).pattern(" g ").pattern("ppp").pattern("p p").unlockedBy("has_planks", has(Items.OAK_PLANKS)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, BlockRegistry.BLACKSMITH_FORGE.get()).define('l', Items.LAVA_BUCKET).define('s', Blocks.STONE).define('c', Blocks.COBBLESTONE).pattern("c c").pattern("clc").pattern("sss").unlockedBy("has_cobble", has(Blocks.COBBLESTONE)).save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_BROWN_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_BROWN.get()).pattern("ww").unlockedBy("has_dwemer_brown_wool", has(BlockRegistry.DWEMER_WOOL_BROWN.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_ORANGE_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_ORANGE.get()).pattern("ww").unlockedBy("has_dwemer_orange_wool", has(BlockRegistry.DWEMER_WOOL_ORANGE.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_RED_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_RED.get()).pattern("ww").unlockedBy("has_dwemer_red_wool", has(BlockRegistry.DWEMER_WOOL_RED.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_MAGENTA_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_MAGENTA.get()).pattern("ww").unlockedBy("has_dwemer_magenta_wool", has(BlockRegistry.DWEMER_WOOL_MAGENTA.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_PINK_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_PINK.get()).pattern("ww").unlockedBy("has_dwemer_pink_wool", has(BlockRegistry.DWEMER_WOOL_PINK.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_PURPLE_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_PURPLE.get()).pattern("ww").unlockedBy("has_dwemer_purple_wool", has(BlockRegistry.DWEMER_WOOL_PURPLE.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_BLUE_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_BLUE.get()).pattern("ww").unlockedBy("has_dwemer_blue_wool", has(BlockRegistry.DWEMER_WOOL_BLUE.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_LIGHT_BLUE_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_LIGHT_BLUE.get()).pattern("ww").unlockedBy("has_dwemer_light_blue_wool", has(BlockRegistry.DWEMER_WOOL_LIGHT_BLUE.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_CYAN_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_CYAN.get()).pattern("ww").unlockedBy("has_dwemer_cyan_wool", has(BlockRegistry.DWEMER_WOOL_CYAN.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_GREEN_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_GREEN.get()).pattern("ww").unlockedBy("has_dwemer_green_wool", has(BlockRegistry.DWEMER_WOOL_GREEN.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_LIME_GREEN_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_LIME_GREEN.get()).pattern("ww").unlockedBy("has_dwemer_lime_green_wool", has(BlockRegistry.DWEMER_WOOL_LIME_GREEN.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_YELLOW_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_YELLOW.get()).pattern("ww").unlockedBy("has_dwemer_yellow_wool", has(BlockRegistry.DWEMER_WOOL_YELLOW.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_BLACK_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_BLACK.get()).pattern("ww").unlockedBy("has_dwemer_black_wool", has(BlockRegistry.DWEMER_WOOL_BLACK.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_GREY_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_GREY.get()).pattern("ww").unlockedBy("has_dwemer_grey_wool", has(BlockRegistry.DWEMER_WOOL_GREY.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_LIGHT_GREY_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_LIGHT_GREY.get()).pattern("ww").unlockedBy("has_dwemer_light_grey_wool", has(BlockRegistry.DWEMER_WOOL_LIGHT_GREY.get()));
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, BlockRegistry.DWEMER_WOOL_WHITE_CARPET.get()).define('w', BlockRegistry.DWEMER_WOOL_WHITE.get()).pattern("ww").unlockedBy("has_dwemer_white_wool", has(BlockRegistry.DWEMER_WOOL_WHITE.get()));

    }

    protected static void oreSmelting(
            RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ) {
        oreCooking(
                pRecipeOutput,
                RecipeSerializer.SMELTING_RECIPE,
                SmeltingRecipe::new,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_smelting"
        );
    }

    protected static void oreBlasting(
            RecipeOutput pRecipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup
    ) {
        oreCooking(
                pRecipeOutput,
                RecipeSerializer.BLASTING_RECIPE,
                BlastingRecipe::new,
                pIngredients,
                pCategory,
                pResult,
                pExperience,
                pCookingTime,
                pGroup,
                "_from_blasting"
        );
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(
            RecipeOutput pRecipeOutput,
            RecipeSerializer<T> pSerializer,
            AbstractCookingRecipe.Factory<T> pRecipeFactory,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup,
            String pSuffix
    ) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pSerializer, pRecipeFactory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pRecipeOutput, Constants.MODID + ":" + getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }
    }
}