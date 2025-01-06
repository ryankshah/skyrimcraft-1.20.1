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

public class DwemerBenchModel<T extends LivingEntity> extends EntityModel<T>
{
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Constants.MODID, "dwemer_bench"), "main");
    private final ModelPart dwemer_chair;

    public DwemerBenchModel(ModelPart root) {
        this.dwemer_chair = root.getChild("dwemer_bench");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition dwemer_metal_bench = partdefinition.addOrReplaceChild("dwemer_metal_bench", CubeListBuilder.create().texOffs(2, 64).addBox(-2.0F, -14.0F, -13.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-3.0F, -16.0F, -14.0F, 32.0F, 2.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(4, 54).addBox(-3.0F, -23.0F, 0.0F, 32.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 56).addBox(0.0F, -26.0F, 0.0F, 26.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 58).addBox(3.0F, -29.0F, 0.0F, 20.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(4, 36).addBox(6.0F, -32.0F, 0.0F, 14.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(-2.0F, -16.0F, 0.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 64).addBox(26.0F, -16.0F, 0.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(2, 64).addBox(26.0F, -14.0F, -13.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 24.0F, 6.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {
        dwemer_chair.render(poseStack, vertexConsumer, i, i1, i2);
    }
}