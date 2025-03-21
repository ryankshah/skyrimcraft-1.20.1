package com.ryankshah.skyrimcraft.data.loot_table;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.PearlOysterBlock;
import com.ryankshah.skyrimcraft.block.TallDoorBlock;
import com.ryankshah.skyrimcraft.block.util.TripleBlockPart;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SkyrimcraftBlockLootTables extends BlockLootSubProvider
{
    public SkyrimcraftBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        // normal ores
//        add(BlockRegistry.EXAMPLE_ORE.get(), createOreDrop(BlockRegistry.EXAMPLE_ORE.get(), ItemRegistry.RAW_EXAMPLE.get()));
//
//        // deepslate ores
//        add(BlockRegistry.DEEPSLATE_EXAMPLE_ORE.get(), createOreDrop(BlockRegistry.DEEPSLATE_EXAMPLE_ORE.get(), ItemRegistry.RAW_EXAMPLE.get()));

        dropSelf(BlockRegistry.CORUNDUM_ORE.get());
        dropSelf(BlockRegistry.EBONY_ORE.get());
        dropSelf(BlockRegistry.MALACHITE_ORE.get());
        dropSelf(BlockRegistry.MOONSTONE_ORE.get());
        dropSelf(BlockRegistry.ORICHALCUM_ORE.get());
        dropSelf(BlockRegistry.QUICKSILVER_ORE.get());
        dropSelf(BlockRegistry.SILVER_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_EBONY_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_MALACHITE_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get());
        dropSelf(BlockRegistry.DEEPSLATE_SILVER_ORE.get());
        dropSelf(BlockRegistry.SHOUT_BLOCK.get());
        dropSelf(BlockRegistry.ALCHEMY_TABLE.get());
        dropSelf(BlockRegistry.OVEN.get());
        dropSelf(BlockRegistry.BLACKSMITH_FORGE.get());
        dropSelf(BlockRegistry.WEAPON_RACK.get());
        dropSelf(BlockRegistry.ARCANE_ENCHANTER.get());
        dropSelf(BlockRegistry.TURN_STONE.get());
        dropSelf(BlockRegistry.RUNE_STONE.get());

        dropSelf(BlockRegistry.STONE_4.get());
        dropSelf(BlockRegistry.STONE_5.get());

        dropSelf(BlockRegistry.DWEMER_METAL_LADDER.get());

        dropSelf(BlockRegistry.RED_MOUNTAIN_FLOWER.get());
        dropSelf(BlockRegistry.BLUE_MOUNTAIN_FLOWER.get());
        dropSelf(BlockRegistry.YELLOW_MOUNTAIN_FLOWER.get());
        dropSelf(BlockRegistry.PURPLE_MOUNTAIN_FLOWER.get());
        dropOther(BlockRegistry.LAVENDER.get(), ItemRegistry.LAVENDER.get());

        this.add(
                BlockRegistry.JAZBAY_GRAPE_BUSH.get(),
                p_249159_ -> this.applyExplosionDecay(
                        p_249159_,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JAZBAY_GRAPE_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JAZBAY_GRAPES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JAZBAY_GRAPE_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JAZBAY_GRAPES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                )
                )
        );
        this.add(
                BlockRegistry.JUNIPER_BERRY_BUSH.get(),
                p_249159_ -> this.applyExplosionDecay(
                        p_249159_,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JUNIPER_BERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JUNIPER_BERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.JUNIPER_BERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.JUNIPER_BERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                )
                )
        );
        this.add(
                BlockRegistry.SNOWBERRY_BUSH.get(),
                p_249159_ -> this.applyExplosionDecay(
                        p_249159_,
                        LootTable.lootTable()
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SNOWBERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 3))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.SNOWBERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 3.0F)))
                                )
                                .withPool(
                                        LootPool.lootPool()
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.SNOWBERRY_BUSH.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(SweetBerryBushBlock.AGE, 2))
                                                )
                                                .add(LootItem.lootTableItem(ItemRegistry.SNOWBERRIES.get()))
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))
                                )
                )
        );

        dropSelf(BlockRegistry.CANIS_ROOT_BLOCK.get());
        dropSelf(BlockRegistry.BLEEDING_CROWN_BLOCK.get());
        dropSelf(BlockRegistry.WHITE_CAP_BLOCK.get());
        dropSelf(BlockRegistry.BLISTERWORT_BLOCK.get());
        dropSelf(BlockRegistry.FLY_AMANITA_BLOCK.get());
        dropSelf(BlockRegistry.CREEP_CLUSTER_BLOCK.get());

        LootItemCondition.Builder lootitemcondition$pearloyster = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.PEARL_OYSTER_BLOCK.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PearlOysterBlock.IS_OPEN, true))
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(PearlOysterBlock.IS_EMPTY, false));
        add(BlockRegistry.PEARL_OYSTER_BLOCK.get(), createCropDrops(BlockRegistry.PEARL_OYSTER_BLOCK.get(), ItemRegistry.PEARL.get(), BlockRegistry.PEARL_OYSTER_BLOCK.get().asItem(), lootitemcondition$pearloyster));

        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.TOMATO_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.TOMATO_CROP.get(), createCropDrops(BlockRegistry.TOMATO_CROP.get(), ItemRegistry.TOMATO.get(), ItemRegistry.TOMATO_SEEDS.get(), lootitemcondition$builder1));

        LootItemCondition.Builder ilootcondition$garliccrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.GARLIC_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.GARLIC_CROP.get(), createCropDrops(BlockRegistry.GARLIC_CROP.get(), ItemRegistry.GARLIC.get(), ItemRegistry.GARLIC.get(), ilootcondition$garliccrop));

        LootItemCondition.Builder ilootcondition$cabbagecrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.CABBAGE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.CABBAGE_CROP.get(), createCropDrops(BlockRegistry.CABBAGE_CROP.get(), ItemRegistry.CABBAGE.get(), ItemRegistry.CABBAGE_SEEDS.get(), ilootcondition$cabbagecrop));

        LootItemCondition.Builder ilootcondition$ashyamcrop = LootItemBlockStatePropertyCondition.hasBlockStateProperties(BlockRegistry.ASH_YAM_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        add(BlockRegistry.ASH_YAM_CROP.get(), createCropDrops(BlockRegistry.ASH_YAM_CROP.get(), ItemRegistry.ASH_YAM.get(), ItemRegistry.ASH_YAM_SLIPS.get(), ilootcondition$ashyamcrop));


        LootTable.Builder birdsNestDrops = LootTable.lootTable().withPool(LootPool.lootPool()
                        .name("birdsNestPool")
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ItemRegistry.HAWK_EGG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                        )
                        .add(LootItem.lootTableItem(ItemRegistry.PINE_THRUSH_EGG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                        )
                        .add(LootItem.lootTableItem(ItemRegistry.ROCK_WARBLER_EGG.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                        )
                        .add(LootItem.lootTableItem(Items.EGG))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.25F, 1.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                        );
        add(BlockRegistry.BIRDS_NEST.get(), birdsNestDrops);

        dropSelf(BlockRegistry.STEEL_TRAPDOOR.get());
        dropSelf(BlockRegistry.DWEMER_METAL_TRAPDOOR.get());
        add(BlockRegistry.STEEL_CELL_DOOR.get(), this::createDoorTable);
        add(BlockRegistry.STEEL_GATE_DOOR.get(), this::createDoorTable);
        add(BlockRegistry.STEEL_TALL_GATE.get(), this::createTallDoorTable);
        dropSelf(BlockRegistry.DWEMER_SOUL_TORCH.get());
        dropOther(BlockRegistry.DWEMER_SOUL_WALL_TORCH.get(), BlockRegistry.DWEMER_SOUL_TORCH.get());
        dropSelf(BlockRegistry.DWEMER_METAL_TORCH.get());
        dropOther(BlockRegistry.DWEMER_METAL_WALL_TORCH.get(), BlockRegistry.DWEMER_METAL_TORCH.get());
        dropSelf(BlockRegistry.DWEMER_REDSTONE_TORCH.get());
        dropOther(BlockRegistry.DWEMER_REDSTONE_WALL_TORCH.get(), BlockRegistry.DWEMER_REDSTONE_TORCH.get());
        dropSelf(BlockRegistry.DWEMER_REPEATER.get());
        dropSelf(BlockRegistry.DWEMER_COMPARATOR.get());
        dropSelf(BlockRegistry.DWEMER_METAL_CHAIN.get());
        dropSelf(BlockRegistry.DWEMER_METAL_BUTTON.get());
        dropSelf(BlockRegistry.DWEMER_METAL_LEVER.get());
        dropSelf(BlockRegistry.DWEMER_STONE_BUTTON.get());
        dropSelf(BlockRegistry.DWEMER_CHAIR.get());
        add(BlockRegistry.DWEMER_METAL_DOOR.get(), this::createDoorTable);
        add(BlockRegistry.DWEMER_METAL_TALL_DOOR.get(), this::createTallDoorTable);
        add(BlockRegistry.DWEMER_METAL_TALL_GATE.get(), this::createTallDoorTable);
        add(BlockRegistry.DWEMER_METAL_GATE.get(), this::createDoorTable);
        dropSelf(BlockRegistry.DWEMER_METAL_BARS.get());
        dropSelf(BlockRegistry.STEEL_BARS.get());
        dropSelf(BlockRegistry.DWEMER_METAL_PILLAR.get());
        dropSelf(BlockRegistry.DWEMER_STONE_PILLAR.get());
        dropSelf(BlockRegistry.DWEMER_METAL_TILES.get());
        dropSelf(BlockRegistry.DWEMER_METAL_TILE_STAIRS.get());
        add(BlockRegistry.DWEMER_METAL_TILE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockRegistry.ORNATE_DWEMER_METAL_TILES.get());
        dropSelf(BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS.get());
        add(BlockRegistry.ORNATE_DWEMER_METAL_TILE_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockRegistry.DWEMER_METAL_BLOCK.get());
        dropSelf(BlockRegistry.DWEMER_METAL_BRICKS.get());
        dropSelf(BlockRegistry.DWEMER_METAL_BRICK_STAIRS.get());
        dropSelf(BlockRegistry.DWEMER_METAL_BRICK_WALL.get());
        dropSelf(BlockRegistry.DWEMER_STONE_BRICK_SLAB.get());
        add(BlockRegistry.DWEMER_METAL_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockRegistry.DWEMER_STONE_BLOCK.get());
        dropSelf(BlockRegistry.DWEMER_STONE_BRICKS.get());
        dropSelf(BlockRegistry.DWEMER_STONE_BRICK_WALL.get());
        dropSelf(BlockRegistry.DWEMER_STONE_BRICK_STAIRS.get());
        add(BlockRegistry.DWEMER_STONE_BRICK_SLAB.get(), this::createSlabItemTable);
        dropSelf(BlockRegistry.DWEMER_STONE_PRESSURE_PLATE.get());
        dropSelf(BlockRegistry.DWEMER_OBSERVER.get());
        dropSelf(BlockRegistry.DWEMER_DISPENSER.get());
        dropSelf(BlockRegistry.DWEMER_DROPPER.get());
        dropSelf(BlockRegistry.DWEMER_DAYLIGHT_DETECTOR.get());

        dropSelf(BlockRegistry.DWEMER_METAL_LANTERN.get());
        dropSelf(BlockRegistry.DWEMER_SOUL_LANTERN.get());

        dropSelf(BlockRegistry.DWEMER_GLASS.get());
        dropSelf(BlockRegistry.DWEMER_FRAMED_GLASS.get());
        dropSelf(BlockRegistry.DWEMER_WINDOWED_GLASS.get());

        dropSelf(BlockRegistry.DWEMER_GLASS_PANE.get());
        dropSelf(BlockRegistry.DWEMER_FRAMED_GLASS_PANE.get());
        dropSelf(BlockRegistry.DWEMER_WINDOWED_GLASS_PANE.get());

        dropSelf(BlockRegistry.DWEMER_REDSTONE_LAMP.get());
        dropSelf(BlockRegistry.DWEMER_REDSTONE_SOUL_LAMP.get());

        dropSelf(BlockRegistry.DWEMER_PISTON.get());
        dropSelf(BlockRegistry.DWEMER_STICKY_PISTON.get());


        dropSelf(BlockRegistry.DWEMER_WOOL_BROWN.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_BROWN_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_ORANGE.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_ORANGE_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_RED.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_RED_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_MAGENTA.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_MAGENTA_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_PINK.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_PINK_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_PURPLE.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_PURPLE_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_BLUE.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_BLUE_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_LIGHT_BLUE.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_LIGHT_BLUE_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_CYAN.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_CYAN_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_GREEN.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_GREEN_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_LIME_GREEN.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_LIME_GREEN_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_YELLOW.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_YELLOW_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_BLACK.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_BLACK_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_GREY.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_GREY_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_LIGHT_GREY.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_LIGHT_GREY_CARPET.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_WHITE.get());
        dropSelf(BlockRegistry.DWEMER_WOOL_WHITE_CARPET.get());

        dropSelf(BlockRegistry.DWEMER_BED_BROWN.get());
        dropSelf(BlockRegistry.DWEMER_BED_ORANGE.get());
    }

    protected LootTable.Builder createTallDoorTable(Block doorBlock) {
        return this.createSinglePropConditionTable(doorBlock, TallDoorBlock.THIRD, TripleBlockPart.LOWER);
    }

    @Override
    public @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> Optional.of(BuiltInRegistries.BLOCK.getKey(block))
                        .filter(key -> key.getNamespace().equals(Constants.MODID))
                        .isPresent())
                .collect(Collectors.toSet());
    }
}