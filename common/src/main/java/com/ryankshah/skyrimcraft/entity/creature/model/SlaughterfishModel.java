package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.Slaughterfish;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SlaughterfishModel extends GeoModel<Slaughterfish>
{
    @Override
    public ResourceLocation getModelResource(Slaughterfish animatable) {
        return new ResourceLocation(Constants.MODID, "geo/slaughterfish.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Slaughterfish animatable) {
        return new ResourceLocation(Constants.MODID, "textures/entity/slaughterfish.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Slaughterfish animatable) {
        return new ResourceLocation(Constants.MODID, "animations/slaughterfish.animation.json");
    }
}