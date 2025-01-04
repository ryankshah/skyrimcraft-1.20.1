package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.init.EntityRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class SkyrimcraftFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        SkyrimcraftCommon.init();
        EntityRegistry.attributeSuppliers.forEach(
                p -> FabricDefaultAttributeRegistry.register(p.entityTypeSupplier().get(), p.factory().get().build())
        );
    }
}
