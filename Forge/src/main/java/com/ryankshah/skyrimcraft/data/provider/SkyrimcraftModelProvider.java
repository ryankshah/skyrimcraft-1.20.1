package com.ryankshah.skyrimcraft.data.provider;

import com.example.examplemod.registration.RegistryObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.block.PearlOysterBlock;
import com.ryankshah.skyrimcraft.block.TallDoorBlock;
import com.ryankshah.skyrimcraft.block.util.TripleBlockPart;
import com.ryankshah.skyrimcraft.registry.BlockRegistry;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.util.NameUtils;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;

import java.util.stream.Stream;

public class SkyrimcraftModelProvider extends ModelProvider
{
    public static final ResourceLocation DUMMY_TEXTURE = ResourceLocation.fromNamespaceAndPath("neoforge", "white");

    public SkyrimcraftModelProvider(PackOutput output) {
        super(output, Constants.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        addBlockModels(blockModels);
        addItemModels(itemModels);
    }

    private void addItemModels(ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(BlockRegistry.DWEMER_METAL_TALL_DOOR_ITEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.DWEMER_METAL_TALL_GATE_ITEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.STEEL_TALL_GATE_ITEM.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.SWEET_ROLL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GARLIC_BREAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.POTATO_BREAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.TOMATO.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GARLIC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.APPLE_PIE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MAMMOTH_SNOUT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MAMMOTH_STEAK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.VENISON.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FLOUR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BUTTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CABBAGE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CABBAGE_SEEDS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ASH_YAM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ASH_YAM_SLIPS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.JAZBAY_GRAPES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GREEN_APPLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SNOWBERRIES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.JUNIPER_BERRIES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SNOWBERRY_CROSTATA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.JUNIPER_BERRY_CROSTATA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.JAZBAY_CROSTATA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.LAVENDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.LAVENDER_DUMPLING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CHICKEN_DUMPLING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.POTATO_SOUP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.TOMATO_SOUP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.APPLE_CABBAGE_STEW.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.APPLE_DUMPLING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BEEF_STEW.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CABBAGE_SOUP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CABBAGE_POTATO_SOUP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CLAM_MEAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SLICED_GOAT_CHEESE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SLICED_EIDAR_CHEESE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GOURD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.LEEK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.LEG_OF_GOAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.LEG_OF_GOAT_ROAST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HORSE_MEAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HORSE_HAUNCH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.VEGETABLE_SOUP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MAMMOTH_CHEESE_BOWL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HONEY_NUT_TREAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GRILLED_LEEKS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELSWEYR_FONDUE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CLAM_CHOWDER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BRAIDED_BREAD.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.TOMATO_SEEDS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.ALE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ALTO_WINE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ARGONIAN_ALE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.NORD_MEAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BLACK_BRIAR_MEAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BLACK_BRIAR_RESERVE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DRAGONS_BREATH_MEAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FIREBRAND_WINE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HONNINGBREW_MEAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MEAD_WITH_JUNIPER_BERRY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SKOOMA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SPICED_WINE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.WINE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BOILED_CREME_TREAT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.CORUNDUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DWARVEN_METAL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.EBONY_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MALACHITE_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MOONSTONE_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ORICHALCUM_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.QUICKSILVER_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.STEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.LEATHER_STRIPS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DAEDRA_HEART.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.FLAWED_AMETHYST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FLAWED_DIAMOND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FLAWED_EMERALD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FLAWED_RUBY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FLAWLESS_RUBY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FLAWED_GARNET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FLAWLESS_GARNET.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.SALT_PILE.get(), ModelTemplates.FLAT_ITEM);
//        itemModels.generateFlatItem(ItemRegistry.CREEP_CLUSTER.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GRASS_POD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.VAMPIRE_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MORA_TAPINELLA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BRIAR_HEART.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GIANTS_TOE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SALMON_ROE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DWARVEN_OIL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FIRE_SALTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ABECEAN_LONGFIN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CYRODILIC_SPADETAIL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SABRE_CAT_TOOTH.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BLUE_DARTWING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ORANGE_DARTWING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PEARL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SMALL_PEARL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PINE_THRUSH_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ROCK_WARBLER_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SLAUGHTERFISH_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SLAUGHTERFISH_SCALES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SPIDER_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HAWK_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.TROLL_FAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CHAURUS_EGGS.get(), ModelTemplates.FLAT_ITEM);
//        itemModels.generateFlatItem(ItemRegistry.FLY_AMANITA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELVES_EAR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.TAPROOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BEE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.EYE_OF_SABRE_CAT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BEAR_CLAWS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BEEHIVE_HUSK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BERITS_ASHES.get(), ModelTemplates.FLAT_ITEM);
//        itemModels.generateFlatItem(ItemRegistry.BLISTERWORT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BLUE_BUTTERFLY_WING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BUTTERFLY_WING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CHARRED_SKEEVER_HIDE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CRIMSON_NIRNROOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DEATHBELL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DRAGONS_TONGUE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ECTOPLASM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FALMER_EAR.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateSpawnEgg(ItemRegistry.BLUE_BUTTERFLY_SPAWN_EGG.get(), 0x5077FF, 0x3366CC); // Light blue and darker blue
        itemModels.generateSpawnEgg(ItemRegistry.MONARCH_BUTTERFLY_SPAWN_EGG.get(), 0xF4A341, 0x5E330C); // Orange and dark brown
        itemModels.generateSpawnEgg(ItemRegistry.LUNAR_MOTH_SPAWN_EGG.get(), 0x93E6C3, 0x447D61); // Pale green and forest green
        itemModels.generateSpawnEgg(ItemRegistry.ORANGE_DARTWING_SPAWN_EGG.get(), 0xE97E33, 0x5E330C); // Bright orange and dark brown
        itemModels.generateSpawnEgg(ItemRegistry.BLUE_DARTWING_SPAWN_EGG.get(), 0x4475F4, 0x243B9B); // Bright blue and navy
        itemModels.generateSpawnEgg(ItemRegistry.TORCHBUG_SPAWN_EGG.get(), 0xFFD700, 0x4A4035); // Yellow-gold and dark brown
        itemModels.generateSpawnEgg(ItemRegistry.DRAGON_SPAWN_EGG.get(), 0x4C4C4C, 0x1B1B1B); // Dark gray and black
        itemModels.generateSpawnEgg(ItemRegistry.GIANT_SPAWN_EGG.get(), 0xA18451, 0x6B4513); // Light tan and saddle brown
        itemModels.generateSpawnEgg(ItemRegistry.SABRE_CAT_SPAWN_EGG.get(), 0xB2734C, 0x5C4033); // Tan and brown
        itemModels.generateSpawnEgg(ItemRegistry.DRAUGR_SPAWN_EGG.get(), 0x708090, 0x2F4F4F); // Slate gray and dark slate gray
        itemModels.generateSpawnEgg(ItemRegistry.KHAJIIT_SPAWN_EGG.get(), 0xA8763E, 0x38210F); // Tawny and dark brown
        itemModels.generateSpawnEgg(ItemRegistry.FALMER_SPAWN_EGG.get(), 0xC1C6C8, 0x53575A); // Pale gray and dark gray

        itemModels.generateFlatItem(ItemRegistry.ANCIENT_NORD_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ANCIENT_NORD_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ANCIENT_NORD_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ANCIENT_NORD_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ANCIENT_NORD_SWORD.get(), ModelTemplates.FLAT_HANDHELD_ITEM);
        greatsword(itemModels, ItemRegistry.ANCIENT_NORD_GREATSWORD.get());
        sword(itemModels, ItemRegistry.ANCIENT_NORD_WAR_AXE.get());
        sword(itemModels, ItemRegistry.ANCIENT_NORD_BATTLEAXE.get());
        itemModels.generateBow(ItemRegistry.ANCIENT_NORD_BOW.get());

        itemModels.generateFlatItem(ItemRegistry.DAEDRIC_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DAEDRIC_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DAEDRIC_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DAEDRIC_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.DAEDRIC_DAGGER.get());
        sword(itemModels, ItemRegistry.DAEDRIC_SWORD.get());
        sword(itemModels, ItemRegistry.DAEDRIC_BATTLEAXE.get());
        itemModels.generateBow(ItemRegistry.DAEDRIC_BOW.get());
        greatsword(itemModels, ItemRegistry.DAEDRIC_GREATSWORD.get());
        sword(itemModels, ItemRegistry.DAEDRIC_MACE.get());
        sword(itemModels, ItemRegistry.DAEDRIC_WAR_AXE.get());
        sword(itemModels, ItemRegistry.DAEDRIC_WARHAMMER.get());

        sword(itemModels, ItemRegistry.DRAGONBONE_DAGGER.get());
        sword(itemModels, ItemRegistry.DRAGONBONE_SWORD.get());
        sword(itemModels, ItemRegistry.DRAGONBONE_BATTLEAXE.get());
        itemModels.generateBow(ItemRegistry.DRAGONBONE_BOW.get());
        greatsword(itemModels, ItemRegistry.DRAGONBONE_GREATSWORD.get());
        sword(itemModels, ItemRegistry.DRAGONBONE_MACE.get());
        sword(itemModels, ItemRegistry.DRAGONBONE_WAR_AXE.get());
        sword(itemModels, ItemRegistry.DRAGONBONE_WARHAMMER.get());

        itemModels.generateFlatItem(ItemRegistry.DWARVEN_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DWARVEN_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DWARVEN_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DWARVEN_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.DWARVEN_DAGGER.get());
        sword(itemModels, ItemRegistry.DWARVEN_SWORD.get());
        sword(itemModels, ItemRegistry.DWARVEN_BATTLEAXE.get());
        itemModels.generateBow(ItemRegistry.DWARVEN_BOW.get());
        greatsword(itemModels, ItemRegistry.DWARVEN_GREATSWORD.get());
        sword(itemModels, ItemRegistry.DWARVEN_MACE.get());
        sword(itemModels, ItemRegistry.DWARVEN_WAR_AXE.get());
        sword(itemModels, ItemRegistry.DWARVEN_WARHAMMER.get());

        itemModels.generateFlatItem(ItemRegistry.EBONY_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.EBONY_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.EBONY_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.EBONY_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.EBONY_DAGGER.get());
        sword(itemModels, ItemRegistry.EBONY_SWORD.get());
        sword(itemModels, ItemRegistry.EBONY_BATTLEAXE.get());
        itemModels.generateBow(ItemRegistry.EBONY_BOW.get());
        greatsword(itemModels, ItemRegistry.EBONY_GREATSWORD.get());
        sword(itemModels, ItemRegistry.EBONY_MACE.get());
        sword(itemModels, ItemRegistry.EBONY_WAR_AXE.get());
        sword(itemModels, ItemRegistry.EBONY_WARHAMMER.get());

        itemModels.generateFlatItem(ItemRegistry.ELVEN_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELVEN_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELVEN_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELVEN_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.ELVEN_DAGGER.get());
        sword(itemModels, ItemRegistry.ELVEN_SWORD.get());
        sword(itemModels, ItemRegistry.ELVEN_BATTLEAXE.get());;
        itemModels.generateBow(ItemRegistry.ELVEN_BOW.get());
        greatsword(itemModels, ItemRegistry.ELVEN_GREATSWORD.get());
        sword(itemModels, ItemRegistry.ELVEN_MACE.get());
        sword(itemModels, ItemRegistry.ELVEN_WAR_AXE.get());
        sword(itemModels, ItemRegistry.ELVEN_WARHAMMER.get());

        itemModels.generateFlatItem(ItemRegistry.FALMER_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FALMER_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FALMER_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FALMER_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.FALMER_SWORD.get());
        itemModels.generateBow(ItemRegistry.FALMER_BOW.get());
        sword(itemModels, ItemRegistry.FALMER_WAR_AXE.get());

        itemModels.generateFlatItem(ItemRegistry.GLASS_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GLASS_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GLASS_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GLASS_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.GLASS_DAGGER.get());
        sword(itemModels, ItemRegistry.GLASS_SWORD.get());
        sword(itemModels, ItemRegistry.GLASS_BATTLEAXE.get());
        itemModels.generateBow(ItemRegistry.GLASS_BOW.get());
        greatsword(itemModels, ItemRegistry.GLASS_GREATSWORD.get());
        sword(itemModels, ItemRegistry.GLASS_MACE.get());
        sword(itemModels, ItemRegistry.GLASS_WAR_AXE.get());
        sword(itemModels, ItemRegistry.GLASS_WARHAMMER.get());

        itemModels.generateFlatItem(ItemRegistry.IMPERIAL_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.IMPERIAL_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.IMPERIAL_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.IMPERIAL_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.IMPERIAL_SWORD.get());

        itemModels.generateFlatItem(ItemRegistry.IRON_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.IRON_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.IRON_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.IRON_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.IRON_DAGGER.get());
        sword(itemModels, ItemRegistry.IRON_SWORD.get());
        sword(itemModels, ItemRegistry.IRON_BATTLEAXE.get());
        greatsword(itemModels, ItemRegistry.IRON_GREATSWORD.get());
        sword(itemModels, ItemRegistry.IRON_MACE.get());
        sword(itemModels, ItemRegistry.IRON_WAR_AXE.get());
        sword(itemModels, ItemRegistry.IRON_WARHAMMER.get());

        itemModels.generateFlatItem(ItemRegistry.ORCISH_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ORCISH_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ORCISH_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ORCISH_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.ORCISH_DAGGER.get());
        sword(itemModels, ItemRegistry.ORCISH_SWORD.get());
        sword(itemModels, ItemRegistry.ORCISH_BATTLEAXE.get());
        itemModels.generateBow(ItemRegistry.ORCISH_BOW.get());
        greatsword(itemModels, ItemRegistry.ORCISH_GREATSWORD.get());
        sword(itemModels, ItemRegistry.ORCISH_MACE.get());
        sword(itemModels, ItemRegistry.ORCISH_WAR_AXE.get());
        sword(itemModels, ItemRegistry.ORCISH_WARHAMMER.get());

//        itemModels.generateFlatItem(ItemRegistry.STEEL_HELMET.get(), ModelTemplates.FLAT_ITEM);
//        itemModels.generateFlatItem(ItemRegistry.STEEL_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
//        itemModels.generateFlatItem(ItemRegistry.STEEL_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
//        itemModels.generateFlatItem(ItemRegistry.STEEL_BOOTS.get(), ModelTemplates.FLAT_ITEM);
        sword(itemModels, ItemRegistry.STEEL_DAGGER.get());
        sword(itemModels, ItemRegistry.STEEL_SWORD.get());
        sword(itemModels, ItemRegistry.STEEL_BATTLEAXE.get());
        greatsword(itemModels, ItemRegistry.STEEL_GREATSWORD.get());
        sword(itemModels, ItemRegistry.STEEL_MACE.get());
        sword(itemModels, ItemRegistry.STEEL_WAR_AXE.get());
        sword(itemModels, ItemRegistry.STEEL_WARHAMMER.get());

        itemModels.generateFlatItem(ItemRegistry.STORMCLOAK_OFFICER_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.STORMCLOAK_OFFICER_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.STORMCLOAK_OFFICER_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.STORMCLOAK_OFFICER_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateBow(ItemRegistry.HUNTING_BOW.get());
        itemModels.generateBow(ItemRegistry.LONGBOW.get());

        itemModels.generateFlatItem(ItemRegistry.SCALED_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SCALED_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SCALED_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SCALED_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.HIDE_HELMET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HIDE_CHESTPLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HIDE_LEGGINGS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.HIDE_BOOTS.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.CHILLREND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DAWNBREAKER.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.GOLD_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GOLD_SAPPHIRE_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GOLD_EMERALD_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GOLD_DIAMOND_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_AMETHYST_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_GARNET_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_RUBY_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ASGEIRS_WEDDING_BAND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.AHZIDALS_RING_OF_ARCANA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BALWENS_ORNAMENTAL_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.BONE_HAWK_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CALCELMOS_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ENCHANTED_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.FJOLAS_WEDDING_BAND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ILAS_TEIS_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.KATARINAS_ORNAMENTAL_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MADESIS_SILVER_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MUIRIS_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.NIGHTWEAVERS_BAND.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PITHIS_ORNAMENTAL_RING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.RING_OF_NAMIRA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.TREOYS_ORNAMENTAL_RING.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.GOLD_NECKLACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GOLD_DIAMOND_NECKLACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GOLD_JEWELLED_NECKLACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GOLD_RUBY_NECKLACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_NECKLACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_EMERALD_NECKLACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_JEWELLED_NECKLACE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SILVER_SAPPHIRE_NECKLACE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.STAFF.get(), ModelTemplates.FLAT_ITEM);
        spellbook(itemModels, ItemRegistry.CONJURE_FAMILIAR_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.FIREBALL_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.HEALING_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.TURN_UNDEAD_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.LIGHTNING_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.FLAME_CLOAK_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.ICE_SPIKE_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.CONJURE_ZOMBIE_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.DETECT_LIFE_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.CANDLELIGHT_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.OAKFLESH_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.TELEKINESIS_SPELLBOOK.get());
        spellbook(itemModels, ItemRegistry.WATERBREATHING_SPELLBOOK.get());

        skillbook(itemModels, ItemRegistry.ALTERATION_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.CONJURATION_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.DESTRUCTION_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.ILLUSION_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.RESTORATION_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.ENCHANTING_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.ONE_HANDED_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.TWO_HANDED_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.ARCHERY_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.BLOCK_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.SMITHING_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.HEAVY_ARMOR_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.LIGHT_ARMOR_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.PICKPOCKET_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.LOCKPICKING_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.SNEAK_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.ALCHEMY_SKILLBOOK.get());
        skillbook(itemModels, ItemRegistry.SPEECH_SKILLBOOK.get());

        itemModels.generateFlatItem(ItemRegistry.MINOR_MAGICKA_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.MAGICKA_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PLENTIFUL_MAGICKA_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.VIGOROUS_MAGICKA_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.EXTREME_MAGICKA_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ULTIMATE_MAGICKA_POTION.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.PHILTER_OF_THE_PHANTOM_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.POTION_OF_WATERWALKING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.POTION_OF_CURE_DISEASES.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.POTION_OF_CURE_POISON.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.LASTING_POTENCY_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DRAUGHT_LASTING_POTENCY_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.SOLUTION_LASTING_POTENCY_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PHILTER_LASTING_POTENCY_POTION.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELIXIR_LASTING_POTENCY_POTION.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.POTION_OF_HAGGLING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DRAUGHT_OF_HAGGLING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PHILTER_OF_HAGGLING.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELIXIR_OF_HAGGLING.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.POTION_OF_TRUE_SHOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DRAUGHT_OF_TRUE_SHOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PHILTER_OF_TRUE_SHOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.ELIXIR_OF_TRUE_SHOT.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(BlockRegistry.DWEMER_METAL_BRICK_WALL_ITEM.get(), ModelTemplates.WALL_INVENTORY);
        itemModels.generateFlatItem(BlockRegistry.DWEMER_STONE_BRICK_WALL_ITEM.get(), ModelTemplates.WALL_INVENTORY);
        //wallInventory("dwemer_metal_brick_wall", ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_bricks"));
        //wallInventory("dwemer_stone_brick_wall", ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_bricks"));

        itemModels.generateFlatItem(BlockRegistry.CANIS_ROOT.get().asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.BLEEDING_CROWN.get().asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.BLISTERWORT.get().asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.FLY_AMANITA.get().asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.WHITE_CAP.get().asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.CREEP_CLUSTER.get().asItem(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(BlockRegistry.DWEMER_COMPARATOR.get().asItem(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(BlockRegistry.DWEMER_REPEATER.get().asItem(), ModelTemplates.FLAT_ITEM);
    }

    private void addBlockModels(BlockModelGenerators blockModels) {
        blockModels.createGenericCube(BlockRegistry.CORUNDUM_ORE.get());
        blockModels.createGenericCube(BlockRegistry.DEEPSLATE_CORUNDUM_ORE.get());
        blockModels.createGenericCube(BlockRegistry.EBONY_ORE.get());
        blockModels.createGenericCube(BlockRegistry.DEEPSLATE_EBONY_ORE.get());
        blockModels.createGenericCube(BlockRegistry.MALACHITE_ORE.get());
        blockModels.createGenericCube(BlockRegistry.DEEPSLATE_MALACHITE_ORE.get());
        blockModels.createGenericCube(BlockRegistry.MOONSTONE_ORE.get());
        blockModels.createGenericCube(BlockRegistry.DEEPSLATE_MOONSTONE_ORE.get());
        blockModels.createGenericCube(BlockRegistry.ORICHALCUM_ORE.get());
        blockModels.createGenericCube(BlockRegistry.DEEPSLATE_ORICHALCUM_ORE.get());
        blockModels.createGenericCube(BlockRegistry.QUICKSILVER_ORE.get());
        blockModels.createGenericCube(BlockRegistry.DEEPSLATE_QUICKSILVER_ORE.get());
        blockModels.createGenericCube(BlockRegistry.SILVER_ORE.get());
        blockModels.createGenericCube(BlockRegistry.DEEPSLATE_SILVER_ORE.get());

        blockModels.createGenericCube(BlockRegistry.STONE_4.get());
        blockModels.createGenericCube(BlockRegistry.STONE_5.get());

        blockModels.createGenericCube(BlockRegistry.SHOUT_BLOCK.get());

        blockModels.createCrossBlock(BlockRegistry.RED_MOUNTAIN_FLOWER.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(BlockRegistry.BLUE_MOUNTAIN_FLOWER.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(BlockRegistry.YELLOW_MOUNTAIN_FLOWER.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(BlockRegistry.PURPLE_MOUNTAIN_FLOWER.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(BlockRegistry.LAVENDER.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createCrossBlock(BlockRegistry.CANIS_ROOT_BLOCK.get(), BlockModelGenerators.PlantType.NOT_TINTED);

        mushroomBlock(blockModels, BlockRegistry.BLEEDING_CROWN_BLOCK.get());
        mushroomBlock(blockModels, BlockRegistry.FLY_AMANITA_BLOCK.get());
        mushroomBlock(blockModels, BlockRegistry.WHITE_CAP_BLOCK.get());
        mushroomBlock(blockModels, BlockRegistry.BLISTERWORT_BLOCK.get());
        lilyPadBlock(blockModels, BlockRegistry.CREEP_CLUSTER_BLOCK.get());

        oysterBlock(blockModels);

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistry.ALCHEMY_TABLE.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/alchemy_table")).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));
        blockModels.registerSimpleItemModel(BlockRegistry.ALCHEMY_TABLE.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/alchemy_table"));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistry.WEAPON_RACK.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/weapon_rack")).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));
        blockModels.registerSimpleItemModel(BlockRegistry.WEAPON_RACK.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/weapon_rack"));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistry.BIRDS_NEST.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/birds_nest")));
        blockModels.registerSimpleItemModel(BlockRegistry.BIRDS_NEST.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/birds_nest"));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistry.OVEN.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/oven")).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));
        blockModels.registerSimpleItemModel(BlockRegistry.OVEN.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/oven"));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistry.BLACKSMITH_FORGE.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/blacksmith_forge")).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));
        blockModels.registerSimpleItemModel(BlockRegistry.BLACKSMITH_FORGE.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/blacksmith_forge"));

        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(BlockRegistry.ARCANE_ENCHANTER.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/arcane_enchanter")).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));
        blockModels.registerSimpleItemModel(BlockRegistry.ARCANE_ENCHANTER.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/arcane_enchanter"));

        blockModels.createCropBlock(BlockRegistry.ASH_YAM_CROP.get(), BlockStateProperties.AGE_3, 0, 1, 2, 3);

        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BlockRegistry.JAZBAY_GRAPE_BUSH.get()).with(PropertyDispatch.property(BlockStateProperties.AGE_3).generate((p_388136_) -> {
            return Variant.variant().with(VariantProperties.MODEL, blockModels.createSuffixedVariant(BlockRegistry.JAZBAY_GRAPE_BUSH.get(), "_stage" + p_388136_, ModelTemplates.CROSS, TextureMapping::cross));
        })));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BlockRegistry.JUNIPER_BERRY_BUSH.get()).with(PropertyDispatch.property(BlockStateProperties.AGE_3).generate((p_388136_) -> {
            return Variant.variant().with(VariantProperties.MODEL, blockModels.createSuffixedVariant(BlockRegistry.JUNIPER_BERRY_BUSH.get(), "_stage" + p_388136_, ModelTemplates.CROSS, TextureMapping::cross));
        })));
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(BlockRegistry.SNOWBERRY_BUSH.get()).with(PropertyDispatch.property(BlockStateProperties.AGE_3).generate((p_388136_) -> {
            return Variant.variant().with(VariantProperties.MODEL, blockModels.createSuffixedVariant(BlockRegistry.SNOWBERRY_BUSH.get(), "_stage" + p_388136_, ModelTemplates.CROSS, TextureMapping::cross));
        })));

//        blockModels.horizontalBlock(BlockRegistry.TURN_STONE.get(), null);
        turnStoneBlock(blockModels, BlockRegistry.TURN_STONE.get());
        runeStoneBlock(blockModels, BlockRegistry.RUNE_STONE.get());
//        blockModels.simpleBlockItem(BlockRegistry.RUNE_STONE.get(), blockModels.models().getExistingFile(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/rune_stone")));


