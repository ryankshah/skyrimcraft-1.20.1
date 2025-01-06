package com.ryankshah.skyrimcraft.entity.furniture.render;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.entity.furniture.DwemerChair;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class DwemerChairRenderer extends EntityRenderer<DwemerChair>
{
    public static final ResourceLocation DEFAULT_SKIN_LOCATION = ResourceLocation.fromNamespaceAndPath(Constants.MODID, "textures/entity/dwemer_chair.png");

    public DwemerChairRenderer(EntityRendererProvider.Context p_173915_) {
        super(p_173915_);
    }

    public ResourceLocation getTextureLocation(DwemerChair entity) {
        return DEFAULT_SKIN_LOCATION;
    }

//
//    protected void setupRotations(DwemerChair entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
//        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - yBodyRot));
//        float f = (float)(entity.level().getGameTime() - entity.lastHit) + partialTick;
//        if (f < 5.0F) {
//            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(f / 1.5F * 3.1415927F) * 3.0F));
//        }
//
//    }
//
//    @Nullable
//    protected RenderType getRenderType(DwemerChair livingEntity, boolean bodyVisible, boolean translucent, boolean glowing) {
//        if (!livingEntity.isMarker()) {
//            return super.getRenderType(livingEntity, bodyVisible, translucent, glowing);
//        } else {
//            ResourceLocation resourcelocation = this.getTextureLocation(livingEntity);
//            if (translucent) {
//                return RenderType.entityTranslucent(resourcelocation, false);
//            } else {
//                return bodyVisible ? RenderType.entityCutoutNoCull(resourcelocation, false) : null;
//            }
//        }
//    }
}
