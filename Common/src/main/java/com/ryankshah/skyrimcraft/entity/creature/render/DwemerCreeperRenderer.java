package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.entity.creature.DwemerCreeper;
import com.ryankshah.skyrimcraft.entity.creature.model.DwemerCreeperModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DwemerCreeperRenderer extends GeoEntityRenderer<DwemerCreeper>
{
    public DwemerCreeperRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DwemerCreeperModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }
}