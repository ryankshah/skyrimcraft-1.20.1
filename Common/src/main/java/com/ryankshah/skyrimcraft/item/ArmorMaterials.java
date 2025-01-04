package com.ryankshah.skyrimcraft.item;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.function.Supplier;

public enum ArmorMaterials implements StringRepresentable, ArmorMaterial
{
    ANCIENT_NORD(Constants.MODID+":ancient_nord", 15,
        Util.make(new EnumMap<>(ArmorItem.Type.class), p_266652_ -> {
            p_266652_.put(ArmorItem.Type.BOOTS, 2);
            p_266652_.put(ArmorItem.Type.LEGGINGS, 4);
            p_266652_.put(ArmorItem.Type.CHESTPLATE, 5);
            p_266652_.put(ArmorItem.Type.HELMET, 2);
        }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0.5F, 0.125F, () -> Ingredient.of(Items.IRON_INGOT)),
    DRAGONBONE(Constants.MODID+":dragonbone", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266652_ -> {
        p_266652_.put(ArmorItem.Type.BOOTS, 2);
        p_266652_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266652_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266652_.put(ArmorItem.Type.HELMET, 2);
    }), 15, SoundEvents.ARMOR_EQUIP_GENERIC, 0.0F, 0.25F, () -> Ingredient.of(Items.IRON_INGOT)),
    HIDE(Constants.MODID+":hide", 5, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266652_ -> {
        p_266652_.put(ArmorItem.Type.BOOTS, 1);
        p_266652_.put(ArmorItem.Type.LEGGINGS, 2);
        p_266652_.put(ArmorItem.Type.CHESTPLATE, 3);
        p_266652_.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ItemRegistry.LEATHER_STRIPS.get())),
    SCALED(Constants.MODID+":scaled", 8, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266652_ -> {
        p_266652_.put(ArmorItem.Type.BOOTS, 1);
        p_266652_.put(ArmorItem.Type.LEGGINGS, 2);
        p_266652_.put(ArmorItem.Type.CHESTPLATE, 3);
        p_266652_.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(ItemRegistry.STEEL_INGOT.get())),
    STEEL(Constants.MODID+":steel", 22, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266654_ -> {
        p_266654_.put(ArmorItem.Type.BOOTS, 2);
        p_266654_.put(ArmorItem.Type.LEGGINGS, 6);
        p_266654_.put(ArmorItem.Type.CHESTPLATE, 7);
        p_266654_.put(ArmorItem.Type.HELMET, 3);
    }), 10, SoundEvents.ARMOR_EQUIP_IRON, 0.1F, 0.0F, () -> Ingredient.of(ItemRegistry.STEEL_INGOT.get())),
    GLASS(Constants.MODID+":glass", 20, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266654_ -> {
        p_266654_.put(ArmorItem.Type.BOOTS, 2);
        p_266654_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266654_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266654_.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0.1F, 0.0F, () -> Ingredient.of(ItemRegistry.MALACHITE_INGOT.get())),
    ELVEN(Constants.MODID+":elven", 22, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266654_ -> {
        p_266654_.put(ArmorItem.Type.BOOTS, 2);
        p_266654_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266654_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266654_.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0.2F, 0.1F, () -> Ingredient.of(ItemRegistry.MOONSTONE_INGOT.get())),
    DAEDRIC(Constants.MODID+":daedric", 45, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266655_ -> {
        p_266655_.put(ArmorItem.Type.BOOTS, 4);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 8);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 8);
        p_266655_.put(ArmorItem.Type.HELMET, 4);
    }), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 4.0F, 0.25F, () -> Ingredient.of(ItemRegistry.EBONY_INGOT.get())),
    EBONY(Constants.MODID+":ebony", 37, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266655_ -> {
        p_266655_.put(ArmorItem.Type.BOOTS, 3);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 6);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 8);
        p_266655_.put(ArmorItem.Type.HELMET, 3);
    }), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, () -> Ingredient.of(ItemRegistry.EBONY_INGOT.get())),
    ORCISH(Constants.MODID+":orcish", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266654_ -> {
        p_266654_.put(ArmorItem.Type.BOOTS, 2);
        p_266654_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266654_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266654_.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0.1F, 0.0F, () -> Ingredient.of(ItemRegistry.ORICHALCUM_INGOT.get())),
    DWARVEN(Constants.MODID+":dwarven", 33, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266654_ -> {
        p_266654_.put(ArmorItem.Type.BOOTS, 2);
        p_266654_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266654_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266654_.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_GENERIC, 0.1F, 0.0F, () -> Ingredient.of(ItemRegistry.DWARVEN_METAL_INGOT.get())),
    IRON(Constants.MODID+":iron", 15, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266654_ -> {
        p_266654_.put(ArmorItem.Type.BOOTS, 2);
        p_266654_.put(ArmorItem.Type.LEGGINGS, 5);
        p_266654_.put(ArmorItem.Type.CHESTPLATE, 6);
        p_266654_.put(ArmorItem.Type.HELMET, 2);
    }), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, () -> Ingredient.of(Items.IRON_INGOT)),
    STORMCLOAK(Constants.MODID+":stormcloak", 8, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266652_ -> {
        p_266652_.put(ArmorItem.Type.BOOTS, 1);
        p_266652_.put(ArmorItem.Type.LEGGINGS, 2);
        p_266652_.put(ArmorItem.Type.CHESTPLATE, 3);
        p_266652_.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY),
    IMPERIAL(Constants.MODID+":imperial", 8, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266652_ -> {
        p_266652_.put(ArmorItem.Type.BOOTS, 1);
        p_266652_.put(ArmorItem.Type.LEGGINGS, 2);
        p_266652_.put(ArmorItem.Type.CHESTPLATE, 3);
        p_266652_.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY),
    FALMER(Constants.MODID+":falmer", 8, Util.make(new EnumMap<>(ArmorItem.Type.class), p_266652_ -> {
        p_266652_.put(ArmorItem.Type.BOOTS, 1);
        p_266652_.put(ArmorItem.Type.LEGGINGS, 2);
        p_266652_.put(ArmorItem.Type.CHESTPLATE, 3);
        p_266652_.put(ArmorItem.Type.HELMET, 1);
    }), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.EMPTY);

    public static final StringRepresentable.EnumCodec<ArmorMaterials> CODEC = StringRepresentable.fromEnum(ArmorMaterials::values);
    private static final EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
        p_266653_.put(ArmorItem.Type.BOOTS, 13);
        p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
        p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
        p_266653_.put(ArmorItem.Type.HELMET, 11);
    });

    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    private ArmorMaterials(String pName, int pDurabilityMultiplier, EnumMap<ArmorItem.Type, Integer> pProtectionFunctionForType, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient) {
        this.name = pName;
        this.durabilityMultiplier = pDurabilityMultiplier;
        this.protectionFunctionForType = pProtectionFunctionForType;
        this.enchantmentValue = pEnchantmentValue;
        this.sound = pSound;
        this.toughness = pToughness;
        this.knockbackResistance = pKnockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
    }

    public int getDurabilityForType(ArmorItem.Type pType) {
        return HEALTH_FUNCTION_FOR_TYPE.get(pType) * this.durabilityMultiplier;
    }

    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionFunctionForType.get(pType);
    }

    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    public SoundEvent getEquipSound() {
        return this.sound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }

    public String getSerializedName() {
        return this.name;
    }
}