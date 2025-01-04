package com.ryankshah.skyrimcraft.data.recipe.serial;

import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.data.recipe.ForgeRecipe;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ForgeRecipeSerializer implements RecipeSerializer<ForgeRecipe>
{
    @Override
    public ForgeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
        return null;
    }

    @Override
    public ForgeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf buf) {
        NonNullList<Ingredient> recipeItems = NonNullList.create();
        String category = buf.readUtf();
        ItemStack stackToCreate = buf.readItem();

        int length = buf.readInt();
        for (int i = 0; i < length; i++) {
            int ingLen = buf.readInt();
            ItemStack[] stacks = new ItemStack[ingLen];
            for(int j = 0; j < ingLen; j++)
                stacks[i] = buf.readItem();
            recipeItems.add(Ingredient.of(stacks));
        }

        int level = buf.readInt();
        int xp = buf.readInt();

        return new ForgeRecipe(category, stackToCreate, xp, level, recipeItems);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ForgeRecipe alchemyRecipe) {
        buf.writeUtf(alchemyRecipe.getCategory());

        if (alchemyRecipe.getResult() != null) {
            buf.writeItem(alchemyRecipe.getResult());
        }

        if (alchemyRecipe.getRecipeItems() != null && !alchemyRecipe.getRecipeItems().isEmpty()) {
            buf.writeInt(alchemyRecipe.getRecipeItems().size());
            for (Ingredient ing : alchemyRecipe.getRecipeItems()) {
                buf.writeInt(ing.getItems().length);
                for(int i = 0; i < ing.getItems().length; i++)
                    buf.writeItem(ing.getItems()[i]);
            }
        }

        buf.writeInt(alchemyRecipe.getRequiredLevel());
        buf.writeInt(alchemyRecipe.getXpGained());
    }
}