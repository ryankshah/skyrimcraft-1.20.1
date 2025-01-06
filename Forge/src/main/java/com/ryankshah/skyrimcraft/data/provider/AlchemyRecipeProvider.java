package com.ryankshah.skyrimcraft.data.provider;

import com.google.common.collect.Sets;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.AlchemyRecipe;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.item.potion.IPotion;
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

public class AlchemyRecipeProvider implements DataProvider
{
    protected final PackOutput.PathProvider recipePathProvider;
    protected final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public AlchemyRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.recipePathProvider = p_248933_.createPathProvider(PackOutput.Target.DATA_PACK, "recipe");
        this.lookupProvider = lookupProvider;
    }

    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<SkyrimPotion> potions = ItemRegistry.ITEMS.getEntries().stream().filter(item -> item.get() instanceof SkyrimPotion).map(item -> (SkyrimPotion)item.get()).toList();
        for(SkyrimPotion potion : potions) {
            if(potion.getCategory() == IPotion.PotionCategory.UNIQUE || potion.getIngredients().isEmpty())
                continue;

            NonNullList<Ingredient> ings = potion.getIngredients();
            AlchemyRecipe recipe = new AlchemyRecipe(potion.getCategory().toString(), new ItemStack(potion, 1), 2, 1, ings);

            pRecipeOutput.accept(ResourceKey.create(Registries.RECIPE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "alchemy/" + BuiltInRegistries.ITEM.getKey(potion).getPath())), recipe, null);
        }
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
                        list.add(DataProvider.saveStable(pOutput, provider, Recipe.CONDITIONAL_CODEC, Optional.of(new WithConditions(recipe, iConditions)), AlchemyRecipeProvider.this.recipePathProvider.json(resourceKey)));
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
        return Constants.MODID + " Alchemy Recipes";
    }
}
