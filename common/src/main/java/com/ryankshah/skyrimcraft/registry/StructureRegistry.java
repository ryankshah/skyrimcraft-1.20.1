package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.registration.RegistryObject;
import com.mojang.serialization.MapCodec;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.world.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class StructureRegistry
{
    public static void init() {}

//    public static final RegistrationProvider<Structure> STRUCTURE_EXPLICIT = RegistrationProvider.get(Registries.STRUCTURE, Constants.MODID);
    public static final ResourceKey<Structure> SHOUT_WALL_KEY = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID, "shout_wall"));
    public static final ResourceKey<Structure> GIANT_CAMP_PLAINS_KEY = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID, "giant_camp_plains"));
    public static final ResourceKey<Structure> WATCHTOWER_1_KEY = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID, "watchtower_1"));
//    public static final ResourceKey<Structure> DWARVEN_RUINS_KEY = ResourceKey.create(Registries.STRUCTURE, new ResourceLocation(Constants.MODID, "dwarven_ruins"));


    public static final RegistrationProvider<StructureType<?>> STRUCTURES = RegistrationProvider.get(Registries.STRUCTURE_TYPE, Constants.MODID);

    public static final RegistryObject<StructureType<ShoutWallStructures>> SHOUT_WALL = STRUCTURES.register("shout_wall", () -> explicitStructureTypeTyping(ShoutWallStructures.CODEC));
    public static final RegistryObject<StructureType<GiantCampStructures>> GIANT_CAMP = STRUCTURES.register("giant_camp", () -> explicitStructureTypeTyping(GiantCampStructures.CODEC));
    public static final RegistryObject<StructureType<DungeonStructures>> DUNGEON = STRUCTURES.register("skyrim_dungeon", () -> explicitStructureTypeTyping(DungeonStructures.CODEC));
    public static final RegistryObject<StructureType<WatchtowerStructures>> WATCHTOWER = STRUCTURES.register("watchtower", () -> explicitStructureTypeTyping(WatchtowerStructures.CODEC));
    public static final RegistryObject<StructureType<RuinsStructures>> RUINS = STRUCTURES.register("ruins", () -> explicitStructureTypeTyping(RuinsStructures.CODEC));
    public static final RegistryObject<StructureType<MonumentStructures>> MONUMENTS = STRUCTURES.register("monuments", () -> explicitStructureTypeTyping(MonumentStructures.CODEC));
    public static final RegistryObject<StructureType<AltarStructures>> ALTAR = STRUCTURES.register("altars", () -> explicitStructureTypeTyping(AltarStructures.CODEC));
    public static final RegistryObject<StructureType<VillageStructures>> VILLAGE = STRUCTURES.register("village", () -> explicitStructureTypeTyping(VillageStructures.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return structureCodec::codec;
//        return () -> structureCodec;
    }
}