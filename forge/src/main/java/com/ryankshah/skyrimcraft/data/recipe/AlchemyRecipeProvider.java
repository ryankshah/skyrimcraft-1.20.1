package com.ryankshah.skyrimcraft.data.recipe;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.AlchemyRecipe;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.item.potion.IPotion;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.registry.RecipeRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class AlchemyRecipeProvider implements DataProvider, IConditionBuilder
{
    protected final PackOutput.PathProvider recipePathProvider;
    protected final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public AlchemyRecipeProvider(PackOutput p_248933_, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.recipePathProvider = p_248933_.createPathProvider(PackOutput.Target.DATA_PACK, "recipes");
        this.lookupProvider = lookupProvider;
    }

    protected void buildRecipes(Consumer<FinishedRecipe> pRecipeOutput) {
        List<SkyrimPotion> potions = ItemRegistry.ITEMS.getEntries().stream().filter(item -> item.get() instanceof SkyrimPotion).map(item -> (SkyrimPotion)item.get()).toList();
        for(SkyrimPotion potion : potions) {
            if(potion.getCategory() == IPotion.PotionCategory.UNIQUE || potion.getIngredients().isEmpty())
                continue;

            NonNullList<Ingredient> ings = potion.getIngredients();
            AlchemyRecipe recipe = new AlchemyRecipe(potion.getCategory().toString(), new ItemStack(potion, 1), 2, 1, ings);

            pRecipeOutput.accept(new FinishedRecipe() {
                @Override
                public void serializeRecipeData(JsonObject p_125967_) {
                    p_125967_.addProperty("type", Constants.MODID+":alchemy");

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
                    return new ResourceLocation(Constants.MODID, "alchemy/" + BuiltInRegistries.ITEM.getKey(potion).getPath());
                }

                @Override
                public RecipeSerializer<?> getType() {
                    return RecipeRegistry.ALCHEMY_RECIPE_SERIALIZER.get();
                }

                @Override
                public @Nullable JsonObject serializeAdvancement() {
                    return null;
                }

                @Override
                public @Nullable ResourceLocation getAdvancementId() {
                    return null;
                }
            });
        }
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
        return Constants.MODID + " Alchemy Recipes";
    }
}
