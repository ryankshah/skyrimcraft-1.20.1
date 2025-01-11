package com.ryankshah.skyrimcraft.registry;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureManager;

public class ParticleRenderTypeRegistry
{
    public static void init() {}

    public static ParticleRenderType GLOW = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
            //return new BufferBuilder(new ByteBufferBuilder(512), VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator pTesselator) {
        }
    };

    public static ParticleRenderType ADDITIVE = new ParticleRenderType() {
        @Override
        public void begin(BufferBuilder pBuilder, TextureManager pTextureManager) {
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.enableCull();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
//            return new BufferBuilder(new ByteBufferBuilder(512), VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
        }

        @Override
        public void end(Tesselator pTesselator) {
        }
    };

//    public static ParticleRenderType TRANSLUCENT = new ParticleRenderType() {
//        @Nullable
//        @Override
//        public BufferBuilder begin(Tesselator p_350949_, TextureManager p_107437_) {
//            RenderSystem.depthMask(false);
//            RenderSystem.enableBlend();
//            RenderSystem.enableCull();
//            RenderSystem.enableDepthTest();
//            RenderSystem.setShaderTexture(0, TextureAtlas.LOCATION_PARTICLES);
//            return new BufferBuilder(new ByteBufferBuilder(512), VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
//        }
//    };
//
//    public static ParticleRenderType CUSTOM = new ParticleRenderType() {
//        @Nullable
//        @Override
//        public BufferBuilder begin(Tesselator p_350949_, TextureManager p_107437_) {
//            return new BufferBuilder(new ByteBufferBuilder(512), VertexFormat.Mode.QUADS, DefaultVertexFormat.NEW_ENTITY);
//        }
//    };
}