        blockModels.copyTrapdoorModel(Blocks.IRON_TRAPDOOR, BlockRegistry.STEEL_TRAPDOOR.get());
        blockModels.copyDoorModel(Blocks.IRON_DOOR, BlockRegistry.STEEL_CELL_DOOR.get());
        blockModels.copyDoorModel(Blocks.IRON_DOOR, BlockRegistry.STEEL_GATE_DOOR.get());
        tallDoorBlock(blockModels, BlockRegistry.STEEL_TALL_GATE.get(), "block/steel_tall_gate");


        blockModels.copyTrapdoorModel(Blocks.IRON_TRAPDOOR, BlockRegistry.DWEMER_METAL_TRAPDOOR.get());

        torchBlock(blockModels, BlockRegistry.DWEMER_METAL_TORCH.get());
        wallTorchBlock(blockModels, BlockRegistry.DWEMER_METAL_TORCH.get(), BlockRegistry.DWEMER_METAL_WALL_TORCH.get());
        torchBlock(blockModels, BlockRegistry.DWEMER_SOUL_TORCH.get());
        wallTorchBlock(blockModels, BlockRegistry.DWEMER_SOUL_TORCH.get(), BlockRegistry.DWEMER_SOUL_WALL_TORCH.get());
//        poweredTorchBlock(blockModels, BlockRegistry.DWEMER_REDSTONE_TORCH.get());
//        poweredWallTorchBlock(blockModels, BlockRegistry.DWEMER_REDSTONE_TORCH.get(), BlockRegistry.DWEMER_REDSTONE_WALL_TORCH.get());

