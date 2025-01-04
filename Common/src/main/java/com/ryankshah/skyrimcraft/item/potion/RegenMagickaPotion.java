package com.ryankshah.skyrimcraft.item.potion;

import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import com.ryankshah.skyrimcraft.registry.AttributeRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RegenMagickaPotion extends SkyrimPotion
{
    private float modifierValue;
    private int duration;

    public RegenMagickaPotion(Properties properties, float modifierValue, int duration) {
        super(properties);
        this.modifierValue = modifierValue;
        this.duration = duration;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerEntity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if (!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayer) {
                ServerPlayer serverPlayerEntity = (ServerPlayer) playerEntity;
                serverPlayerEntity.getAttribute(AttributeRegistry.MAGICKA_REGEN.get()).addTransientModifier(new AttributeModifier(AttributeRegistry.MODIFIER_ID_MAGICKA_REGEN.toString(), modifierValue, AttributeModifier.Operation.ADDITION));
                serverPlayerEntity.addEffect(new MobEffectInstance(ModEffects.MAGICKA_REGEN.get(), duration, 0, true, true, true));
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.create();
        if (this == ItemRegistry.LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALMON_ROE.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.GARLIC.get(), 1)));
        } else if(this == ItemRegistry.DRAUGHT_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.GARLIC.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALT_PILE.get(), 1)));
        } else if (this == ItemRegistry.SOLUTION_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_OIL.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALMON_ROE.get(), 1)));
        } else if (this == ItemRegistry.PHILTER_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.SALT_PILE.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_OIL.get(), 1)));
        } else if (this == ItemRegistry.ELIXIR_LASTING_POTENCY_POTION.get()) {
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.DWARVEN_OIL.get(), 1)));
            ingredients.add(Ingredient.of(new ItemStack(ItemRegistry.FIRE_SALTS.get(), 1)));
        }
        return ingredients;
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.REGENERATE_HEALTH;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String regenValue = "";

        Item item = pStack.getItem();
        if (ItemRegistry.LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "50%";
        } else if (ItemRegistry.DRAUGHT_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "60%";
        } else if (ItemRegistry.SOLUTION_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "70%";
        } else if (ItemRegistry.PHILTER_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "80%";
        } else if (ItemRegistry.ELIXIR_LASTING_POTENCY_POTION.get().equals(item)) {
            regenValue = "100%";
        }

        pTooltipComponents.add(Component.literal("Grants " + duration/20 + "s of " + regenValue + " magicka regen"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}