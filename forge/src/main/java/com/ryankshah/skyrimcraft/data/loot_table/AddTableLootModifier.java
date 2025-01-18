package com.ryankshah.skyrimcraft.data.loot_table;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.ApiStatus;

public class AddTableLootModifier extends LootModifier {
    @ApiStatus.Internal
    public static final Codec<AddTableLootModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(glm -> glm.conditions),
                    ResourceLocation.CODEC.fieldOf("table").forGetter(AddTableLootModifier::table))
            .apply(instance, AddTableLootModifier::new)
    );

    private final ResourceLocation table;

    protected AddTableLootModifier(LootItemCondition[] conditionsIn, ResourceLocation table) {
        super(conditionsIn);
        this.table = table;
    }

    public ResourceLocation table() {
        return this.table;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        LootTable extraTable = context.getResolver().getLootTable(this.table);
        // Don't run loot modifiers for subtables;
        // the added loot will be modifiable by downstream loot modifiers modifying the target table,
        // so if we modify it here then it could get modified twice.
        extraTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return SkyrimLootModifiers.ADD_TABLE_LOOT_MODIFIER_TYPE.get();
    }
}