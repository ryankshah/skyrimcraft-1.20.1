package com.ryankshah.skyrimcraft.item.potion;

import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.item.SkyrimPotion;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CureDiseasesPotion extends SkyrimPotion
{
    private int duration;

    public CureDiseasesPotion(Properties properties, int duration) {
        super(properties);
        this.duration = duration;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
        Player playerEntity = entityLiving instanceof Player ? (Player) entityLiving : null;

        if(!worldIn.isClientSide) {
            if(playerEntity instanceof ServerPlayer) {
                playerEntity.addEffect(new MobEffectInstance(ModEffects.CURE_DISEASE.get(), duration, 0, true, false, false));
            }
        }

        return super.finishUsingItem(stack, worldIn, entityLiving);
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.create();
    }

    @Override
    public PotionCategory getCategory() {
        return PotionCategory.UNIQUE;
    }

    @Override
    public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
        $$2.add(Component.literal("Cures all diseases"));
        super.appendHoverText($$0, $$1, $$2, $$3);
    }
}