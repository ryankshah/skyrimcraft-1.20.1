package com.ryankshah.skyrimcraft.entity.passive.flying.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.passive.flying.OrangeDartwing;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;


public class OrangeDartwingModel extends GeoModel<OrangeDartwing>
{
    @Override
    public ResourceLocation getModelResource(OrangeDartwing object)
    {
        return new ResourceLocation(Constants.MODID, "geo/orangedartwing.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(OrangeDartwing object)
    {
        return new ResourceLocation(Constants.MODID, "textures/entity/orangedartwing.png");
    }

    @Override
    public ResourceLocation getAnimationResource(OrangeDartwing object)
    {
        return new ResourceLocation(Constants.MODID, "animations/orangedartwing.animation.json");
    }
}
