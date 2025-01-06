package com.ryankshah.skyrimcraft.data.loot_table;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

import java.util.Objects;
import java.util.function.Supplier;

public class AddTableLootModifier extends LootModifier
{
    public static final Supplier<Codec<AddTableLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create((instance) -> {
        return instance.group(IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter((glm) -> {
            return glm.conditions;
        }), ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("table").forGetter(AddTableLootModifier::table)).apply(instance, AddTableLootModifier::new);
    }));
    private final ResourceKey<LootTable> table;

    public AddTableLootModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> table) {
        super(conditionsIn);
        this.table = table;
    }

    public ResourceKey<LootTable> table() {
        return this.table;
    }

    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        context.getResolver().lookupOrThrow(Registries.LOOT_TABLE).get(this.table).ifPresent((extraTable) -> {
            LootTable var10000 = (LootTable)extraTable.value();
            ServerLevel var10002 = context.getLevel();
            Objects.requireNonNull(generatedLoot);
            var10000.getRandomItemsRaw(context, LootTable.createStackSplitter(var10002, generatedLoot::add));
        });
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}