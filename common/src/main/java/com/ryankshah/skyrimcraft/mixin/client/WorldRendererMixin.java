package com.ryankshah.skyrimcraft.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.SkyrimcraftCommonClient;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class WorldRendererMixin
{
    // Before first checkPoseStack call
    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;checkPoseStack(Lcom/mojang/blaze3d/vertex/PoseStack;)V", ordinal = 0), method = "renderLevel")
    private void renderLevel(PoseStack mtx, float pt, long nanoTime, boolean renderOutline, Camera cam, GameRenderer gr, LightTexture lightTex, Matrix4f proj, CallbackInfo ci)
    {
        SkyrimcraftCommonClient.renderLocks(mtx, Minecraft.getInstance().renderBuffers().bufferSource(), ClientUtil.getFrustum(mtx, proj), pt);
    }

}