package com.ryankshah.skyrimcraft.network.recipe;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.AlchemyRecipe;
import com.ryankshah.skyrimcraft.data.recipe.OvenRecipe;
import com.ryankshah.skyrimcraft.registry.RecipeRegistry;
import com.ryankshah.skyrimcraft.screen.AlchemyScreen;
import com.ryankshah.skyrimcraft.screen.OvenScreen;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record OpenOvenScreen()
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "openovenscreen");

    public OpenOvenScreen(final FriendlyByteBuf buffer) {
        this();
    }

    public static OpenOvenScreen decode(FriendlyByteBuf buf) {
        return new OpenOvenScreen();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static void handle(PacketContext<OpenOvenScreen> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }
    public static void handleServer(PacketContext<OpenOvenScreen> context) {
    }

    public static void handleClient(PacketContext<OpenOvenScreen> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            if(Minecraft.getInstance().player != null) {
                Player player = Minecraft.getInstance().player;
//                List<AlchemyRecipe> recipes = context.message().recipes();
                List<OvenRecipe> recipes = player.level().getRecipeManager().getAllRecipesFor(RecipeRegistry.OVEN.get());
                Minecraft.getInstance().setScreen(new OvenScreen(recipes));
            }
        });
    }
}

