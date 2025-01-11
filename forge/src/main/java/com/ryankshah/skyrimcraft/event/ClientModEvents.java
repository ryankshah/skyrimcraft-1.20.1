package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT) // GAME or MOD event bus??
public class ClientModEvents
{
    @SubscribeEvent
    public static void onKeyBinds(RegisterKeyMappingsEvent event) {
        event.register(KeysRegistry.MENU_KEY);
        event.register(KeysRegistry.SPELL_SLOT_1_KEY);
        event.register(KeysRegistry.SPELL_SLOT_2_KEY);
        event.register(KeysRegistry.PICKPOCKET_KEY);

        event.register(KeysRegistry.SKYRIM_MENU_ENTER);
        event.register(KeysRegistry.SKYRIM_MENU_NORTH);
        event.register(KeysRegistry.SKYRIM_MENU_SOUTH);
        event.register(KeysRegistry.SKYRIM_MENU_WEST);
        event.register(KeysRegistry.SKYRIM_MENU_EAST);
    }
}