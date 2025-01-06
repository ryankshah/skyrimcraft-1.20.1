package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.Sets;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.OvenRecipe;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.WithConditions;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class OvenRecipeProvider implements DataProvider
{
    protected final PackOutput.PathProvider recipePathProvider;
    protected final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public OvenRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.recipePathProvider = p_248933_.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
        this.lookupProvider = lookupProvider;
    }

    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.SWEET_ROLL.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.SWEET_ROLL.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.BUTTER.get()),
                                Ingredient.of(Items.EGG),
                                Ingredient.of(ItemRegistry.SALT_PILE.get()),
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(Items.MILK_BUCKET)
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.GARLIC_BREAD.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.GARLIC_BREAD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.BUTTER.get()),
                                Ingredient.of(ItemRegistry.GARLIC.get()),
                                Ingredient.of(ItemRegistry.FLOUR.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.POTATO_BREAD.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.POTATO_BREAD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get()),
                                Ingredient.of(Items.MILK_BUCKET),
                                Ingredient.of(Items.EGG),
                                Ingredient.of(Items.POTATO)
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.APPLE_PIE.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.APPLE_PIE.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get()),
                                Ingredient.of(ItemRegistry.BUTTER.get()),
                                Ingredient.of(Items.EGG),
                                Ingredient.of(Items.APPLE)
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.MAMMOTH_STEAK.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.MAMMOTH_STEAK.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.MAMMOTH_SNOUT.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.TOMATO_SOUP.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.TOMATO_SOUP.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.GARLIC.get()),
                                Ingredient.of(ItemRegistry.LEEK.get()),
                                Ingredient.of(ItemRegistry.TOMATO.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.APPLE_CABBAGE_STEW.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.APPLE_CABBAGE_STEW.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.CABBAGE.get()),
                                Ingredient.of(Items.APPLE),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.APPLE_DUMPLING.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.APPLE_DUMPLING.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(Items.APPLE),
                                Ingredient.of(ItemRegistry.GREEN_APPLE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.BEEF_STEW.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.BEEF_STEW.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(Items.BEEF),
                                Ingredient.of(Items.CARROT),
                                Ingredient.of(ItemRegistry.SALT_PILE.get()),
                                Ingredient.of(ItemRegistry.GARLIC.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.CABBAGE_SOUP.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.CABBAGE_SOUP.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.CABBAGE.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.CABBAGE_POTATO_SOUP.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.CABBAGE_POTATO_SOUP.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.CABBAGE.get()),
                                Ingredient.of(Items.POTATO),
                                Ingredient.of(ItemRegistry.LEEK.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.CHICKEN_DUMPLING.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.CHICKEN_DUMPLING.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.GARLIC.get()),
                                Ingredient.of(Items.CHICKEN),
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.LEEK.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.CLAM_CHOWDER.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.CLAM_CHOWDER.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.CLAM_MEAT.get()),
                                Ingredient.of(Items.POTATO),
                                Ingredient.of(Items.MILK_BUCKET),
                                Ingredient.of(ItemRegistry.BUTTER.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.LEG_OF_GOAT_ROAST.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.LEG_OF_GOAT_ROAST.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.LEG_OF_GOAT.get()),
                                Ingredient.of(ItemRegistry.BUTTER.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.HORSE_HAUNCH.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.HORSE_HAUNCH.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.HORSE_MEAT.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.VEGETABLE_SOUP.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.VEGETABLE_SOUP.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.CABBAGE.get()),
                                Ingredient.of(Items.POTATO),
                                Ingredient.of(ItemRegistry.LEEK.get()),
                                Ingredient.of(ItemRegistry.TOMATO.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.BRAIDED_BREAD.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.BRAIDED_BREAD.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE,
                        ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.JAZBAY_CROSTATA.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.JAZBAY_CROSTATA.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.JAZBAY_GRAPES.get(), ItemRegistry.JAZBAY_GRAPES.get()),
                                Ingredient.of(ItemRegistry.BUTTER.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE,
                        ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.JUNIPER_BERRY_CROSTATA.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.JUNIPER_BERRY_CROSTATA.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.JUNIPER_BERRIES.get(), ItemRegistry.JUNIPER_BERRIES.get(), ItemRegistry.JUNIPER_BERRIES.get()),
                                Ingredient.of(ItemRegistry.BUTTER.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE,
                        ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.SNOWBERRY_CROSTATA.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.SNOWBERRY_CROSTATA.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.SNOWBERRIES.get(), ItemRegistry.SNOWBERRIES.get()),
                                Ingredient.of(ItemRegistry.BUTTER.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE,
                        ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.LAVENDER_DUMPLING.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.LAVENDER_DUMPLING.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(Items.SUGAR),
                                Ingredient.of(ItemRegistry.FLOUR.get()),
                                Ingredient.of(ItemRegistry.SNOWBERRIES.get(), ItemRegistry.SNOWBERRIES.get()),
                                Ingredient.of(ItemRegistry.LAVENDER.get())
                        )),
                null
        );
        pRecipeOutput.accept(
                ResourceKey.create(Registries.RECIPE,
                        ResourceLocation.fromNamespaceAndPath(Constants.MODID, "oven/" + BuiltInRegistries.ITEM.getKey(ItemRegistry.POTATO_SOUP.get()).getPath())),
                new OvenRecipe("food", new ItemStack(ItemRegistry.POTATO_SOUP.get(), 1), 1, 5,
                        NonNullList.of(
                                Ingredient.of(Items.POTATO),
                                Ingredient.of(ItemRegistry.SALT_PILE.get())
                        )),
                null
        );
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return this.lookupProvider.thenCompose((provider) -> {
            final Set<ResourceKey<Recipe<?>>> set = Sets.newHashSet();
            final List<CompletableFuture<?>> list = new ArrayList<>();
            this.buildRecipes(new RecipeOutput() {
                @Override
                public void accept(ResourceKey<Recipe<?>> resourceKey, Recipe<?> recipe, @Nullable AdvancementHolder advancementHolder, ICondition... iConditions) {
                    if (!set.add(resourceKey)) {
                        throw new IllegalStateException("Duplicate recipe " + resourceKey);
                    } else {
                        list.add(DataProvider.saveStable(pOutput, provider, Recipe.CONDITIONAL_CODEC, Optional.of(new WithConditions(recipe, iConditions)), OvenRecipeProvider.this.recipePathProvider.json(resourceKey)));
                    }
                }

                @Override
                public Advancement.Builder advancement() {
                    return null;
                }

                @Override
                public void includeRootAdvancement() {
                }
            });
            return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
        });
    }

    @Override
    public String getName() {
        return Constants.MODID + " Oven Recipes";
    }
}
