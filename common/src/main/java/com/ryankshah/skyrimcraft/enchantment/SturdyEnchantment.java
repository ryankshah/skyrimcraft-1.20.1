package com.ryankshah.skyrimcraft.enchantment;

import com.ryankshah.skyrimcraft.registry.EnchantmentRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SturdyEnchantment extends Enchantment
{
    public SturdyEnchantment()
    {
        super(Rarity.RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[] { EquipmentSlot.MAINHAND });
    }

    @Override
    public int getMinCost(int level)
    {
        return 5 + (level - 1) * 15;
    }

    @Override
    public int getMaxCost(int level)
    {
        return 50;
    }

    @Override
    public int getMaxLevel()
    {
        return 3;
    }
}