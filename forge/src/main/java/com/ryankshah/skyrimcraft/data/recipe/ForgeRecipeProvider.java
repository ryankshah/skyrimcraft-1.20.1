package com.ryankshah.skyrimcraft.data.recipe;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.registry.RecipeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ForgeRecipeProvider implements DataProvider, IConditionBuilder
{
    protected final PackOutput.PathProvider recipePathProvider;
    protected final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public ForgeRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.recipePathProvider = p_248933_.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
        this.lookupProvider = lookupProvider;
    }

    protected void buildRecipes(Consumer<FinishedRecipe> pRecipeOutput) {
        daedricRecipes(pRecipeOutput);
        dwarvenRecipes(pRecipeOutput);
        ebonyRecipes(pRecipeOutput);
        elvenRecipes(pRecipeOutput);
        glassRecipes(pRecipeOutput);
        ironRecipes(pRecipeOutput);
        orcishRecipes(pRecipeOutput);
        steelRecipes(pRecipeOutput);
    }

    protected static FinishedRecipe makeRecipe(Item item, ForgeRecipe recipe) {
        return new FinishedRecipe() {
            @Override
            public void serializeRecipeData(JsonObject p_125967_) {
                p_125967_.addProperty("type", Constants.MODID+":forge");

                p_125967_.addProperty("category", recipe.getCategory());

                JsonObject output = new JsonObject();
                output.addProperty("item", BuiltInRegistries.ITEM.getKey(recipe.getResult().getItem()).toString());
                output.addProperty("amount", recipe.getResult().getCount());
                p_125967_.add("output", output);

                JsonArray recipeItems = new JsonArray();
                for(Ingredient ing : recipe.getRecipeItems()) {
                    ItemStack[] stacks = ing.getItems();
                    for(ItemStack stack : stacks) {
                        JsonObject stackObj = new JsonObject();
                        stackObj.addProperty("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
                        stackObj.addProperty("amount", stack.getCount());
                        recipeItems.add(stackObj);
                    }
                }
                p_125967_.add("recipe", recipeItems);

                p_125967_.addProperty("levelToCreate", recipe.getRequiredLevel());
                p_125967_.addProperty("xp", recipe.getXpGained());
            }

            @Override
            public ResourceLocation getId() {
                return new ResourceLocation(Constants.MODID, "forge/" + BuiltInRegistries.ITEM.getKey(item).getPath());
            }

            @Override
            public RecipeSerializer<?> getType() {
                return RecipeRegistry.FORGE_RECIPE_SERIALIZER.get();
            }

            @Override
            public @Nullable JsonObject serializeAdvancement() {
                return null;
            }

            @Override
            public @Nullable ResourceLocation getAdvancementId() {
                return null;
            }
        };
    }

    public CompletableFuture<?> run(CachedOutput p_254020_) {
        Set<ResourceLocation> set = Sets.newHashSet();
        List<CompletableFuture<?>> list = new ArrayList<>();
        this.buildRecipes((p_253413_) -> {
            if (!set.add(p_253413_.getId())) {
                throw new IllegalStateException("Duplicate recipe " + p_253413_.getId());
            } else {
                list.add(DataProvider.saveStable(p_254020_, p_253413_.serializeRecipe(), this.recipePathProvider.json(p_253413_.getId())));
//                JsonObject jsonobject = p_253413_.serializeAdvancement();
//                if (jsonobject != null) {
//                    var saveAdvancementFuture = saveAdvancement(p_254020_, p_253413_, jsonobject);
//                    if (saveAdvancementFuture != null)
//                        list.add(saveAdvancementFuture);
//                }

            }
        });
        return CompletableFuture.allOf(list.toArray((p_253414_) -> {
            return new CompletableFuture[p_253414_];
        }));
    }

    @Override
    public String getName() {
        return Constants.MODID + " Forge Recipes";
    }

    private static void daedricRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("daedric", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.DAEDRIC_ARROW.get(), 24)).level(90).xp(5)
