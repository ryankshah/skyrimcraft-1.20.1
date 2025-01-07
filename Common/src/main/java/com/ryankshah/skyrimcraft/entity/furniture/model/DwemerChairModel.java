package com.ryankshah.skyrimcraft.entity.furniture.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class DwemerChairModel<T extends LivingEntity> extends EntityModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Constants.MODID, "dwemer_chair"), "main");
    private final ModelPart dwemer_chair;

    public DwemerChairModel(ModelPart root) {
        this.dwemer_chair = root.getChild("dwemer_chair");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition dwemer_chair = partdefinition.addOrReplaceChild("dwemer_chair", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, 0.0F, 16.0F, 13.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(28, 29).addBox(10.0F, -16.0F, 12.0F, 2.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 29).addBox(-4.0F, -16.0F, 12.0F, 2.0F, 16.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 29).addBox(-2.0F, -15.0F, 13.0F, 12.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 11.0F, -8.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, float v, float v1, float v2, float v3) {
        dwemer_chair.render(poseStack, vertexConsumer, i, i1, v, v1, v2, v3);
    }
}