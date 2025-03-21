package com.ryankshah.skyrimcraft.enchantment;

import com.ryankshah.skyrimcraft.registry.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class ShockingEnchantment extends Enchantment
{
    public ShockingEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentCategory.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinCost(int level)
    {
        return 2 + (level - 1) * 9;
    }

    @Override
    public int getMaxCost(int level)
    {
        return this.getMinCost(level) + 30;
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }
}