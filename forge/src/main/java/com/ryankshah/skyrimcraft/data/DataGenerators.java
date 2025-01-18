package com.ryankshah.skyrimcraft.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.advancement.SkyrimAdvancementProvider;
import com.ryankshah.skyrimcraft.data.block.SkyrimcraftBlockStateProvider;
import com.ryankshah.skyrimcraft.data.block.SkyrimcraftBlockTagsProvider;
import com.ryankshah.skyrimcraft.data.item.SkyrimcraftItemModelProvider;
import com.ryankshah.skyrimcraft.data.item.SkyrimcraftItemTagProvider;
import com.ryankshah.skyrimcraft.data.lang.SkyrimcraftLanguageProvider;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimLootModifierProvider;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimcraftLootTables;
import com.ryankshah.skyrimcraft.data.recipe.AlchemyRecipe;
import com.ryankshah.skyrimcraft.data.recipe.Provider;
import com.ryankshah.skyrimcraft.data.recipe.SkyrimcraftRecipeProvider;
import com.ryankshah.skyrimcraft.data.sound.SkyrimSoundsProvider;
import com.ryankshah.skyrimcraft.data.tag.SkyrimcraftPoiTypeTagsProvider;
import com.ryankshah.skyrimcraft.data.world.SkyrimBiomeTagsProvider;
import com.ryankshah.skyrimcraft.data.world.SkyrimcraftStructureTagsProvider;
import com.ryankshah.skyrimcraft.data.world.SkyrimcraftWorldGenProvider;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.item.potion.IPotion;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.registry.RecipeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DataGenerators
{
    public static void gatherData(GatherDataEvent event) {
        try {
            DataGenerator generator = event.getGenerator();
            PackOutput output = generator.getPackOutput();
            ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

            generator.addProvider(true, new SkyrimAdvancementProvider(output, event.getLookupProvider(), existingFileHelper, List.of(new SkyrimAdvancementProvider.SkyrimAdvancements())));
            generator.addProvider(true, new SkyrimcraftLanguageProvider(output, Constants.MODID, "en_us"));
            generator.addProvider(true, new SkyrimBiomeTagsProvider(output, event.getLookupProvider(), Constants.MODID, existingFileHelper));
            generator.addProvider(true, new SkyrimcraftItemModelProvider(output, existingFileHelper));
            generator.addProvider(true, new SkyrimcraftBlockStateProvider(output, Constants.MODID, existingFileHelper));
            generator.addProvider(true, new SkyrimcraftLootTables(output, event.getLookupProvider()));
            generator.addProvider(true, new SkyrimcraftRecipeProvider(output, event.getLookupProvider()));
            SkyrimcraftBlockTagsProvider blockTagsProvider = new SkyrimcraftBlockTagsProvider(output, event.getLookupProvider(), Constants.MODID, existingFileHelper);
            generator.addProvider(true, blockTagsProvider);
            generator.addProvider(true, new SkyrimcraftItemTagProvider(output, event.getLookupProvider(), blockTagsProvider, existingFileHelper));
            generator.addProvider(event.includeServer(), new SkyrimcraftStructureTagsProvider(output, event.getLookupProvider(), Constants.MODID, existingFileHelper));
            generator.addProvider(event.includeServer(), new SkyrimcraftWorldGenProvider(output, event.getLookupProvider()));
//            generator.addProvider(event.includeServer(), new SkyrimCuriosDataProvider(Constants.MODID, output,existingFileHelper, event.getLookupProvider()));
            generator.addProvider(true, new SkyrimLootModifierProvider(output, event.getLookupProvider(), Constants.MODID));
            generator.addProvider(event.includeClient(), new SkyrimSoundsProvider(output, Constants.MODID, existingFileHelper));
            generator.addProvider(event.includeServer(), new SkyrimcraftPoiTypeTagsProvider(output, event.getLookupProvider(), existingFileHelper));

            //Alchemy recipes
            Provider<FinishedRecipe> alchemyRecipes = Provider.create(event, PackOutput.Target.DATA_PACK, "recipes", FinishedRecipe::serializeRecipe);
            List<SkyrimPotion> potions = ItemRegistry.ITEMS.getEntries().stream().filter(item -> item.get() instanceof SkyrimPotion).map(item -> (SkyrimPotion)item.get()).toList();
            for(SkyrimPotion potion : potions) {
                if(potion.getCategory() == IPotion.PotionCategory.UNIQUE || potion.getIngredients().isEmpty())
                    continue;

                NonNullList<Ingredient> ings = potion.getIngredients();
                AlchemyRecipe recipe = new AlchemyRecipe(potion.getCategory().toString(), new ItemStack(potion, 1), 2, 1, ings);

                alchemyRecipes.put(new ResourceLocation(Constants.MODID + ":alchemy/" + BuiltInRegistries.ITEM.getKey(potion).getPath()), new FinishedRecipe() {
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
        } catch (RuntimeException e) {
            Constants.LOG.error("Failed to generate data", e);
        }
    }
}