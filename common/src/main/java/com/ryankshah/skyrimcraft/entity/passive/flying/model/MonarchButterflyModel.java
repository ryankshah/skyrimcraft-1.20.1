package com.ryankshah.skyrimcraft.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.passive.flying.MonarchButterfly;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class MonarchButterflyModel extends GeoModel<MonarchButterfly>
{
    @Override
    public ResourceLocation getModelResource(MonarchButterfly object)
    {
        return new ResourceLocation(Constants.MODID, "geo/monarchbutterfly.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MonarchButterfly object)
    {
        return new ResourceLocation(Constants.MODID, "textures/entity/monarchbutterfly.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MonarchButterfly object)
    {
        return new ResourceLocation(Constants.MODID, "animations/monarchbutterfly.animation.json");
    }
}
