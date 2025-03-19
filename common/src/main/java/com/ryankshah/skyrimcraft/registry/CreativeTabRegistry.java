package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;

import java.util.Comparator;

public class CreativeTabRegistry
{
    public static void init() {}

    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MODID);

    // tab title
    public static String SKYRIMCRAFT_BLOCK_TAB_TITLE = "creativetab.skyrimcraft.blocks";
    public static String SKYRIMCRAFT_INGREDIENT_TAB_TITLE = "creativetab.skyrimcraft.ingredients";
    public static String SKYRIMCRAFT_FOOD_TAB_TITLE = "creativetab.skyrimcraft.food";
    public static String SKYRIMCRAFT_COMBAT_TAB_TITLE = "creativetab.skyrimcraft.combat";
    public static String SKYRIMCRAFT_MAGIC_TITLE = "creativetab.skyrimcraft.magic";
    public static String SKYRIMCRAFT_JEWELLERY_TAB_TITLE = "creativetab.skyrimcraft.jewellery";
    public static String SKYRIMCRAFT_ALL_TITLE = "creativetab.skyrimcraft.all";

    public static final RegistryObject<CreativeModeTab> SKYRIMCRAFT_BLOCKS_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_blocks_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            output.accept(BlockRegistry.CORUNDUM_ORE_ITEM.get());
            output.accept(BlockRegistry.DEEPSLATE_CORUNDUM_ORE_ITEM.get());
            output.accept(BlockRegistry.EBONY_ORE_ITEM.get());
            output.accept(BlockRegistry.DEEPSLATE_EBONY_ORE_ITEM.get());
            output.accept(BlockRegistry.MALACHITE_ORE_ITEM.get());
            output.accept(BlockRegistry.DEEPSLATE_MALACHITE_ORE_ITEM.get());
            output.accept(BlockRegistry.MOONSTONE_ORE_ITEM.get());
            output.accept(BlockRegistry.DEEPSLATE_MOONSTONE_ORE_ITEM.get());
            output.accept(BlockRegistry.ORICHALCUM_ORE_ITEM.get());
            output.accept(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE_ITEM.get());
            output.accept(BlockRegistry.QUICKSILVER_ORE_ITEM.get());
            output.accept(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE_ITEM.get());
            output.accept(BlockRegistry.SILVER_ORE_ITEM.get());
            output.accept(BlockRegistry.DEEPSLATE_SILVER_ORE_ITEM.get());

            output.accept(BlockRegistry.STONE_4_ITEM.get());
            output.accept(BlockRegistry.STONE_5_ITEM.get());

            output.accept(BlockRegistry.TURN_STONE_ITEM.get());
            output.accept(BlockRegistry.RUNE_STONE_ITEM.get());

            output.accept(BlockRegistry.SHOUT_BLOCK_ITEM.get());

            output.accept(BlockRegistry.BIRDS_NEST_ITEM.get());

            output.accept(BlockRegistry.ALCHEMY_TABLE_ITEM.get());
            output.accept(BlockRegistry.OVEN_ITEM.get());
            output.accept(BlockRegistry.BLACKSMITH_FORGE_ITEM.get());
            output.accept(BlockRegistry.WEAPON_RACK_ITEM.get());
            output.accept(BlockRegistry.ARCANE_ENCHANTER_ITEM.get());

            output.accept(BlockRegistry.RED_MOUNTAIN_FLOWER_ITEM.get());
            output.accept(BlockRegistry.BLUE_MOUNTAIN_FLOWER_ITEM.get());
            output.accept(BlockRegistry.YELLOW_MOUNTAIN_FLOWER_ITEM.get());
            output.accept(BlockRegistry.PURPLE_MOUNTAIN_FLOWER_ITEM.get());
            output.accept(BlockRegistry.LAVENDER_ITEM.get());

            output.accept(BlockRegistry.JAZBAY_GRAPE_BUSH.get());
            output.accept(BlockRegistry.JUNIPER_BERRY_BUSH.get());
            output.accept(BlockRegistry.SNOWBERRY_BUSH.get());

            output.accept(BlockRegistry.BLEEDING_CROWN.get());
            output.accept(BlockRegistry.WHITE_CAP.get());
            output.accept(BlockRegistry.BLISTERWORT.get());
            output.accept(BlockRegistry.FLY_AMANITA.get());
            output.accept(BlockRegistry.CANIS_ROOT.get());
            output.accept(BlockRegistry.CREEP_CLUSTER.get());

            output.accept(BlockRegistry.PEARL_OYSTER_BLOCK_ITEM.get());

            output.accept(BlockRegistry.STEEL_TRAPDOOR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_TRAPDOOR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_TORCH_ITEM.get());
            output.accept(BlockRegistry.DWEMER_REDSTONE_TORCH_ITEM.get());
            output.accept(BlockRegistry.DWEMER_SOUL_TORCH_ITEM.get());

            output.accept(BlockRegistry.DWEMER_METAL_PILLAR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_PILLAR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_TILES_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_TILE_SLAB_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_TILE_STAIRS_ITEM.get());
            output.accept(BlockRegistry.ORNATE_DWEMER_METAL_TILES_ITEM.get());
            output.accept(BlockRegistry.ORNATE_DWEMER_METAL_TILE_SLAB_ITEM.get());
            output.accept(BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_BLOCK_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_BRICKS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_BRICK_WALL_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_BRICK_SLAB_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_BRICK_STAIRS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_COMPARATOR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_DROPPER_ITEM.get());
            output.accept(BlockRegistry.DWEMER_DISPENSER_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_CHAIN_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_LEVER_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_BUTTON_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_BUTTON_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_BLOCK_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_BRICKS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_BRICK_WALL_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_BRICK_SLAB_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_BRICK_STAIRS_ITEM.get());

            output.accept(BlockRegistry.STEEL_CELL_DOOR_ITEM.get());
            output.accept(BlockRegistry.STEEL_GATE_DOOR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_GATE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_DOOR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STONE_PRESSURE_PLATE_ITEM.get());

            output.accept(BlockRegistry.DWEMER_GLASS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WINDOWED_GLASS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_FRAMED_GLASS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_GLASS_PANE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WINDOWED_GLASS_PANE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_FRAMED_GLASS_PANE_ITEM.get());

            output.accept(BlockRegistry.DWEMER_OBSERVER_ITEM.get());
            output.accept(BlockRegistry.DWEMER_REPEATER_ITEM.get());

            output.accept(BlockRegistry.DWEMER_REDSTONE_LAMP_ITEM.get());
            output.accept(BlockRegistry.DWEMER_REDSTONE_SOUL_LAMP_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_LANTERN_ITEM.get());
            output.accept(BlockRegistry.DWEMER_SOUL_LANTERN_ITEM.get());

            output.accept(BlockRegistry.DWEMER_DAYLIGHT_DETECTOR_ITEM.get());
            output.accept(BlockRegistry.DWEMER_PISTON_ITEM.get());
            output.accept(BlockRegistry.DWEMER_STICKY_PISTON_ITEM.get());

            output.accept(BlockRegistry.DWEMER_CHAIR_ITEM.get());

            output.accept(BlockRegistry.DWEMER_BED_BROWN_ITEM.get());
            output.accept(BlockRegistry.DWEMER_BED_ORANGE_ITEM.get());

            output.accept(BlockRegistry.STEEL_TALL_GATE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_TALL_GATE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_TALL_DOOR_ITEM.get());

            output.accept(BlockRegistry.STEEL_BARS_ITEM.get());
            output.accept(BlockRegistry.DWEMER_METAL_BARS_ITEM.get());

            output.accept(BlockRegistry.DWEMER_WOOL_BROWN_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_ORANGE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_RED_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_MAGENTA_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_PINK_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_PURPLE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_BLUE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_LIGHT_BLUE_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_CYAN_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_GREEN_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_LIME_GREEN_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_YELLOW_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_BLACK_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_GREY_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_LIGHT_GREY_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_WHITE_ITEM.get());

            output.accept(BlockRegistry.DWEMER_WOOL_BROWN_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_ORANGE_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_RED_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_MAGENTA_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_PINK_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_PURPLE_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_BLUE_ITEM_CARPET.get());
            output.accept(BlockRegistry.DWEMER_WOOL_LIGHT_BLUE_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_CYAN_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_GREEN_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_LIME_GREEN_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_YELLOW_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_BLACK_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_GREY_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_LIGHT_GREY_CARPET_ITEM.get());
            output.accept(BlockRegistry.DWEMER_WOOL_WHITE_CARPET_ITEM.get());
        });

        builder.icon(() -> new ItemStack(BlockRegistry.SHOUT_BLOCK.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_BLOCK_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab> SKYRIMCRAFT_COMBAT_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_combat_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .filter(item -> item.get() instanceof SwordItem || item.get() instanceof ShieldItem || item.get() instanceof BowItem || item.get() instanceof ArmorItem)
                    .map(RegistryObject::get)
                    .sorted(Comparator.comparing(Item::getDescriptionId))
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.DAEDRIC_SWORD.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_COMBAT_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab> SKYRIMCRAFT_ALL_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_all_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .map(RegistryObject::get)
                    .sorted(Comparator.comparing(Item::getDescriptionId))
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.DAEDRA_HEART.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_ALL_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab> SKYRIMCRAFT_MAGIC_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_magic_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .filter(item -> item.get() instanceof SpellBook)
                    .map(RegistryObject::get)
                    .sorted(Comparator.comparing(Item::getDescriptionId))
                    .forEach(output::accept);
            output.accept(ItemRegistry.STAFF.get());
        });

        builder.icon(() -> new ItemStack(ItemRegistry.TURN_UNDEAD_SPELLBOOK.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_MAGIC_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab> SKYRIMCRAFT_INGREDIENTS_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_ingredients_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            ItemRegistry.ITEMS.getEntries()
                    .stream()
                    .filter(item -> item.get() instanceof SkyrimIngredient || item.get() instanceof SkyrimBlockItemIngredient)
                    .map(RegistryObject::get)
                    .sorted(Comparator.comparing(Item::getDescriptionId))
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.ORANGE_DARTWING.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_INGREDIENT_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab> SKYRIMCRAFT_JEWELLERY_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_jewellery_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {ItemRegistry.ITEMS.getEntries()
                .stream()
                .filter(item -> item.get() instanceof SkyrimRing || item.get() instanceof SkyrimNecklace)
                .map(RegistryObject::get)
                .sorted(Comparator.comparing(Item::getDescriptionId))
                .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.RING_OF_NAMIRA.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_JEWELLERY_TAB_TITLE));

        return builder.build();
    });

    public static final RegistryObject<CreativeModeTab> SKYRIMCRAFT_FOOD_TAB = CREATIVE_MODE_TABS.register("skyrimcraft_food_tab", () -> {
        CreativeModeTab.Builder builder = CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0);

        builder.displayItems((itemDisplayParameters, output) -> {
            output.accept(ItemRegistry.BUTTER.get());
            output.accept(ItemRegistry.FLOUR.get());
            ItemRegistry.ITEMS.getEntries()
                    .stream()
//                    .filter(item -> item.get().getFoodProperties(new ItemStack(item), null) != null || item.get() instanceof PotionItem) //TODO: item -> item.get().isEdible() ||
                    .map(RegistryObject::get)
                    .sorted(Comparator.comparing(Item::getDescriptionId))
                    .map(Item::getDefaultInstance)
                    .filter(stack -> stack.getItem().getFoodProperties() != null || stack.getItem() instanceof PotionItem)
                    .forEach(output::accept);
        });

        builder.icon(() -> new ItemStack(ItemRegistry.SWEET_ROLL.get()));
        builder.title(Component.translatable(SKYRIMCRAFT_FOOD_TAB_TITLE));

        return builder.build();
    });
}