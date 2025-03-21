package com.ryankshah.skyrimcraft.data.recipe.serial;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.data.recipe.OvenRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class OvenRecipeSerializer implements RecipeSerializer<OvenRecipe>
{
    public OvenRecipeSerializer() {
    }

    @Override
    public OvenRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
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

        return new OvenRecipe(category, resultStack, xpGained, levelToCreate, ingredients);
    }

    @Override
    public OvenRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buf) {
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

        return new OvenRecipe(category, stackToCreate, xp, level, recipeItems);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, OvenRecipe ovenRecipe) {
        buf.writeUtf(ovenRecipe.getCategory());
        buf.writeInt(ovenRecipe.getRequiredLevel());
        buf.writeInt(ovenRecipe.getXpGained());
        buf.writeItem(ovenRecipe.getResult());

        buf.writeInt(ovenRecipe.getRecipeItems().size());
        for (Ingredient ing : ovenRecipe.getRecipeItems()) {
            buf.writeInt(ing.getItems().length);
            for(int i = 0; i < ing.getItems().length; i++) {
                buf.writeItem(ing.getItems()[i]);
            }
        }
    }
}