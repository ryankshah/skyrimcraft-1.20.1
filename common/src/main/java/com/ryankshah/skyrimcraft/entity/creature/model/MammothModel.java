package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Mammoth;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MammothModel extends GeoModel<Mammoth>
{
    @Override
    public ResourceLocation getModelResource(Mammoth object)
    {
        return new ResourceLocation(Constants.MODID, "geo/mammoth.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Mammoth object) {
        return new ResourceLocation(Constants.MODID, "textures/entity/mammoth.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Mammoth object)
    {
        return new ResourceLocation(Constants.MODID, "animations/mammoth.animation.json");
    }
}