//                .category("daedric")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.DAEDRIC_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_DAGGER.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_DAGGER.get(), 1), 90, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_SWORD.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_SWORD.get(), 1), 90, 13,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_BATTLEAXE.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_BATTLEAXE.get(), 1), 90, 27,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_BOW.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_BOW.get(), 1), 90, 25,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_GREATSWORD.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_GREATSWORD.get(), 1), 90, 25,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_MACE.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_MACE.get(), 1), 90, 18,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_WAR_AXE.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_WAR_AXE.get(), 1), 90, 15,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_WARHAMMER.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_WARHAMMER.get(), 1), 90, 40,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_HELMET.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_HELMET.get(), 1), 90, 16,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_CHESTPLATE.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_CHESTPLATE.get(), 1), 90, 32,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_LEGGINGS.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_LEGGINGS.get(), 1), 90, 32,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_BOOTS.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_BOOTS.get(), 1), 90, 16,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DAEDRIC_SHIELD.get(),
                new ForgeRecipe("daedric", new ItemStack(ItemRegistry.DAEDRIC_SHIELD.get(), 1), 90, 16,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 4)),
                                Ingredient.of(new ItemStack(ItemRegistry.DAEDRA_HEART.get(), 1))
                        ))));
    }

    private static void dwarvenRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("dwarven", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.DWARVEN_ARROW.get(), 24)).level(30).xp(5)
//                .category("dwarven")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.DWARVEN_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_DAGGER.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_DAGGER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 4)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_BATTLEAXE.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_BATTLEAXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_BOW.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_BOW.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_GREATSWORD.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_GREATSWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_MACE.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_MACE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_SWORD.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_SWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_WAR_AXE.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_WAR_AXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_WARHAMMER.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_WARHAMMER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_HELMET.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_HELMET.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_CHESTPLATE.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_CHESTPLATE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_LEGGINGS.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_LEGGINGS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_BOOTS.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_BOOTS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.DWARVEN_SHIELD.get(),
                new ForgeRecipe("dwarven", new ItemStack(ItemRegistry.DWARVEN_SHIELD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_METAL_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
    }

    private static void ebonyRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("ebony", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.EBONY_ARROW.get(), 24)).level(80).xp(5)
//                .category("ebony")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.EBONY_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_DAGGER.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_DAGGER.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_SWORD.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_SWORD.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_GREATSWORD.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_GREATSWORD.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_WAR_AXE.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_WAR_AXE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_BATTLEAXE.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_BATTLEAXE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_MACE.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_MACE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_WARHAMMER.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_WARHAMMER.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_BOW.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_BOW.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_HELMET.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_HELMET.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_CHESTPLATE.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_CHESTPLATE.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_LEGGINGS.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_LEGGINGS.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 5))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_BOOTS.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_BOOTS.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.EBONY_SHIELD.get(),
                new ForgeRecipe("ebony", new ItemStack(ItemRegistry.EBONY_SHIELD.get(), 1), 80, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.EBONY_INGOT.get(), 4))
                        ))));
    }

    private static void elvenRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("elven", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.ELVEN_ARROW.get(), 24)).level(30).xp(5)
//                .category("elven")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.ELVEN_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_DAGGER.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_DAGGER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_SWORD.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_SWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_GREATSWORD.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_GREATSWORD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_WAR_AXE.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_WAR_AXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_BATTLEAXE.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_BATTLEAXE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_MACE.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_MACE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_WARHAMMER.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_WARHAMMER.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_BOW.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_BOW.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.QUICKSILVER_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_HELMET.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_HELMET.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_CHESTPLATE.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_CHESTPLATE.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_LEGGINGS.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_LEGGINGS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_BOOTS.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_BOOTS.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ELVEN_SHIELD.get(),
                new ForgeRecipe("elven", new ItemStack(ItemRegistry.ELVEN_SHIELD.get(), 1), 30, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 4))
                        ))));
    }

    private static void glassRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("glass", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.GLASS_ARROW.get(), 24)).level(70).xp(5)
