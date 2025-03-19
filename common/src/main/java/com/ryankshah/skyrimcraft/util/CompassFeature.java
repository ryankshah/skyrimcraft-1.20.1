package com.ryankshah.skyrimcraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Structures;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.AbstractMap;

public class CompassFeature
{
    private TagKey<Structure> feature;
    private BlockPos blockPos;
    private String id;

    public static Codec<CompassFeature> CODEC = RecordCodecBuilder.create(cf -> cf.group(
            Codec.STRING.fieldOf("id").forGetter(CompassFeature::getId),
            TagKey.codec(Registries.STRUCTURE).fieldOf("feature").forGetter(CompassFeature::getFeature),
            BlockPos.CODEC.fieldOf("blockPos").forGetter(CompassFeature::getBlockPos)
    ).apply(cf, CompassFeature::new));

    public static final int ICON_WIDTH = 15, ICON_HEIGHT = 15;

    public CompassFeature(String id, TagKey<Structure> feature, BlockPos blockPos) {
        this.feature = feature;
        this.blockPos = blockPos;
        this.id = id;
    }

    public TagKey<Structure> getFeature() {
        return feature;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public String getId() {
        return id;
    }

    public AbstractMap.SimpleEntry<Integer, Integer> getIconUV() {
        if(feature.equals(StructureTags.VILLAGE)) {
            return new AbstractMap.SimpleEntry<>(0, 120);
        } else if(feature.equals(TagsRegistry.StructureTagsInit.NETHER_FORTRESS)) {
            return new AbstractMap.SimpleEntry<>(16, 120);
        } else if(feature.equals(TagsRegistry.StructureTagsInit.SHOUT_WALL)) {
            return new AbstractMap.SimpleEntry<>(31, 120);
        } else if(feature.equals(StructureTags.MINESHAFT)) {
            return new AbstractMap.SimpleEntry<>(46, 120);
        } else if(feature.equals(StructureTags.SHIPWRECK)) {
            return new AbstractMap.SimpleEntry<>(16, 152);
//        } else if(feature.equals(StructureTags.RUINED_PORTAL)) {
//            return new AbstractMap.SimpleEntry<>(0, 136);
        } else if(feature.equals(TagsRegistry.StructureTagsInit.GIANT_CAMP)) {
            return new AbstractMap.SimpleEntry<>(31, 136);
//        } else if(feature.equals(TagsRegistry.StructureTagsInit.DWARVEN_RUINS)) {
//            return new AbstractMap.SimpleEntry<>(17, 143);
        } else if(feature.equals(TagsRegistry.StructureTagsInit.WATCHTOWER)) {
            return new AbstractMap.SimpleEntry<>(46, 136);
        } else if(feature.equals(TagsRegistry.StructureTagsInit.PYRAMID)) {
            return new AbstractMap.SimpleEntry<>(46, 152);
        } else if(feature.equals(TagsRegistry.StructureTagsInit.RUINED_PORTAL)) {
            return new AbstractMap.SimpleEntry<>(0, 136);
        } else return null;
    }// 50

    public String getFeatureName() {
        if(feature.equals(StructureTags.VILLAGE)) {
            return "Village";
        } else if(feature.equals(TagsRegistry.StructureTagsInit.NETHER_FORTRESS)) {
            return "Nether Fortress";
        } else if(feature.equals(TagsRegistry.StructureTagsInit.SHOUT_WALL)) {
            return "Word Wall";
        } else if(feature.equals(StructureTags.MINESHAFT)) {
            return "Abandoned Mineshaft";
        } else if(feature.equals(StructureTags.SHIPWRECK)) {
            return "Shipwreck";
//        } else if(feature.equals(StructureTags.RUINED_PORTAL)) {
//            return "Ruined Portal";
        } else if(feature.equals(TagsRegistry.StructureTagsInit.GIANT_CAMP)) {
            return "Giant Camp";
//        } else if(feature.equals(TagsRegistry.StructureTagsInit.DWARVEN_RUINS)) {
//            return "Dwarven Ruins";
        } else if(feature.equals(TagsRegistry.StructureTagsInit.WATCHTOWER)) {
            return "Watchtower";
        } else if(feature.equals(TagsRegistry.StructureTagsInit.PYRAMID)) {
            return "Pyramid";
        } else if(feature.equals(TagsRegistry.StructureTagsInit.RUINED_PORTAL)) {
            return "Ruined Portal";
        } else return "";
    }

    public CompoundTag serialise() {
        CompoundTag nbt = new CompoundTag();

        nbt.putString("id", id);
        nbt.putString("resourcelocation", feature.location().toString());
        nbt.putInt("xPos", blockPos.getX());
        nbt.putInt("yPos", blockPos.getY());
        nbt.putInt("zPos", blockPos.getZ());

        return nbt;
    }

    public static CompassFeature deserialise(CompoundTag nbt) {
        String id = nbt.getString("id");
        ResourceLocation feature = ResourceLocation.tryParse(nbt.getString("resourcelocation"));
        int x = nbt.getInt("xPos");
        int y = nbt.getInt("yPos");
        int z = nbt.getInt("zPos");
        BlockPos blockPos = new BlockPos(x, y, z);
        return new CompassFeature(id, TagKey.create(Registries.STRUCTURE, feature), blockPos);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof CompassFeature))
            return false;

        CompassFeature featureToCompare = (CompassFeature)obj;
        return this.feature.equals(featureToCompare.feature) && this.blockPos.getX() == featureToCompare.getBlockPos().getX() && this.getBlockPos().getZ() == featureToCompare.getBlockPos().getZ();
    }
}