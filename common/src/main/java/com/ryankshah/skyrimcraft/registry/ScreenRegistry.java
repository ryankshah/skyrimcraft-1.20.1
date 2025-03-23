package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.screen.LockPickingScreen;
import net.minecraft.client.gui.screens.MenuScreens;

public class ScreenRegistry
{
    public static void register() {
        MenuScreens.register(ContainerRegistry.LOCK_PICKING.get(), LockPickingScreen::new);
    }
}