        blockModels.copyDoorModel(Blocks.IRON_DOOR, BlockRegistry.DWEMER_METAL_DOOR.get());
        tallDoorBlock(blockModels, BlockRegistry.DWEMER_METAL_TALL_DOOR.get(), "block/dwemer_metal_tall_door");
        blockModels.copyDoorModel(Blocks.IRON_DOOR, BlockRegistry.DWEMER_METAL_GATE.get());
        tallDoorBlock(blockModels, BlockRegistry.DWEMER_METAL_TALL_GATE.get(), "block/dwemer_metal_tall_gate");
        blockModels.createAxisAlignedPillarBlock(BlockRegistry.DWEMER_METAL_PILLAR.get(), TexturedModel.COLUMN);
        blockModels.registerSimpleItemModel(BlockRegistry.DWEMER_METAL_PILLAR.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_metal_pillar"));
        blockModels.createAxisAlignedPillarBlock(BlockRegistry.DWEMER_STONE_PILLAR.get(), TexturedModel.COLUMN);
        blockModels.registerSimpleItemModel(BlockRegistry.DWEMER_STONE_PILLAR.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_pillar"));

        pressurePlateBlock(blockModels, BlockRegistry.DWEMER_STONE_PRESSURE_PLATE.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_pressure_plate"));
        blockModels.registerSimpleItemModel(BlockRegistry.DWEMER_STONE_PILLAR.get(), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/dwemer_stone_pressure_plate"));

        blockModels.createGenericCube(BlockRegistry.DWEMER_METAL_TILES.get());
        blockModels.createGenericCube(BlockRegistry.ORNATE_DWEMER_METAL_TILES.get());
        blockModels.createGenericCube(BlockRegistry.DWEMER_METAL_BLOCK.get());
        blockModels.createGenericCube(BlockRegistry.DWEMER_METAL_BRICKS.get());
        blockModels.createGenericCube(BlockRegistry.DWEMER_STONE_BLOCK.get());
        blockModels.createGenericCube(BlockRegistry.DWEMER_STONE_BRICKS.get());

        glassBlock(blockModels, BlockRegistry.DWEMER_GLASS.get());
        glassBlock(blockModels, BlockRegistry.DWEMER_FRAMED_GLASS.get());
        glassBlock(blockModels, BlockRegistry.DWEMER_WINDOWED_GLASS.get());

        blockModels.createHorizontallyRotatedBlock(BlockRegistry.DWEMER_CHAIR.get(), TexturedModel.ORIENTABLE);
        blockModels.createHorizontallyRotatedBlock(BlockRegistry.DWEMER_BED_BROWN.get(), TexturedModel.ORIENTABLE);
        blockModels.createHorizontallyRotatedBlock(BlockRegistry.DWEMER_BED_ORANGE.get(), TexturedModel.ORIENTABLE); // TODO: VERIFY THESE THREE...

        blockModels.createFlatItemModelWithBlockTexture(BlockRegistry.DWEMER_OBSERVER.get().asItem(), BlockRegistry.DWEMER_OBSERVER.get());
//        blockModels.simpleBlockItem(BlockRegistry.DWEMER_COMPARATOR.get(), blockModels.models().getExistingFile(blockModels.modLoc("dwemer_comparator")));
//        blockModels.itemModels().basicItem(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "dwemer_comparator")); // added to item provider above
//        blockModels.itemModels().basicItem(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "dwemer_repeater")); // added to item provider above
        blockModels.createFlatItemModelWithBlockTexture(BlockRegistry.DWEMER_DROPPER.get().asItem(), BlockRegistry.DWEMER_DROPPER.get());
        blockModels.createFlatItemModelWithBlockTexture(BlockRegistry.DWEMER_DISPENSER.get().asItem(), BlockRegistry.DWEMER_DISPENSER.get());
        blockModels.createFlatItemModelWithBlockTexture(BlockRegistry.DWEMER_PISTON.get().asItem(), BlockRegistry.DWEMER_PISTON.get());
        blockModels.createFlatItemModelWithBlockTexture(BlockRegistry.DWEMER_STICKY_PISTON.get().asItem(), BlockRegistry.DWEMER_STICKY_PISTON.get());
        daylightDetector(blockModels, BlockRegistry.DWEMER_DAYLIGHT_DETECTOR.get());
        blockModels.createDaylightDetector();

        lampBlock(blockModels, BlockRegistry.DWEMER_REDSTONE_LAMP.get());
        lampBlock(blockModels, BlockRegistry.DWEMER_REDSTONE_SOUL_LAMP.get());
        lantern(blockModels, BlockRegistry.DWEMER_METAL_LANTERN.get());
        lantern(blockModels, BlockRegistry.DWEMER_SOUL_LANTERN.get());

        blockModels.createFlatItemModelWithBlockTexture(BlockRegistry.DWEMER_METAL_CHAIN.get().asItem(), BlockRegistry.DWEMER_METAL_CHAIN.get());
        blockModels.createFlatItemModelWithBlockTexture(BlockRegistry.DWEMER_METAL_LEVER.get().asItem(), BlockRegistry.DWEMER_METAL_LEVER.get());

        BlockFamily dwemer_metal_fam = new BlockFamily.Builder(BlockRegistry.DWEMER_METAL_BRICKS.get())
                .button(BlockRegistry.DWEMER_METAL_BUTTON.get())
                .slab(BlockRegistry.DWEMER_METAL_BRICK_SLAB.get())
                .wall(BlockRegistry.DWEMER_METAL_BRICK_WALL.get())
                .stairs(BlockRegistry.DWEMER_METAL_BRICK_STAIRS.get())
                .getFamily();
        BlockFamily dwemer_stone_fam = new BlockFamily.Builder(BlockRegistry.DWEMER_STONE_BRICKS.get())
                .button(BlockRegistry.DWEMER_STONE_BUTTON.get())
                .slab(BlockRegistry.DWEMER_STONE_BRICK_SLAB.get())
                .wall(BlockRegistry.DWEMER_STONE_BRICK_WALL.get())
                .stairs(BlockRegistry.DWEMER_STONE_BRICK_STAIRS.get())
                .getFamily();
        BlockFamily dwemer_tile_fam = new BlockFamily.Builder(BlockRegistry.DWEMER_METAL_TILES.get())
                .button(BlockRegistry.DWEMER_METAL_TILE_SLAB.get())
                .slab(BlockRegistry.DWEMER_METAL_TILE_STAIRS.get())
                .getFamily();
        BlockFamily ornate_dwemer_tile_fam = new BlockFamily.Builder(BlockRegistry.ORNATE_DWEMER_METAL_TILES.get())
                .button(BlockRegistry.ORNATE_DWEMER_METAL_TILE_SLAB.get())
                .slab(BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS.get())
                .getFamily();

        blockModels.family(BlockRegistry.DWEMER_METAL_BUTTON.get()).generateFor(dwemer_metal_fam);
        blockModels.family(BlockRegistry.DWEMER_METAL_BRICK_SLAB.get()).generateFor(dwemer_metal_fam);
        blockModels.family(BlockRegistry.DWEMER_METAL_BRICK_WALL.get()).generateFor(dwemer_metal_fam);
        blockModels.family(BlockRegistry.DWEMER_METAL_BRICK_STAIRS.get()).generateFor(dwemer_metal_fam);

        blockModels.family(BlockRegistry.DWEMER_STONE_BUTTON.get()).generateFor(dwemer_stone_fam);
        blockModels.family(BlockRegistry.DWEMER_STONE_BRICK_SLAB.get()).generateFor(dwemer_stone_fam);
        blockModels.family(BlockRegistry.DWEMER_STONE_BRICK_WALL.get()).generateFor(dwemer_stone_fam);
        blockModels.family(BlockRegistry.DWEMER_STONE_BRICK_STAIRS.get()).generateFor(dwemer_stone_fam);

        blockModels.family(BlockRegistry.DWEMER_METAL_TILE_SLAB.get()).generateFor(dwemer_tile_fam);
        blockModels.family(BlockRegistry.DWEMER_METAL_TILE_STAIRS.get()).generateFor(dwemer_tile_fam);

        blockModels.family(BlockRegistry.ORNATE_DWEMER_METAL_TILE_SLAB.get()).generateFor(ornate_dwemer_tile_fam);
        blockModels.family(BlockRegistry.ORNATE_DWEMER_METAL_TILE_STAIRS.get()).generateFor(ornate_dwemer_tile_fam);

        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_BROWN.get(), BlockRegistry.DWEMER_WOOL_BROWN_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_ORANGE.get(), BlockRegistry.DWEMER_WOOL_ORANGE_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_RED.get(), BlockRegistry.DWEMER_WOOL_RED_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_MAGENTA.get(), BlockRegistry.DWEMER_WOOL_MAGENTA_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_PINK.get(), BlockRegistry.DWEMER_WOOL_PINK_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_PURPLE.get(), BlockRegistry.DWEMER_WOOL_PURPLE_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_BLUE.get(), BlockRegistry.DWEMER_WOOL_BLUE_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_LIGHT_BLUE.get(), BlockRegistry.DWEMER_WOOL_LIGHT_BLUE_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_CYAN.get(), BlockRegistry.DWEMER_WOOL_CYAN_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_GREEN.get(), BlockRegistry.DWEMER_WOOL_GREEN_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_LIME_GREEN.get(), BlockRegistry.DWEMER_WOOL_LIME_GREEN_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_YELLOW.get(), BlockRegistry.DWEMER_WOOL_YELLOW_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_BLACK.get(), BlockRegistry.DWEMER_WOOL_BLACK_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_GREY.get(), BlockRegistry.DWEMER_WOOL_GREY_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_LIGHT_GREY.get(), BlockRegistry.DWEMER_WOOL_LIGHT_GREY_CARPET.get());
        woolAndCarpet(blockModels, BlockRegistry.DWEMER_WOOL_WHITE.get(), BlockRegistry.DWEMER_WOOL_WHITE_CARPET.get());
    }

    private void sword(ItemModelGenerators generator, Item item) {
        generator.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    private void greatsword(ItemModelGenerators generator, Item item) {
        ItemModel.Unbaked itemmodel$unbaked = ItemModelUtils.plainModel(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "item/greatsword"));
        ItemModel.Unbaked itemmodel$unbaked1 = ItemModelUtils.plainModel(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "item/greatsword_blocking"));
        generator.generateBooleanDispatch(item, ItemModelUtils.isUsingItem(), itemmodel$unbaked1, itemmodel$unbaked);
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return BlockRegistry.BLOCKS.getEntries().stream().map(RegistryObject::asHolder);
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return ItemRegistry.ITEMS.getEntries().stream().map(RegistryObject::asHolder);
    }

    private static String name(Block block) {
        return key(block).getPath();
    }

    private static ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    protected static MultiVariantGenerator variant(BlockModelGenerators blockModels, Holder<Block> block) {
        return variant(blockModels, block, Variant.variant());
    }

    protected static MultiVariantGenerator variant(BlockModelGenerators blockModels, Holder<Block> block, Variant... baseVariants) {
        MultiVariantGenerator generator = MultiVariantGenerator.multiVariant(block.value(), baseVariants);
        blockModels.blockStateOutput.accept(generator);
        return generator;
    }

    protected static VariantProperties.Rotation rotByIdx(int idx) {
        return VariantProperties.Rotation.values()[idx];
    }

    protected static Variant rotationToVariant(int rotation) {
        return horDirToVariant(Direction.from2DDataValue(rotation / 4));
    }

    protected static Variant horDirToVariant(Direction dir) {
        VariantProperties.Rotation rotValue = switch (dir)
        {
            case NORTH -> VariantProperties.Rotation.R180;
            case SOUTH -> VariantProperties.Rotation.R0;
            case WEST -> VariantProperties.Rotation.R90;
            case EAST -> VariantProperties.Rotation.R270;
            default -> throw new IllegalArgumentException("Invalid direction for Y rotation: " + dir);
        };
        Variant variant = Variant.variant();
        if (rotValue != VariantProperties.Rotation.R0)
        {
            variant.with(VariantProperties.Y_ROT, rotValue);
        }
        return variant;
    }

    protected static MultiPartGenerator multiPart(BlockModelGenerators blockModels, Holder<Block> block) {
        MultiPartGenerator generator = MultiPartGenerator.multiPart(block.value());
        blockModels.blockStateOutput.accept(generator);
        return generator;
    }

    protected static void simpleBlock(BlockModelGenerators blockModels, Holder<Block> block) {
        simpleBlock(blockModels, block, ModelLocationUtils.getModelLocation(block.value()));
    }

    protected static void simpleBlock(BlockModelGenerators blockModels, Holder<Block> block, ResourceLocation model) {
        blockModels.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block.value(), model));
    }

    protected static void blockFromTemplate(BlockModelGenerators blockModels, Holder<Block> block, ModelTemplate template, TextureMapping textures) {
        simpleBlock(blockModels, block, blockModelFromTemplate(blockModels, block, template, textures));
    }

    protected static ResourceLocation blockModelFromTemplate(BlockModelGenerators blockModels, Holder<Block> block, ModelTemplate template, TextureMapping textures) {
        ResourceLocation name = ModelLocationUtils.getModelLocation(block.value(), template.suffix.orElse(""));
        return template.create(name, textures, blockModels.modelOutput);
    }

    protected static void blockItemFromTemplate(BlockModelGenerators blockModels, Holder<Block> block, ModelTemplate template, TextureMapping textures) {
        ResourceLocation name = ModelLocationUtils.getModelLocation(block.value().asItem(), template.suffix.orElse(""));
        blockModels.registerSimpleItemModel(block.value(), template.create(name, textures, blockModels.modelOutput));
    }

    private void spellbook(ItemModelGenerators generator, Item item) {
        String name = NameUtils.getItemName(item);
        generator.generateFlatItem(item, ModelTemplates.createItem("spellbook", TextureSlot.LAYER0));
    }

    private void skillbook(ItemModelGenerators generator, Item item) {
        String name = NameUtils.getItemName(item);
        generator.generateFlatItem(item, ModelTemplates.createItem("skillbook", TextureSlot.LAYER0));
    }

    public void lilyPadBlock(BlockModelGenerators generator, Block block) {
        ResourceLocation resourcelocation = generator.createFlatItemModelWithBlockTexture(Items.LILY_PAD, Blocks.LILY_PAD);
        generator.registerSimpleTintedItemModel(block, resourcelocation, ItemModelUtils.constantTint(-9321636));
        generator.blockStateOutput.accept(BlockModelGenerators.createRotatedVariant(Blocks.LILY_PAD, ModelLocationUtils.getModelLocation(block)));
    }

    public void lantern(BlockModelGenerators generator, Block lanternBlock) { // todo: replace model and hanging model with my custom ones
        ResourceLocation model = TexturedModel.LANTERN.create(lanternBlock, generator.modelOutput);
        ResourceLocation hangingModel = TexturedModel.HANGING_LANTERN.create(lanternBlock, generator.modelOutput);

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(lanternBlock)
                .with(PropertyDispatch.property(BlockStateProperties.HANGING)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, hangingModel))
                        .select(false, Variant.variant().with(VariantProperties.MODEL, model))
                ));
        generator.registerSimpleFlatItemModel(lanternBlock);
    }

    public static void lampBlock(BlockModelGenerators generator, Block block) {
        ResourceLocation blockKey = key(block);
        String path = blockKey.getPath();

        ResourceLocation resourcelocation = TexturedModel.CUBE.create(Blocks.REDSTONE_LAMP, generator.modelOutput);
        ResourceLocation resourcelocation1 = generator.createSuffixedVariant(Blocks.REDSTONE_LAMP, "_on", ModelTemplates.CUBE_ALL, TextureMapping::cube);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(Blocks.REDSTONE_LAMP).with(generator.createBooleanModelDispatch(BlockStateProperties.LIT, resourcelocation1, resourcelocation)));
    }

    private void oysterBlock(BlockModelGenerators blockModels)
    {
        TextureSlot slotBase = TextureSlot.create("base");

        ResourceLocation oyster = blockModelFromTemplate(
                blockModels,
                BlockRegistry.PEARL_OYSTER_BLOCK.asHolder(),
                ModelTemplates.create("pearl_oyster", slotBase, TextureSlot.PARTICLE),
                new TextureMapping()
                        .put(slotBase, DUMMY_TEXTURE)
                        .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster"))
        );
        ResourceLocation oysterOpen = blockModelFromTemplate(
                blockModels,
                BlockRegistry.PEARL_OYSTER_BLOCK.asHolder(),
                ModelTemplates.create("pearl_oyster_open", "_open", slotBase, TextureSlot.PARTICLE),
                new TextureMapping()
                        .put(slotBase, DUMMY_TEXTURE)
                        .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster"))
        );
        ResourceLocation oysterOpenEmpty = blockModelFromTemplate(
                blockModels,
                BlockRegistry.PEARL_OYSTER_BLOCK.asHolder(),
                ModelTemplates.create("pearl_oyster_empty", "_empty", slotBase, TextureSlot.PARTICLE),
                new TextureMapping()
                        .put(slotBase, DUMMY_TEXTURE)
                        .put(TextureSlot.PARTICLE, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/pearl_oyster"))
        );

        variant(blockModels, BlockRegistry.PEARL_OYSTER_BLOCK.asHolder())
                .with(BlockModelGenerators.createBooleanModelDispatch(PearlOysterBlock.IS_OPEN, oyster, oysterOpen))
                .with(BlockModelGenerators.createBooleanModelDispatch(PearlOysterBlock.IS_EMPTY, oysterOpen, oysterOpenEmpty))
                .with(PropertyDispatch.property(PearlOysterBlock.FACING).generate((dir) -> {
                            return rotationToVariant((int) ((dir.toYRot() + 180) % 360));
                        })
                );
        blockModels.registerSimpleFlatItemModel(BlockRegistry.PEARL_OYSTER_BLOCK.get().asItem());
    }

    public static void tallDoorBlock(BlockModelGenerators blockModels, Block block, String baseName) {
        internalTallDoorBlock(blockModels, (TallDoorBlock)block, baseName, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "" + baseName + "_bottom"), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "" + baseName + "_middle"), ResourceLocation.fromNamespaceAndPath(Constants.MODID, "" + baseName + "_top"));
    }

    private static void internalTallDoorBlock(BlockModelGenerators blockModels, TallDoorBlock block, String baseName, ResourceLocation bottom, ResourceLocation middle, ResourceLocation top) {
        ModelFile bottomLeft = blockModels.models().withExistingParent(baseName + "_bottom_left", ":" + BlockModelGenerators.BLOCK_FOLDER + "/door_bottom_left").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile bottomLeftOpen = blockModels.models().withExistingParent(baseName + "_bottom_left_open", "" + BlockModelGenerators.BLOCK_FOLDER + "/door_bottom_left_open").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile bottomRight = blockModels.models().withExistingParent(baseName + "_bottom_right", "" + BlockModelGenerators.BLOCK_FOLDER + "/door_bottom_right").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile bottomRightOpen = blockModels.models().withExistingParent(baseName + "_bottom_right_open", "" + BlockModelGenerators.BLOCK_FOLDER + "/door_bottom_right_open").texture("bottom", bottom).texture("middle", middle).texture("top",top).renderType("cutout");
        ModelFile middleLeft = blockModels.models().withExistingParent(baseName + "_middle_left", Constants.MODID + ":" + BlockModelGenerators.BLOCK_FOLDER + "/door_middle_left").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile middleLeftOpen = blockModels.models().withExistingParent(baseName + "_middle_left_open", Constants.MODID + ":" + BlockModelGenerators.BLOCK_FOLDER + "/door_middle_left_open").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile middleRight = blockModels.models().withExistingParent(baseName + "_middle_right", Constants.MODID + ":" + BlockModelGenerators.BLOCK_FOLDER + "/door_middle_right").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile middleRightOpen = blockModels.models().withExistingParent(baseName + "_middle_right_open", Constants.MODID + ":" + BlockModelGenerators.BLOCK_FOLDER + "/door_middle_right_open").texture("bottom", middle).texture("middle", middle).texture("top", middle).renderType("cutout");
        ModelFile topLeft = blockModels.models().withExistingParent(baseName + "_top_left", "" + BlockModelGenerators.BLOCK_FOLDER + "/door_top_left").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile topLeftOpen = blockModels.models().withExistingParent(baseName + "_top_left_open", "" + BlockModelGenerators.BLOCK_FOLDER + "/door_top_left_open").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile topRight = blockModels.models().withExistingParent(baseName + "_top_right", "" + BlockModelGenerators.BLOCK_FOLDER + "/door_top_right").texture("bottom", bottom).texture("middle", middle).texture("top", top).renderType("cutout");
        ModelFile topRightOpen = blockModels.models().withExistingParent(baseName + "_top_right_open", "" + BlockModelGenerators.BLOCK_FOLDER + "/door_top_right_open").texture("bottom", bottom).texture("middle", middle).texture("top",top).renderType("cutout");
        tallDoorBlock(blockModels, block, bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen, middleLeft, middleLeftOpen, middleRight, middleRightOpen, topLeft, topLeftOpen, topRight, topRightOpen);
    }

    public static void tallDoorBlock(BlockModelGenerators blockModels, TallDoorBlock block, ModelFile bottomLeft, ModelFile bottomLeftOpen, ModelFile bottomRight, ModelFile bottomRightOpen, ModelFile middleLeft, ModelFile middleLeftOpen, ModelFile middleRight, ModelFile middleRightOpen, ModelFile topLeft, ModelFile topLeftOpen, ModelFile topRight, ModelFile topRightOpen) {
        blockModels.getVariantBuilder(block).forAllStatesExcept(state -> {
            int yRot = ((int) state.getValue(TallDoorBlock.FACING).toYRot()) + 90;
            TripleBlockPart third = state.getValue(TallDoorBlock.THIRD);
            boolean right = state.getValue(TallDoorBlock.HINGE) == DoorHingeSide.RIGHT;
            boolean open = state.getValue(TallDoorBlock.OPEN);
            if (open) {
                yRot += 90;
            }
            if (right && open) {
                yRot += 180;
            }
            yRot %= 360;

            ModelFile model = null;
            switch(third) {
                case LOWER:
                default:
                    if (right && open) {
                        model = bottomRightOpen;
                    } else if (!right && open) {
                        model = bottomLeftOpen;
                    }
                    if (right && !open) {
                        model = bottomRight;
                    } else if (!right && !open) {
                        model = bottomLeft;
                    }
                    break;
                case MIDDLE:
                    if (right && open) {
                        model = middleRightOpen;
                    } else if (!right && open) {
                        model = middleLeftOpen;
                    }
                    if (right && !open) {
                        model = middleRight;
                    } else if (!right && !open) {
                        model = middleLeft;
                    }
                    break;
                case UPPER:
                    if (right && open) {
                        model = topRightOpen;
                    } else if (!right && open) {
                        model = topLeftOpen;
                    }
                    if (right && !open) {
                        model = topRight;
                    } else if (!right && !open) {
                        model = topLeft;
                    }
                    break;
            }
            return ConfiguredModel.builder().modelFile(model).rotationY(yRot).build();
        }, TallDoorBlock.POWERED, TallDoorBlock.WATERLOGGED);
    }

    public static void daylightDetector(BlockModelGenerators blockModels, Block block) {
        ResourceLocation resourcelocation = TextureMapping.getBlockTexture(block, "_side");
        TextureMapping texturemapping = (new TextureMapping()).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top")).put(TextureSlot.SIDE, resourcelocation);
        TextureMapping texturemapping1 = (new TextureMapping()).put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_inverted_top")).put(TextureSlot.SIDE, resourcelocation);
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.INVERTED).select(false, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.DAYLIGHT_DETECTOR.create(block, texturemapping, blockModels.modelOutput))).select(true, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.DAYLIGHT_DETECTOR.create(ModelLocationUtils.getModelLocation(block, "_inverted"), texturemapping1, blockModels.modelOutput)))));

    }

    public static void glassBlock(BlockModelGenerators blockModels, Block block) {
        ResourceLocation blockKey = BuiltInRegistries.BLOCK.getKey(block);
        String path = blockKey.getPath();
        blockModels.createTrivialCube(block);
//        blockModels.simpleBlock(block, blockModels.models().cubeAll(path, blockModels.modLoc("block/" + path)).renderType("translucent"));
//        blockModels.simpleBlockItem(block, blockModels.models().getExistingFile(blockModels.modLoc("block/" + path)));
    }

    public static void pressurePlateBlock(BlockModelGenerators generator, Block block, ResourceLocation texture) {
        ModelTemplate plateUp = ModelTemplates.PRESSURE_PLATE_UP;
        ModelTemplate plateDown = ModelTemplates.PRESSURE_PLATE_DOWN;

        TextureMapping mapping = TextureMapping.defaultTexture(texture);
        ResourceLocation upModel = plateUp.create(block, mapping, generator.modelOutput);
        ResourceLocation downModel = plateDown.create(block, mapping, generator.modelOutput);

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.POWERED)
                        .select(false, Variant.variant().with(VariantProperties.MODEL, upModel))
                        .select(true, Variant.variant().with(VariantProperties.MODEL, downModel))));
    }

    public static void torchBlock(BlockModelGenerators generator, Block block) {
        TextureMapping texturemapping = TextureMapping.torch(block);
        ResourceLocation model = ModelTemplates.TORCH.create(block, texturemapping, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
        generator.registerSimpleFlatItemModel(block);
    }

    public static void wallTorchBlock(BlockModelGenerators generator, Block torchBlock, Block wallTorchBlock) {
        TextureMapping texturemapping = TextureMapping.torch(torchBlock);
        ResourceLocation model = ModelTemplates.WALL_TORCH.create(wallTorchBlock, texturemapping, generator.modelOutput);

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(wallTorchBlock)
                .with(BlockModelGenerators.createTorchHorizontalDispatch()));
        generator.registerSimpleFlatItemModel(torchBlock);
    }

    public static void turnStoneBlock(BlockModelGenerators generator, Block block) {
//        ResourceLocation model = ModelLocationUtils.getModelLocation(block);
        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    public static void runeStoneBlock(BlockModelGenerators generator, Block block) {
        ResourceLocation powered = ModelLocationUtils.getModelLocation(block, "_powered");
        ResourceLocation notPowered = ModelLocationUtils.getModelLocation(block);

        generator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.property(BlockStateProperties.POWERED)
                        .select(true, Variant.variant().with(VariantProperties.MODEL, powered))
                        .select(false, Variant.variant().with(VariantProperties.MODEL, notPowered)))
                .with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    public static void mushroomBlock(BlockModelGenerators generator, Block block) {
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/triple_mushroom_generic")).with(BlockModelGenerators.createHorizontalFacingDispatchAlt()));
        generator.registerSimpleItemModel(block, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "block/triple_mushroom_generic"));

    }

    public static void woolAndCarpet(BlockModelGenerators generator, Block woolBlock, Block carpetBlock) {
        // Wool block
        TexturedModel woolModel = TexturedModel.CUBE.get(woolBlock);
        ResourceLocation woolLocation = woolModel.create(woolBlock, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(woolBlock, woolLocation));

        // Carpet block
        TexturedModel carpetModel = TexturedModel.CARPET.get(woolBlock);
        ResourceLocation carpetLocation = carpetModel.create(carpetBlock, generator.modelOutput);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(carpetBlock, carpetLocation));

        generator.registerSimpleItemModel(woolBlock, woolLocation);
        generator.registerSimpleItemModel(carpetBlock, carpetLocation);
    }
}
