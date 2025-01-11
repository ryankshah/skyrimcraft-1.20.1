package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.entity.creature.DwemerCreeper;
import com.ryankshah.skyrimcraft.entity.creature.DwemerCube;
import com.ryankshah.skyrimcraft.entity.creature.model.DwemerCreeperModel;
import com.ryankshah.skyrimcraft.entity.creature.model.DwemerCubeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DwemerCubeRenderer extends GeoEntityRenderer<DwemerCube>
{
    public DwemerCubeRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new DwemerCubeModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }
}