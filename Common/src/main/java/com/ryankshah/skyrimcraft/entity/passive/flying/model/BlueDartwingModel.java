package com.ryankshah.skyrimcraft.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.passive.flying.BlueDartwing;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class BlueDartwingModel extends GeoModel<BlueDartwing>
{
    @Override
    public ResourceLocation getModelResource(BlueDartwing object)
    {
        return new ResourceLocation(Constants.MODID, "geo/bluedartwing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BlueDartwing object)
    {
        return new ResourceLocation(Constants.MODID, "textures/entity/bluedartwing.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BlueDartwing object)
    {
        return new ResourceLocation(Constants.MODID, "animations/bluedartwing.animation.json");
    }
}
