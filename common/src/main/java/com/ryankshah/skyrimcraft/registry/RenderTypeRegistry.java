package com.ryankshah.skyrimcraft.registry;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.ryankshah.skyrimcraft.Constants;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

import java.util.OptionalDouble;

public class RenderTypeRegistry extends RenderType
{
    public static void init() {}

    public RenderTypeRegistry(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static final RenderType OVERLAY_LINES = RenderType.create(Constants.MODID + ".overlay_lines", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.LINES, 256, false, false, RenderType.CompositeState.builder()
            .setLineState(new LineStateShard(OptionalDouble.empty()))
            .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
            .setDepthTestState(RenderStateShard.NO_DEPTH_TEST)
            .setWriteMaskState(RenderStateShard.COLOR_WRITE)
            .createCompositeState(false));

    private static final RenderType LIGHTNING = create(Constants.MODID + ".lightning", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 256,
            false, true, CompositeState.builder()
                    .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
                    .setTransparencyState(LIGHTNING_TRANSPARENCY)
                    .createCompositeState(false));

    public static @NotNull RenderType lightning() {
        return LIGHTNING;
    }

    public static @NotNull RenderType overlayLines() { return OVERLAY_LINES; }
}