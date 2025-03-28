package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class TagsRegistry
{
    public static void init() {}

    public static class BlockTagsInit {
        // Ores
        public static TagKey<Block> CORUNDUM_ORE_TAG = create(createResourceLocation("ores/corundum_ore"));
        public static TagKey<Block> EBONY_ORE_TAG = create(createResourceLocation("ores/ebony_ore"));
        public static TagKey<Block> MALACHITE_ORE_TAG = create(createResourceLocation("ores/malachite_ore"));
        public static TagKey<Block> MOONSTONE_ORE_TAG = create(createResourceLocation("ores/moonstone_ore"));
        public static TagKey<Block> ORICHALCUM_ORE_TAG = create(createResourceLocation("ores/orichalcum_ore"));
        public static TagKey<Block> QUICKSILVER_ORE_TAG = create(createResourceLocation("ores/quicksilver_ore"));
        public static TagKey<Block> SILVER_ORE_TAG = create(createResourceLocation("ores/silver_ore"));
        //Deepslate Ores
        public static TagKey<Block> DEEPSLATE_CORUNDUM_ORE_TAG = create(createResourceLocation("ores/deepslate_corundum_ore"));
        public static TagKey<Block> DEEPSLATE_EBONY_ORE_TAG = create(createResourceLocation("ores/deepslate_ebony_ore"));
        public static TagKey<Block> DEEPSLATE_MALACHITE_ORE_TAG = create(createResourceLocation("ores/deepslate_malachite_ore"));
        public static TagKey<Block> DEEPSLATE_MOONSTONE_ORE_TAG = create(createResourceLocation("ores/deepslate_moonstone_ore"));
        public static TagKey<Block> DEEPSLATE_ORICHALCUM_ORE_TAG = create(createResourceLocation("ores/deepslate_orichalcum_ore"));
        public static TagKey<Block> DEEPSLATE_QUICKSILVER_ORE_TAG = create(createResourceLocation("ores/deepslate_quicksilver_ore"));
        public static TagKey<Block> DEEPSLATE_SILVER_ORE_TAG = create(createResourceLocation("ores/deepslate_silver_ore"));


        public static TagKey<Block> NEEDS_ANCIENT_NORD_TOOL = create(createResourceLocation("needs_ancient_nord_tool"));
        public static TagKey<Block> NEEDS_DRAGONBONE_TOOL = create(createResourceLocation("needs_dragonbone_tool"));
        public static TagKey<Block> NEEDS_STEEL_TOOL = create(createResourceLocation("needs_steel_tool"));
        public static TagKey<Block> NEEDS_FALMER_TOOL = create(createResourceLocation("needs_falmer_tool"));
        public static TagKey<Block> NEEDS_GLASS_TOOL = create(createResourceLocation("needs_glass_tool"));
        public static TagKey<Block> NEEDS_ELVEN_TOOL = create(createResourceLocation("needs_elven_tool"));
        public static TagKey<Block> NEEDS_ORCISH_TOOL = create(createResourceLocation("needs_orcish_tool"));
        public static TagKey<Block> NEEDS_DWARVEN_TOOL = create(createResourceLocation("needs_dwarven_tool"));
        public static TagKey<Block> NEEDS_DAEDRIC_TOOL = create(createResourceLocation("needs_daedric_tool"));
        public static TagKey<Block> NEEDS_EBONY_TOOL = create(createResourceLocation("needs_ebony_tool"));

        public static final TagKey<Block> SHORT_DOORS = create(createResourceLocation("short_doors"));
        public static final TagKey<Block> SHORT_WOODEN_DOORS = create(createResourceLocation("short_wooden_doors"));
        public static final TagKey<Block> MOB_INTERACTABLE_SHORT_DOORS = create(createResourceLocation("mob_interactable_short_doors"));

        public static final TagKey<Block> TALL_DOORS = create(createResourceLocation("tall_doors"));
        public static final TagKey<Block> TALL_WOODEN_DOORS = create(createResourceLocation("tall_wooden_doors"));
        public static final TagKey<Block> MOB_INTERACTABLE_TALL_DOORS = create(createResourceLocation("mob_interactable_tall_doors"));

        public static TagKey<Block> create(ResourceLocation location) {
            return TagKey.create(Registries.BLOCK, location);
        }

//        // For normal blocks - storage
//        public static TagKey<Block> STORAGE_BLOCKS_EXAMPLE = create(createResourceLocation("storage_blocks/example_block"));

        private static ResourceLocation createResourceLocation(String name) {
            return new ResourceLocation(Constants.MODID,  name);
        }
    }

    public static class ItemTagsInit {

        public static TagKey<Item> bind(ResourceLocation location) {
            return TagKey.create(Registries.ITEM, location);
        }
        // Ores
        public static TagKey<Item> CORUNDUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/corundum_ore"));
        public static TagKey<Item> EBONY_ORE_ITEM_TAG = bind(createResourceLocation("ores/ebony_ore"));
        public static TagKey<Item> MALACHITE_ORE_ITEM_TAG = bind(createResourceLocation("ores/malachite_ore"));
        public static TagKey<Item> MOONSTONE_ORE_ITEM_TAG = bind(createResourceLocation("ores/moonstone_ore"));
        public static TagKey<Item> ORICHALCUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/orichalcum_ore"));
        public static TagKey<Item> QUICKSILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/quicksilver_ore"));
        public static TagKey<Item> SILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/silver_ore"));
        //Deepslate Ores
        public static TagKey<Item> DEEPSLATE_CORUNDUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_corundum_ore"));
        public static TagKey<Item> DEEPSLATE_EBONY_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_ebony_ore"));
        public static TagKey<Item> DEEPSLATE_MALACHITE_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_malachite_ore"));
        public static TagKey<Item> DEEPSLATE_MOONSTONE_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_moonstone_ore"));
        public static TagKey<Item> DEEPSLATE_ORICHALCUM_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_orichalcum_ore"));
        public static TagKey<Item> DEEPSLATE_QUICKSILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_quicksilver_ore"));
        public static TagKey<Item> DEEPSLATE_SILVER_ORE_ITEM_TAG = bind(createResourceLocation("ores/deepslate_silver_ore"));

        public static final TagKey<Item> SHORT_DOORS = bind(createResourceLocation("short_doors"));
        public static final TagKey<Item> SHORT_WOODEN_DOORS = bind(createResourceLocation("short_wooden_doors"));

        public static final TagKey<Item> TALL_DOORS = bind(createResourceLocation("tall_doors"));
        public static final TagKey<Item> TALL_WOODEN_DOORS = bind(createResourceLocation("tall_wooden_doors"));

        public static final TagKey<Item> KEY = TagKey.create(Registries.ITEM, new ResourceLocation("supplementaries", "key"));

        public static final TagKey<Item> REPAIRS_STEEL = bind(createResourceLocation("repairs_steel"));
        public static final TagKey<Item> REPAIRS_GLASS = bind(createResourceLocation("repairs_glass"));
        public static final TagKey<Item> REPAIRS_ELVEN = bind(createResourceLocation("repairs_elven"));
        public static final TagKey<Item> REPAIRS_DWARVEN = bind(createResourceLocation("repairs_dwarven"));
        public static final TagKey<Item> REPAIRS_EBONY = bind(createResourceLocation("repairs_ebony"));
        public static final TagKey<Item> REPAIRS_ORCISH = bind(createResourceLocation("repairs_orcish"));

        public static final TagKey<Item> EMPTY_INGREDIENT = bind(createResourceLocation("empty_ingredient"));

        // Items

        //        // ore ingot
//        public static TagKey<Item> INGOTS_EXAMPLE = bind(createResourceLocation("ingots/example"));
        public static TagKey<Item> SKYRIM_INGREDIENTS = bind(createResourceLocation("ingredients"));
//
//        // Raw ore scrap
//        public static TagKey<Item> SCRAPS_EXAMPLE = bind(createResourceLocation("scraps/example"));

        public static final TagKey<Item>
                KEYS = bind(createResourceLocation("keys")),
                LOCKS = bind(createResourceLocation("locks"));


        private static ResourceLocation createResourceLocation(String name) {
            return new ResourceLocation(Constants.MODID,  name);
        }
    }

    public static class StructureTagsInit {
        public static TagKey<Structure> NETHER_FORTRESS = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID,  "fortress"));
        public static TagKey<Structure> SHOUT_WALL = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID,  "shout_wall"));
        public static TagKey<Structure> GIANT_CAMP = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID,  "giant_camp"));
//        public static TagKey<Structure> DWARVEN_RUINS = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID,  "dwarven_ruins"));
        public static TagKey<Structure> WATCHTOWER = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID,  "watchtower"));
        public static TagKey<Structure> PYRAMID = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID,  "pyramid"));
        public static TagKey<Structure> RUINED_PORTAL = TagKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID,  "ruined_portal"));
    }

    public static class BiomeTagsInit {
        public static TagKey<Biome> WHITE_SABRE_BIOMES = TagKey.create(Registries.BIOME, new ResourceLocation(Constants.MODID,  "all_snow"));
    }
}