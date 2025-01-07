package com.ryankshah.skyrimcraft.entity.creature.model;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.creature.DwarvenSpider;
import com.ryankshah.skyrimcraft.entity.creature.DwemerCreeper;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class DwemerCreeperModel extends GeoModel<DwemerCreeper>
{
    @Override
    public ResourceLocation getModelResource(DwemerCreeper object)
    {
        return new ResourceLocation(Constants.MODID, "geo/dwemer_creeper.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DwemerCreeper object) {
        return new ResourceLocation(Constants.MODID, "textures/entity/dwemer_creeper.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DwemerCreeper object)
    {
        return new ResourceLocation(Constants.MODID, "animations/dwemer_creeper.animation.json");
    }
}