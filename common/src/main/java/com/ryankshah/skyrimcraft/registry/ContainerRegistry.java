package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.registration.RegistryObject;
import com.ryankshah.skyrimcraft.screen.container.LockPickingContainer;
import commonnetwork.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ContainerRegistry
{
    public static void init() {}

    public static final RegistrationProvider<MenuType<?>>
            CONTAINER_TYPES = RegistrationProvider.get(BuiltInRegistries.MENU, Constants.MOD_ID);

    public static final RegistryObject<MenuType<LockPickingContainer>>
            LOCK_PICKING = add("lock_picking", new MenuType<>(LockPickingContainer::new, FeatureFlags.DEFAULT_FLAGS)); //(id, inv) -> new LockPickingContainer(id, inv.player, inv.player.getUsedItemHand(), Services.PLATFORM.getLockableHandler(inv.player.level()).getLoaded().get(0)), FeatureFlags.DEFAULT_FLAGS));

    private ContainerRegistry() {}

    public static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> add(String name, MenuType<T> type)
    {
        return CONTAINER_TYPES.register(name, () -> type);
    }
}