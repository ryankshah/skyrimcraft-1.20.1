package com.ryankshah.skyrimcraft.data.loot_table;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.Constants;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyrimLootModifiers
{
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MODID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_TABLE_LOOT_MODIFIER_TYPE = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_table", () -> AddTableLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_DUNGEON_LOOT_MODIFIER_TYPE = GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("dungeon_loot", DungeonLootEnhancerModifier.CODEC);

}
