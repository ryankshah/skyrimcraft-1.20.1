package com.ryankshah.skyrimcraft.network.recipe;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.data.recipe.AlchemyRecipe;
import com.ryankshah.skyrimcraft.registry.RecipeRegistry;
import com.ryankshah.skyrimcraft.screen.AlchemyScreen;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record OpenAlchemyScreen()
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "openalchemyscreen");

    public OpenAlchemyScreen(final FriendlyByteBuf buffer) {
        this();
    }

    public static OpenAlchemyScreen decode(FriendlyByteBuf buf) {
        return new OpenAlchemyScreen();
    }

    public void encode(FriendlyByteBuf buf) {
    }

    public static void handle(PacketContext<OpenAlchemyScreen> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }
    public static void handleServer(PacketContext<OpenAlchemyScreen> context) {
    }

    public static void handleClient(PacketContext<OpenAlchemyScreen> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            if(Minecraft.getInstance().player != null) {
                Player player = Minecraft.getInstance().player;
                List<AlchemyRecipe> recipes = player.level().getRecipeManager().getAllRecipesFor(RecipeRegistry.ALCHEMY.get());
                Minecraft.getInstance().setScreen(new AlchemyScreen(recipes));
            }
        });
    }
}

