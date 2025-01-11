package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.DwemerCube;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DwemerCubeModel extends GeoModel<DwemerCube>
{
    @Override
    public ResourceLocation getModelResource(DwemerCube object)
    {
        return new ResourceLocation(Constants.MODID, "geo/dwemer_cube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DwemerCube object) {
        return new ResourceLocation(Constants.MODID, "textures/entity/dwemer_cube.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DwemerCube object)
    {
        return new ResourceLocation(Constants.MODID, "animations/dwemer_cube.animation.json");
    }
}