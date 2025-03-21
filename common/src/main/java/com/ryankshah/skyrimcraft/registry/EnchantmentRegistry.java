package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.enchantment.ComplexityEnchantment;
import com.ryankshah.skyrimcraft.enchantment.ShockingEnchantment;
import com.ryankshah.skyrimcraft.enchantment.SturdyEnchantment;
import com.ryankshah.skyrimcraft.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.registration.RegistryObject;
import commonnetwork.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentRegistry
{
    public static void init() {}

//    public static final EnchantmentCategory LOCK_TYPE = EnchantmentCategory.create("LOCK", item -> item instanceof LockItem);

    public static final RegistrationProvider<Enchantment> ENCHANTMENTS = RegistrationProvider.get(BuiltInRegistries.ENCHANTMENT, Constants.MOD_ID);
    public static final RegistryObject<Enchantment>
            SHOCKING = add("shocking", new ShockingEnchantment()),
            STURDY = add("sturdy", new SturdyEnchantment()),
            COMPLEXITY = add("complexity", new ComplexityEnchantment());

    public static RegistryObject<Enchantment> add(String name, Enchantment ench) {
        return ENCHANTMENTS.register(name, () -> ench);
    }
}