//                .category("glass")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.GLASS_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_DAGGER.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_DAGGER.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_SWORD.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_SWORD.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_GREATSWORD.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_GREATSWORD.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_WAR_AXE.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_WAR_AXE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_BATTLEAXE.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_BATTLEAXE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_MACE.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_MACE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_WARHAMMER.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_WARHAMMER.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_BOW.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_BOW.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_HELMET.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_HELMET.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_CHESTPLATE.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_CHESTPLATE.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_LEGGINGS.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_LEGGINGS.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_BOOTS.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_BOOTS.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.LEATHER, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.GLASS_SHIELD.get(),
                new ForgeRecipe("glass", new ItemStack(ItemRegistry.GLASS_SHIELD.get(), 1), 70, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.MOONSTONE_INGOT.get(), 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.MALACHITE_INGOT.get(), 4))
                        ))));
    }

    private static void ironRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("iron", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.IRON_ARROW.get(), 24)).level(1).xp(5)
//                .category("iron")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(Items.IRON_INGOT, 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.IRON_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_DAGGER.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_DAGGER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_SWORD.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_SWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_GREATSWORD.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_GREATSWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_WAR_AXE.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_WAR_AXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_BATTLEAXE.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_BATTLEAXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_MACE.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_MACE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_WARHAMMER.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_WARHAMMER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_HELMET.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_HELMET.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_CHESTPLATE.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_CHESTPLATE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 5))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_LEGGINGS.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_LEGGINGS.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 5))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_BOOTS.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_BOOTS.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.IRON_SHIELD.get(),
                new ForgeRecipe("iron", new ItemStack(ItemRegistry.IRON_SHIELD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 4))
                        ))));
    }

    private static void orcishRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("orcish", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.ORCISH_ARROW.get(), 24)).level(50).xp(5)
//                .category("orcish")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.ORCISH_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_DAGGER.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_DAGGER.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_SWORD.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_SWORD.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_GREATSWORD.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_GREATSWORD.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_WAR_AXE.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_WAR_AXE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_BATTLEAXE.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_BATTLEAXE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_MACE.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_MACE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_WARHAMMER.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_WARHAMMER.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_BOW.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_BOW.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_HELMET.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_HELMET.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_CHESTPLATE.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_CHESTPLATE.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_LEGGINGS.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_LEGGINGS.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_BOOTS.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_BOOTS.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.ORCISH_SHIELD.get(),
                new ForgeRecipe("orcish", new ItemStack(ItemRegistry.ORCISH_SHIELD.get(), 1), 50, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.ORICHALCUM_INGOT.get(), 3))
                        ))));
    }

    private static void steelRecipes(Consumer<FinishedRecipe> consumer) {
//        RECIPES.put("steel", ForgeRecipe.Builder.recipe().output(new ItemStack(ItemRegistry.STEEL_ARROW.get(), 24)).level(1).xp(5)
//                .category("steel")
//                .addRecipeItem(new ItemStack(Items.STICK, 1))
//                .addRecipeItem(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
//                .save(consumer, Constants.MODID + ":recipes/forge/" + ItemRegistry.STEEL_ARROW.getId().getPath()));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_DAGGER.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_DAGGER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 1))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_SWORD.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_SWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_GREATSWORD.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_GREATSWORD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 2)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_WAR_AXE.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_WAR_AXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 2))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_BATTLEAXE.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_BATTLEAXE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 2)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_MACE.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_MACE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 3))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_WARHAMMER.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_WARHAMMER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 3)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 4))
                        ))));
        consumer.accept(makeRecipe(
                ItemRegistry.STEEL_SHIELD.get(),
                new ForgeRecipe("steel", new ItemStack(ItemRegistry.STEEL_SHIELD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(new ItemStack(ItemRegistry.LEATHER_STRIPS.get(), 1)),
                                Ingredient.of(new ItemStack(Items.IRON_INGOT, 1)),
                                Ingredient.of(new ItemStack(ItemRegistry.STEEL_INGOT.get(), 3))
                        ))));
    }
}
