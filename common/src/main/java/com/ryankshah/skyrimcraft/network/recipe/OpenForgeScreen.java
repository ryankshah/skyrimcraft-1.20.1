package com.ryankshah.skyrimcraft.network.recipe;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.ForgeRecipe;
import com.ryankshah.skyrimcraft.data.recipe.OvenRecipe;
import com.ryankshah.skyrimcraft.registry.RecipeRegistry;
import com.ryankshah.skyrimcraft.screen.BlacksmithForgeScreen;
import com.ryankshah.skyrimcraft.screen.OvenScreen;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record OpenForgeScreen()
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "openforgescreen");

    public OpenForgeScreen(final FriendlyByteBuf buffer) {
        this();
    }

    public static OpenForgeScreen decode(FriendlyByteBuf buf) {
        return new OpenForgeScreen();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static void handle(PacketContext<OpenForgeScreen> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }
    public static void handleServer(PacketContext<OpenForgeScreen> context) {
    }

    public static void handleClient(PacketContext<OpenForgeScreen> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            if(Minecraft.getInstance().player != null) {
                Player player = Minecraft.getInstance().player;
//                List<AlchemyRecipe> recipes = context.message().recipes();
                List<ForgeRecipe> recipes = player.level().getRecipeManager().getAllRecipesFor(RecipeRegistry.FORGE.get());
                Minecraft.getInstance().setScreen(new BlacksmithForgeScreen(recipes));
            }
        });
    }
}

