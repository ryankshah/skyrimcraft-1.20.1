package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.capability.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.data.DataGenerators;
import com.ryankshah.skyrimcraft.data.loot_table.SkyrimLootModifiers;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.world.CommonSpawning;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
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
//        eventBus.addListener(this::registerKeys);
        eventBus.addListener(DataGenerators::gatherData);
    }

//    private void registerKeys(RegisterKeyMappingsEvent event) {
//        event.register(KeysRegistry.MENU_KEY);
//        event.register(KeysRegistry.SPELL_SLOT_1_KEY);
//        event.register(KeysRegistry.SPELL_SLOT_2_KEY);
//        event.register(KeysRegistry.PICKPOCKET_KEY);
//    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        EntityRegistry.registerEntityAttributes(event::put);
        CommonSpawning.placements();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(SkyrimcraftCommon::setupTerraBlender);
    }
}