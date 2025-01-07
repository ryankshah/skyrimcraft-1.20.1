package com.ryankshah.skyrimcraft.entity.creature.render;

import com.ryankshah.skyrimcraft.entity.creature.Slaughterfish;
import com.ryankshah.skyrimcraft.entity.creature.model.SlaughterfishModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SlaughterfishRenderer extends GeoEntityRenderer<Slaughterfish>
{
    public SlaughterfishRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new SlaughterfishModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }
}
