package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Giant;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GiantModel extends GeoModel<Giant>
{
    @Override
    public ResourceLocation getModelResource(Giant object)
    {
        return new ResourceLocation(Constants.MODID, "geo/giant.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Giant object) {
        return new ResourceLocation(Constants.MODID, "textures/entity/giant.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Giant object)
    {
        return new ResourceLocation(Constants.MODID, "animations/giant.animation.json");
    }
}