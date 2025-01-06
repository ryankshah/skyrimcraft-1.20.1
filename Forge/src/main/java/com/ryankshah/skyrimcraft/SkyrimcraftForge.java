package com.ryankshah.skyrimcraft;

import com.mojang.serialization.Codec;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.data.DataGenerators;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimLootModifiers;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.world.CommonSpawning;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@Mod(Constants.MODID)
public class SkyrimcraftForge
{
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(ForgeRegistries.ATTACHMENT_TYPES, Constants.MODID);

    public static final Supplier<AttachmentType<Character>> CHARACTER = ATTACHMENT_TYPES.register(
            "character", () -> AttachmentType.builder(Character::new).serialize(Character.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<ExtraCharacter>> EXTRA_CHARACTER = ATTACHMENT_TYPES.register(
            "extra_character", () -> AttachmentType.builder(() -> new ExtraCharacter()).serialize(ExtraCharacter.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<LevelUpdates>> LEVEL_UPDATES = ATTACHMENT_TYPES.register(
            "level_updates", () -> AttachmentType.builder(() -> new LevelUpdates()).serialize(LevelUpdates.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<StatIncreases>> STAT_INCREASES = ATTACHMENT_TYPES.register(
            "stat_increases", () -> AttachmentType.builder(StatIncreases::new).serialize(StatIncreases.CODEC).copyOnDeath().build());
    public static final Supplier<AttachmentType<PlayerQuests>> QUESTS = ATTACHMENT_TYPES.register(
            "quests", () -> AttachmentType.builder(PlayerQuests::new).serialize(PlayerQuests.CODEC).copyOnDeath().build());

    public static final Supplier<AttachmentType<Long>> CONJURE_FAMILIAR_SPELL_DATA = ATTACHMENT_TYPES.register(
            "conjure_familiar_spell_data", () -> AttachmentType.builder(() -> 0L).serialize(Codec.LONG).build());

    public SkyrimcraftForge(IEventBus eventBus) {
        SkyrimcraftCommon.init();
        SkyrimLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
        eventBus.addListener(DataGenerators::gatherData);

        ATTACHMENT_TYPES.register(eventBus);

        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::registerEntityAttributes);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        EntityRegistry.registerEntityAttributes(event::put);
        CommonSpawning.placements();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(SkyrimcraftCommon::setupTerraBlender);
    }
}