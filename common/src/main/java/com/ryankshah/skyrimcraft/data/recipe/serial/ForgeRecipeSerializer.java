package com.ryankshah.skyrimcraft.data.recipe.serial;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.data.recipe.ForgeRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ForgeRecipeSerializer implements RecipeSerializer<ForgeRecipe>
{
    @Override
    public ForgeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        String category = GsonHelper.getAsString(pSerializedRecipe, "category");
        int levelToCreate = GsonHelper.getAsInt(pSerializedRecipe, "levelToCreate", 0);
        int xpGained = GsonHelper.getAsInt(pSerializedRecipe, "xp", 0);
        JsonObject outputObject = GsonHelper.getAsJsonObject(pSerializedRecipe, "output");
        String outputItem = GsonHelper.getAsString(outputObject, "item");
        int outputAmount = GsonHelper.getAsInt(outputObject, "amount", 1);

        ResourceLocation outputItemId = new ResourceLocation(outputItem);
        ItemStack resultStack = new ItemStack(BuiltInRegistries.ITEM.get(outputItemId), outputAmount);

        JsonArray recipeArray = GsonHelper.getAsJsonArray(pSerializedRecipe, "recipe");
        NonNullList<Ingredient> ingredients = NonNullList.create();

        for (JsonElement element : recipeArray) {
            JsonObject ingredientObject = element.getAsJsonObject();
            String itemName = GsonHelper.getAsString(ingredientObject, "item");
            int amount = GsonHelper.getAsInt(ingredientObject, "amount", 1);

            ResourceLocation itemId = new ResourceLocation(itemName);
            ItemStack ingredientStack = new ItemStack(BuiltInRegistries.ITEM.get(itemId), amount);
            ingredients.add(Ingredient.of(ingredientStack));
        }

        return new ForgeRecipe(category, resultStack, xpGained, levelToCreate, ingredients);
    }

    @Override
    public ForgeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buf) {
        String category = buf.readUtf();
        int level = buf.readInt();
        int xp = buf.readInt();
        ItemStack stackToCreate = buf.readItem();

        NonNullList<Ingredient> recipeItems = NonNullList.create();
        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            int ingLen = buf.readInt();
            ItemStack[] stacks = new ItemStack[ingLen];
            for(int j = 0; j < ingLen; j++) {
                stacks[j] = buf.readItem();
            }
            recipeItems.add(Ingredient.of(stacks));
        }

        return new ForgeRecipe(category, stackToCreate, xp, level, recipeItems);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ForgeRecipe forgeRecipe) {
        buf.writeUtf(forgeRecipe.getCategory());
        buf.writeInt(forgeRecipe.getRequiredLevel());
        buf.writeInt(forgeRecipe.getXpGained());
        buf.writeItem(forgeRecipe.getResult());

        buf.writeInt(forgeRecipe.getRecipeItems().size());
        for (Ingredient ing : forgeRecipe.getRecipeItems()) {
            buf.writeInt(ing.getItems().length);
            for(int i = 0; i < ing.getItems().length; i++) {
                buf.writeItem(ing.getItems()[i]);
            }
        }
    }
}