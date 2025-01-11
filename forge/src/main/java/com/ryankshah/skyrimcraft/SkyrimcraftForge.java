package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.capability.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.data.DataGenerators;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimLootModifiers;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.world.CommonSpawning;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Constants.MODID)
public class SkyrimcraftForge
{
    public SkyrimcraftForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SkyrimcraftCommon.init();
        SkyrimLootModifiers.GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
        eventBus.addListener(this::commonSetup);
        eventBus.addListener(this::registerEntityAttributes);
        eventBus.addListener(DataGenerators::gatherData);
    }

    @SubscribeEvent
    public void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Character.class);
        event.register(ExtraCharacter.class);
        event.register(LevelUpdates.class);
        event.register(StatIncreases.class);
        event.register(PlayerQuests.class);
    }

    @SubscribeEvent
    public void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            CharacterCapability characterCapability = new CharacterCapability();
            event.addCapability(CharacterCapability.ID, characterCapability);
            event.addListener(characterCapability::onInvalidate);

            ExtraCharacterCapability extraCharacterCapability = new ExtraCharacterCapability();
            event.addCapability(ExtraCharacterCapability.ID, extraCharacterCapability);
            event.addListener(extraCharacterCapability::onInvalidate);

            StatIncreasesCapability siC = new StatIncreasesCapability();
            event.addCapability(StatIncreasesCapability.ID, siC);
            event.addListener(siC::onInvalidate);

            LevelUpdatesCapability lUC = new LevelUpdatesCapability();
            event.addCapability(LevelUpdatesCapability.ID, lUC);
            event.addListener(lUC::onInvalidate);

            PlayerQuestsCapability pQC = new PlayerQuestsCapability();
            event.addCapability(PlayerQuestsCapability.ID, pQC);
            event.addListener(pQC::onInvalidate);

            ConjureFamiliarCapability cFC = new ConjureFamiliarCapability();
            event.addCapability(ConjureFamiliarCapability.ID, cFC);
            event.addListener(cFC::onInvalidate);
        }
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        EntityRegistry.registerEntityAttributes(event::put);
        CommonSpawning.placements();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(SkyrimcraftCommon::setupTerraBlender);
    }
}