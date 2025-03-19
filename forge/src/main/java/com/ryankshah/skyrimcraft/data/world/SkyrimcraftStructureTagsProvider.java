package com.ryankshah.skyrimcraft.data.world;

import com.ryankshah.skyrimcraft.registry.StructureRegistry;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class SkyrimcraftStructureTagsProvider extends StructureTagsProvider
{
    public SkyrimcraftStructureTagsProvider(PackOutput p_256522_, CompletableFuture<HolderLookup.Provider> p_256661_, String modId,  ExistingFileHelper existingFileHelper) {
        super(p_256522_, p_256661_, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TagsRegistry.StructureTagsInit.NETHER_FORTRESS)
                .add(BuiltinStructures.FORTRESS);
        this.tag(TagsRegistry.StructureTagsInit.PYRAMID)
                .add(BuiltinStructures.DESERT_PYRAMID);
        this.tag(TagsRegistry.StructureTagsInit.RUINED_PORTAL)
                .add(BuiltinStructures.RUINED_PORTAL_DESERT)
                .add(BuiltinStructures.RUINED_PORTAL_JUNGLE)
                .add(BuiltinStructures.RUINED_PORTAL_MOUNTAIN)
                .add(BuiltinStructures.RUINED_PORTAL_OCEAN)
                .add(BuiltinStructures.RUINED_PORTAL_NETHER)
                .add(BuiltinStructures.RUINED_PORTAL_STANDARD)
                .add(BuiltinStructures.RUINED_PORTAL_SWAMP);
        this.tag(TagsRegistry.StructureTagsInit.SHOUT_WALL)
                .add(StructureRegistry.SHOUT_WALL_KEY);
//        this.tag(TagsRegistry.StructureTagsInit.DWARVEN_RUINS)
//                .add(StructureRegistry.DWARVEN_RUINS_KEY);
        this.tag(TagsRegistry.StructureTagsInit.WATCHTOWER)
                .add(StructureRegistry.WATCHTOWER_1_KEY);
        this.tag(TagsRegistry.StructureTagsInit.GIANT_CAMP)
                .add(StructureRegistry.GIANT_CAMP_PLAINS_KEY);
    }
}
