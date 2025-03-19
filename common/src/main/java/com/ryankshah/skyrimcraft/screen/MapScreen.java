package com.ryankshah.skyrimcraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.network.character.FastTravel;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.KeysRegistry;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import com.ryankshah.skyrimcraft.util.RenderUtil;
import com.ryankshah.skyrimcraft.util.Waypoint;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import org.joml.Matrix4f;

import java.util.List;
import java.util.Set;

public class MapScreen extends Screen
{
    private static final int INITIAL_RENDER_DISTANCE = 8;
    private static final int CHUNK_SIZE = 16;
    private static final int MIN_BLOCK_SIZE = 2; //1
    private static final int MAX_BLOCK_SIZE = 6; //8
    private static final int ICON_WIDTH = 12;
    private static final int ICON_HEIGHT = 16;
    private static final float BASE_MARKER_SIZE = 8.0f;
    private final ResourceLocation OVERLAY_ICONS = new ResourceLocation(Constants.MODID, "textures/gui/overlay_icons.png");

    private final Minecraft mc;
    private final Level level;
    private int centerX, centerZ;
    private int minHeight, maxHeight;
    private int mapOffsetX = 0;
    private int mapOffsetZ = 0;
    private int blockSize = 2;
    private int renderDistance = INITIAL_RENDER_DISTANCE;
    private int dragStartX, dragStartY;
    private boolean isDragging = false;
    private Character character;
    private ExtraCharacter extraCharacter;
    private CompassFeature hoveredFeature = null;
    private CompassFeature selectedFeature;
    private boolean showFastTravelPopup = false;
    private int selectedOption = 0; // 0 for Yes, 1 for No

    private static final int MAP_BACKGROUND_COLOR = 0xFF000000;
    private static final int TERRAIN_LINE_COLOR = 0xFF404040;
    private static final int CROSS_HATCH_COLOR = 0xFF505050;
    private static final int MAP_BORDER_COLOR = 0xFF8B4513;
    private static final int TERRAIN_BASE_COLOR = 0xFF3E2723;

    public MapScreen() {
        super(Component.empty());
        this.mc = Minecraft.getInstance();
        this.level = mc.level;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        if (this.level == null || this.mc.player == null) {
            return;
        }

        guiGraphics.fill(0, 0, this.width, this.height, 0xFF000000);

        this.character = Services.PLATFORM.getCharacter(mc.player);
        this.extraCharacter = Services.PLATFORM.getExtraCharacter(mc.player);
        if (extraCharacter == null || extraCharacter.getMapData() == null) {
            return;
        }

        updateVisitedChunks();

        List<CompassFeature> mapFeatures = character.getCompassFeatures();
        List<Waypoint> waypoints = extraCharacter.getWaypoints();

        centerX = (int) this.mc.player.getX() + mapOffsetX;
        centerZ = (int) this.mc.player.getZ() + mapOffsetZ;

        calculateHeightRange();
        renderTopDownWorld(guiGraphics);
        renderPlayerMarker(guiGraphics);
        renderWaypoints(guiGraphics, waypoints);
        renderMapFeatures(guiGraphics, mapFeatures, mouseX, mouseY);

        String time = calculateSkyrimTime(minecraft.player.level());
        drawScaledString(guiGraphics, time,this.width - font.width(time) + 30, this.height - 24, 0xFFFFFFFF, 0.75F);

        guiGraphics.fillGradient(0, this.height * 3 / 4 + 20, this.width, this.height, 0xAA000000, 0xAA000000);
        guiGraphics.fillGradient(0, this.height * 3 / 4 + 22, this.width, this.height * 3 / 4 + 23, 0xFF5D5A51, 0xFF5D5A51);

        // Draw buttons for input controls
        drawGradientRect(guiGraphics, guiGraphics.pose(), 17, this.height - 29,
                21 + font.width("[↑↓←→/WASD]") + 4, this.height - 14,
                0xAA000000, 0xAA000000, 0xFF5D5A51);
        guiGraphics.drawString(font, "[↑↓←→/WASD]", 21, this.height - 25, 0x00FFFFFF);
        guiGraphics.drawString(font, "Move", 19 + font.width("[↑↓←→/WASD]") + 10, this.height - 25, 0x00FFFFFF);

        // Render tooltip for hovered map feature
        if (hoveredFeature != null) {
            renderFeatureTooltip(guiGraphics, hoveredFeature, mouseX, mouseY);
        }

        if (showFastTravelPopup) {
            renderFastTravelPopup(guiGraphics, mouseX, mouseY);
        }
    }

    private void calculateHeightRange() {
        minHeight = Integer.MAX_VALUE;
        maxHeight = Integer.MIN_VALUE;
        int playerChunkX = centerX >> 4;
        int playerChunkZ = centerZ >> 4;

        for (int chunkX = playerChunkX - renderDistance; chunkX <= playerChunkX + renderDistance; chunkX++) {
            for (int chunkZ = playerChunkZ - renderDistance; chunkZ <= playerChunkZ + renderDistance; chunkZ++) {
                LevelChunk chunk = level.getChunk(chunkX, chunkZ);
                for (int x = 0; x < CHUNK_SIZE; x++) {
                    for (int z = 0; z < CHUNK_SIZE; z++) {
                        int height = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, x, z);
                        minHeight = Math.min(minHeight, height);
                        maxHeight = Math.max(maxHeight, height);
                    }
                }
            }
        }
    }

    private void renderTopDownWorld(GuiGraphics guiGraphics) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        // Draw parchment colored background
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        int padding = 20;
        int backgroundWidth = mapSize + padding * 2;
        int backgroundHeight = mapSize + padding * 2;

        // Draw base map background
        renderMapBackground(guiGraphics,
                startX - padding,
                startY - padding,
                backgroundWidth,
                backgroundHeight);

        // Setup terrain rendering
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        int chunkRadius = renderDistance;
        int centerChunkX = (centerX + mapOffsetX) >> 4;
        int centerChunkZ = (centerZ + mapOffsetZ) >> 4;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(startX, startY, 0);

        Matrix4f matrix = poseStack.last().pose();

        // Get the visited chunks from player data
        Set<Long> visitedChunks = this.extraCharacter.getMapData().getVisitedChunks();

        // Render chunks
        for (int chunkX = centerChunkX - chunkRadius; chunkX <= centerChunkX + chunkRadius; chunkX++) {
            for (int chunkZ = centerChunkZ - chunkRadius; chunkZ <= centerChunkZ + renderDistance; chunkZ++) {
                ChunkPos pos = new ChunkPos(chunkX, chunkZ);

                // Check if the chunk has been visited
                if (visitedChunks.contains(pos.toLong())) {
                    // Render visited chunk with terrain
                    renderAntiqueChunkStyle(bufferBuilder, matrix, chunkX, chunkZ);
                } else {
                    // Render unexplored chunk
                    renderUnexploredChunk(bufferBuilder, matrix, chunkX, chunkZ);
                }
            }
        }

        tesselator.end();

        // Draw decorative border
        renderMapBorder(guiGraphics, startX - padding, startY - padding,
                mapSize + padding * 2, mapSize + padding * 2);

        poseStack.popPose();
    }

    private void renderUnexploredChunk(BufferBuilder bufferBuilder, Matrix4f matrix, int chunkX, int chunkZ) {
        int startX = (chunkX - ((centerX + mapOffsetX) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;
        int startZ = (chunkZ - ((centerZ + mapOffsetZ) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;
        int chunkPixelSize = CHUNK_SIZE * blockSize;

        // Use the same brown color as the border
//        float r = 0.545f; // 139/255
//        float g = 0.271f; // 69/255
//        float b = 0.075f; // 19/255
//        float a = 1.0f;

        float r = 0.37f; // ~94
        float g = 0.35f; // ~90
        float b = 0.32f; // ~81
        float a = 1.0f;

        // Simple solid colored quad
        bufferBuilder.vertex(matrix, startX, startZ + chunkPixelSize, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, startX + chunkPixelSize, startZ + chunkPixelSize, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, startX + chunkPixelSize, startZ, 0).color(r, g, b, a).endVertex();
        bufferBuilder.vertex(matrix, startX, startZ, 0).color(r, g, b, a).endVertex();
    }

    private void updateVisitedChunks() {
        int playerChunkX = (int)mc.player.getX() >> 4;
        int playerChunkZ = (int)mc.player.getZ() >> 4;
        int loadDistance = mc.options.renderDistance().get();

        for (int x = playerChunkX - loadDistance; x <= playerChunkX + loadDistance; x++) {
            for (int z = playerChunkZ - loadDistance; z <= playerChunkZ + loadDistance; z++) {
                if (level.hasChunk(x, z)) {
                    extraCharacter.getMapData().addVisitedChunk(new ChunkPos(x, z));
                }
            }
        }
    }

    private void renderAntiqueChunkStyle(BufferBuilder bufferBuilder, Matrix4f matrix, int chunkX, int chunkZ) {
        LevelChunk chunk = level.getChunk(chunkX, chunkZ);
        if (chunk == null) return;

        int startX = (chunkX - ((centerX + mapOffsetX) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;
        int startZ = (chunkZ - ((centerZ + mapOffsetZ) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;

        // Cell size matches the grid pattern
        int cellSize = blockSize;

        // First pass: Gather water block information
        boolean[][] isWater = new boolean[CHUNK_SIZE][CHUNK_SIZE];
        for (int blockX = 0; blockX < CHUNK_SIZE; blockX++) {
            for (int blockZ = 0; blockZ < CHUNK_SIZE; blockZ++) {
                int worldX = chunkX * CHUNK_SIZE + blockX;
                int worldZ = chunkZ * CHUNK_SIZE + blockZ;
                int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, blockX, blockZ);
                BlockPos pos = new BlockPos(worldX, y, worldZ);
                BlockState state = chunk.getBlockState(pos);

                // Check if the block is water (you might need to adjust this check based on your mod's water blocks)
                isWater[blockX][blockZ] = state.getMapColor(level, pos).col == 0x4444FF ||
                        state.getMapColor(level, pos).col == 0x3F76E4;
            }
        }

        // Second pass: Render terrain and water with borders
        for (int blockX = 0; blockX < CHUNK_SIZE; blockX++) {
            for (int blockZ = 0; blockZ < CHUNK_SIZE; blockZ++) {
                int worldX = chunkX * CHUNK_SIZE + blockX;
                int worldZ = chunkZ * CHUNK_SIZE + blockZ;
                int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, blockX, blockZ);
                BlockPos pos = new BlockPos(worldX, y, worldZ);
                BlockState state = chunk.getBlockState(pos);

                int baseColor = state.getMapColor(level, pos).col;
                int mapColor = convertToMapColor(baseColor, y);

                float r = ((mapColor >> 16) & 0xFF) / 255.0F;
                float g = ((mapColor >> 8) & 0xFF) / 255.0F;
                float b = (mapColor & 0xFF) / 255.0F;
                float a = 1.0F;

                int x = startX + blockX * cellSize;
                int z = startZ + blockZ * cellSize;

                // Draw base terrain cell
                bufferBuilder.vertex(matrix, x, z + cellSize, 0).color(r, g, b, a).endVertex();
                bufferBuilder.vertex(matrix, x + cellSize, z + cellSize, 0).color(r, g, b, a).endVertex();
                bufferBuilder.vertex(matrix, x + cellSize, z, 0).color(r, g, b, a).endVertex();
                bufferBuilder.vertex(matrix, x, z, 0).color(r, g, b, a).endVertex();

                // If it's water, check for borders
                if (isWater[blockX][blockZ]) {
                    // Darken the colors for water borders
                    float borderR = r * 0.7f;
                    float borderG = g * 0.7f;
                    float borderB = b * 0.7f;

                    int borderWidth = Math.max(1, cellSize / 4);

                    // Check adjacent blocks and draw borders where water meets land
                    // Top border
                    if (blockZ == 0 || !isWater[blockX][blockZ - 1]) {
                        bufferBuilder.vertex(matrix, x, z, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + cellSize, z, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + cellSize, z + borderWidth, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x, z + borderWidth, 0).color(borderR, borderG, borderB, a).endVertex();
                    }

                    // Bottom border
                    if (blockZ == CHUNK_SIZE - 1 || !isWater[blockX][blockZ + 1]) {
                        bufferBuilder.vertex(matrix, x, z + cellSize - borderWidth, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + cellSize, z + cellSize - borderWidth, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + cellSize, z + cellSize, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x, z + cellSize, 0).color(borderR, borderG, borderB, a).endVertex();
                    }

                    // Left border
                    if (blockX == 0 || !isWater[blockX - 1][blockZ]) {
                        bufferBuilder.vertex(matrix, x, z, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + borderWidth, z, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + borderWidth, z + cellSize, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x, z + cellSize, 0).color(borderR, borderG, borderB, a).endVertex();
                    }

                    // Right border
                    if (blockX == CHUNK_SIZE - 1 || !isWater[blockX + 1][blockZ]) {
                        bufferBuilder.vertex(matrix, x + cellSize - borderWidth, z, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + cellSize, z, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + cellSize, z + cellSize, 0).color(borderR, borderG, borderB, a).endVertex();
                        bufferBuilder.vertex(matrix, x + cellSize - borderWidth, z + cellSize, 0).color(borderR, borderG, borderB, a).endVertex();
                    }
                }
            }
        }
    }

    private int convertToMapColor(int originalColor, int height) {
        // Extract RGB components
        int r = (originalColor >> 16) & 0xFF;
        int g = (originalColor >> 8) & 0xFF;
        int b = originalColor & 0xFF;

        // Calculate base brightness and saturation
        float brightness = (r * 0.299f + g * 0.587f + b * 0.114f) / 255f;
        float saturation = Math.max(Math.max(r, g), b) - Math.min(Math.min(r, g), b);
        float heightFactor = (float)(height - minHeight) / (maxHeight - minHeight);

        // Define color ranges for different terrain types
        boolean isSnow = brightness > 0.8f && saturation < 30;
        boolean isWater = b > Math.max(r, g) && saturation > 20;
        boolean isDesert = r > g && g > b && brightness > 0.6f;
        boolean isGrass = g > Math.max(r, b);

        // Base parchment color (lighter)
        int parchmentR = 0xea;
        int parchmentG = 0xd8;
        int parchmentB = 0xc1;

        // Darker shade for contrast
        int darkR = 0x8b;
        int darkG = 0x6b;
        int darkB = 0x4c;

        // Adjust colors based on terrain type
        if (isSnow) {
            // More yellow-tinted for snow to avoid pure white
            parchmentR = 0xf5;
            parchmentG = 0xeb;
            parchmentB = 0xdc;
            darkR = 0xd8;
            darkG = 0xd0;
            darkB = 0xc0;
        } else if (isWater) {
            // Deeper blue-tinted browns for water
            parchmentR = 0xc8;
            parchmentG = 0xc0;
            parchmentB = 0xb8;
            darkR = 0x70;
            darkG = 0x60;
            darkB = 0x58;
        } else if (isDesert) {
            // Warmer browns for desert
            parchmentR = 0xf0;
            parchmentG = 0xe0;
            parchmentB = 0xc8;
            darkR = 0xa0;
            darkG = 0x80;
            darkB = 0x60;
        } else if (isGrass) {
            // Olive-tinted browns for vegetation
            parchmentR = 0xe0;
            parchmentG = 0xd8;
            parchmentB = 0xb8;
            darkR = 0x80;
            darkG = 0x85;
            darkB = 0x60;
        }

        // Calculate blending factor based on height and brightness
        float factor = brightness * 0.6f + heightFactor * 0.4f;
        factor = 1.0f - factor; // Invert to make higher/brighter areas darker

        // Add slight variation based on position to avoid flat areas
        float variation = ((height * 31) & 0x7) / 128.0f;
        factor = Math.max(0.0f, Math.min(1.0f, factor + variation));

        int finalR = interpolate(parchmentR, darkR, factor);
        int finalG = interpolate(parchmentG, darkG, factor);
        int finalB = interpolate(parchmentB, darkB, factor);

        return (0xFF << 24) | (finalR << 16) | (finalG << 8) | finalB;
    }

    private int interpolate(int a, int b, float factor) {
        return (int)(a + (b - a) * factor);
    }

    private void renderMapBorder(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        // Draw ornate border
        int borderWidth = 4;
        guiGraphics.fill(x, y, x + width, y + borderWidth, MAP_BORDER_COLOR); // Top
        guiGraphics.fill(x, y + height - borderWidth, x + width, y + height, MAP_BORDER_COLOR); // Bottom
        guiGraphics.fill(x, y, x + borderWidth, y + height, MAP_BORDER_COLOR); // Left
        guiGraphics.fill(x + width - borderWidth, y, x + width, y + height, MAP_BORDER_COLOR); // Right

        // Add corner decorations
        int cornerSize = 12;
        renderCornerDecoration(guiGraphics, x, y, cornerSize, 0); // Top left
        renderCornerDecoration(guiGraphics, x + width - cornerSize, y, cornerSize, 1); // Top right
        renderCornerDecoration(guiGraphics, x, y + height - cornerSize, cornerSize, 2); // Bottom left
        renderCornerDecoration(guiGraphics, x + width - cornerSize, y + height - cornerSize, cornerSize, 3); // Bottom right
    }

    private void renderMapBackground(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        // Draw parchment colored background
        guiGraphics.fill(x, y, x + width, y + height, 0xFFEAD8C1); // Base parchment color

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        Matrix4f matrix = poseStack.last().pose();

        // Fixed cell size
        int cellSize = 8;
        float lineAlpha = 0.2f; // Subtle grid lines

        bufferBuilder.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);

        // Dark brown grid lines
        float r = 0.55f; // ~139
        float g = 0.42f; // ~107
        float b = 0.30f; // ~76

        // Draw horizontal lines
        for (int j = 0; j <= height; j += cellSize) {
            bufferBuilder.vertex(matrix, x, y + j, 0)
                    .color(r, g, b, lineAlpha)
                    .endVertex();
            bufferBuilder.vertex(matrix, x + width, y + j, 0)
                    .color(r, g, b, lineAlpha)
                    .endVertex();
        }

        // Draw vertical lines
        for (int i = 0; i <= width; i += cellSize) {
            bufferBuilder.vertex(matrix, x + i, y, 0)
                    .color(r, g, b, lineAlpha)
                    .endVertex();
            bufferBuilder.vertex(matrix, x + i, y + height, 0)
                    .color(r, g, b, lineAlpha)
                    .endVertex();
        }

        BufferUploader.drawWithShader(bufferBuilder.end());
        poseStack.popPose();
    }

    private void renderCornerDecoration(GuiGraphics guiGraphics, int x, int y, int size, int corner) {
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        Matrix4f matrix = poseStack.last().pose();

        // Set corner decoration color
        float r = 0.37f; // ~94
        float g = 0.35f; // ~90
        float b = 0.32f; // ~81
        float a = 1.0f;

        bufferBuilder.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);

        // Draw corner design based on position
        switch(corner) {
            case 0: // Top left
                drawCornerLines(bufferBuilder, matrix, x, y, size, 0, 0, r, g, b, a);
                break;
            case 1: // Top right
                drawCornerLines(bufferBuilder, matrix, x + size, y, size, 1, 0, r, g, b, a);
                break;
            case 2: // Bottom left
                drawCornerLines(bufferBuilder, matrix, x, y + size, size, 0, 1, r, g, b, a);
                break;
            case 3: // Bottom right
                drawCornerLines(bufferBuilder, matrix, x + size, y + size, size, 1, 1, r, g, b, a);
                break;
        }

        BufferUploader.drawWithShader(bufferBuilder.end());
        poseStack.popPose();
    }

    private void drawCornerLines(BufferBuilder buffer, Matrix4f matrix, int x, int y, int size, int flipX, int flipY, float r, float g, float b, float a) {
        int dirX = flipX == 0 ? 1 : -1;
        int dirY = flipY == 0 ? 1 : -1;

        // Draw main corner accent
        for(int i = 0; i < size; i += 2) {
            float startX = x;
            float startY = y + (dirY * i);
            float endX = x + (dirX * (size - i));
            float endY = y;

            buffer.vertex(matrix, startX, startY, 0).color(r, g, b, a).endVertex();
            buffer.vertex(matrix, endX, endY, 0).color(r, g, b, a).endVertex();
        }

        // Draw secondary decorative lines
        int smallSize = size / 2;
        for(int i = 0; i < smallSize; i += 2) {
            float startX = x + (dirX * i);
            float startY = y + (dirY * (smallSize - i/2));
            float endX = x + (dirX * smallSize);
            float endY = y + (dirY * i/2);

            buffer.vertex(matrix, startX, startY, 0).color(r, g, b, a * 0.7f).endVertex();
            buffer.vertex(matrix, endX, endY, 0).color(r, g, b, a * 0.7f).endVertex();
        }
    }

    private int convertToAntiqueColor(int originalColor, int height) {
        // Extract RGB components
        int r = (originalColor >> 16) & 0xFF;
        int g = (originalColor >> 8) & 0xFF;
        int b = originalColor & 0xFF;

        // Convert to sepia-like tones
        float heightFactor = (float)(height - minHeight) / (maxHeight - minHeight);

        // Adjust colors to be more brown/sepia toned
        int newR = (int)(r * 0.8f + g * 0.2f);
        int newG = (int)(r * 0.6f + g * 0.4f);
        int newB = (int)(r * 0.4f + g * 0.2f + b * 0.1f);

        // Darken based on height
        float shadingFactor = 1.0f - (heightFactor * 0.4f);
        newR = (int)(newR * shadingFactor);
        newG = (int)(newG * shadingFactor);
        newB = (int)(newB * shadingFactor);

        // Ensure values are in valid range
        newR = Math.min(255, Math.max(0, newR));
        newG = Math.min(255, Math.max(0, newG));
        newB = Math.min(255, Math.max(0, newB));

        return (0xFF << 24) | (newR << 16) | (newG << 8) | newB;
    }

    private void renderPlayerMarker(GuiGraphics guiGraphics) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        // Calculate player's position on the map
        int playerX = (int) ((this.mc.player.getX() - (centerX + mapOffsetX)) * blockSize) + mapSize / 2;
        int playerZ = (int) ((this.mc.player.getZ() - (centerZ + mapOffsetZ)) * blockSize) + mapSize / 2;

        // Calculate marker size based on zoom level
        float markerSize = BASE_MARKER_SIZE * (blockSize / 2.0f);

        // Only render if the player is within the map bounds
        if (playerX >= 0 && playerX < mapSize && playerZ >= 0 && playerZ < mapSize) {
            PoseStack poseStack = guiGraphics.pose();
            poseStack.pushPose();
            poseStack.translate(startX + playerX, startY + playerZ, 0);
            poseStack.scale(markerSize/8, markerSize/8, 1);
            RenderUtil.bind(minecraft.player.getSkinTextureLocation());
            RenderUtil.blitWithBlend(guiGraphics.pose(), -4, -4, 8, 8, 8, 8, 64, 64, 1, 1);
            poseStack.popPose();
        }
    }

    private void renderWaypoints(GuiGraphics guiGraphics, List<Waypoint> waypoints) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        for (Waypoint waypoint : waypoints) {
            BlockPos pos = waypoint.getBlockPos();
            int waypointX = (int) ((pos.getX() - (centerX + mapOffsetX)) * blockSize) + mapSize / 2;
            int waypointZ = (int) ((pos.getZ() - (centerZ + mapOffsetZ)) * blockSize) + mapSize / 2;

            if (waypointX >= 0 && waypointX < mapSize && waypointZ >= 0 && waypointZ < mapSize) {
                guiGraphics.pose().pushPose();
                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(guiGraphics.pose(), startX + waypointX, startY + waypointZ, 0, 158, 8, 16, 256, 256, 1, 1.0f);
                guiGraphics.pose().popPose();
            }
        }
    }

    private void renderMapFeatures(GuiGraphics guiGraphics, List<CompassFeature> mapFeatures, int mouseX, int mouseY) {
        int screenWidth = this.width;
        int screenHeight = this.height;
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int startX = (screenWidth - mapSize) / 2;
        int startY = (screenHeight - mapSize) / 2;

        hoveredFeature = null;

        for (CompassFeature feature : mapFeatures) {
            BlockPos pos = feature.getBlockPos();
            int featureX = (int) ((pos.getX() - (centerX + mapOffsetX)) * blockSize) + mapSize / 2;
            int featureZ = (int) ((pos.getZ() - (centerZ + mapOffsetZ)) * blockSize) + mapSize / 2;

            if (featureX >= 0 && featureX < mapSize && featureZ >= 0 && featureZ < mapSize) {
                int renderX = startX + featureX - ICON_WIDTH / 2;
                int renderZ = startY + featureZ - ICON_HEIGHT / 2;
                int u = feature.getIconUV().getKey(), v = feature.getIconUV().getValue();

                guiGraphics.pose().pushPose();

                // Draw gradient rect behind the icon
                drawGradientRect(guiGraphics, guiGraphics.pose(), renderX - 2, renderZ - 2,
                        renderX + ICON_WIDTH + 2, renderZ + ICON_HEIGHT + 2,
                        0xAA000000, 0xAA000000, 0xFF5D5A51);

                RenderUtil.bind(OVERLAY_ICONS);
                RenderUtil.blitWithBlend(guiGraphics.pose(), renderX, renderZ, u, v, ICON_WIDTH, ICON_HEIGHT, 256, 256, 1, 1.0f);
                guiGraphics.pose().popPose();

                // Check if the mouse is hovering over this feature
                if (mouseX >= renderX && mouseX < renderX + ICON_WIDTH &&
                        mouseY >= renderZ && mouseY < renderZ + ICON_HEIGHT) {
                    hoveredFeature = feature;
                }
            }
        }
    }

    private void renderCube(GuiGraphics guiGraphics, int x, int y, int color) {
        int size = blockSize;
        guiGraphics.fill(x, y, x + size, y + size, color);
    }

    private void renderFeatureTooltip(GuiGraphics guiGraphics, CompassFeature feature, int mouseX, int mouseY) {
        if(feature.getIconUV() == null)
            return;

        int tooltipWidth = 10 + font.width(feature.getFeatureName());
        int tooltipHeight = 16;
        int padding = 4;

        // Position tooltip above and to the right of the cursor
        int tooltipX = mouseX + 12;
        int tooltipY = mouseY - tooltipHeight - 12;

        // Ensure tooltip stays within screen bounds
        tooltipX = Math.min(tooltipX, this.width - tooltipWidth - padding);
        tooltipY = Math.max(tooltipY, padding);

        guiGraphics.pose().pushPose();
        RenderSystem.enableDepthTest();
        // Render tooltip background
        drawGradientRect(guiGraphics, guiGraphics.pose(), tooltipX, tooltipY, tooltipX + tooltipWidth, tooltipY + tooltipHeight, 0xAA000000, 0xAA000000, 0xFF5D5A51);
        // Render feature name
        guiGraphics.drawString(font, feature.getFeatureName(), tooltipX + padding, tooltipY + padding, 0xFFFFFFFF);
        guiGraphics.pose().popPose();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging) {
            // Scale movement to match key movement (16 blocks per key press)
            double moveScale = 16.0 / blockSize;
            mapOffsetX -= (int) (dragX * moveScale);
            mapOffsetZ -= (int) (dragY * moveScale);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (showFastTravelPopup) {
            int popupWidth = 200;
            int popupHeight = 80;
            int popupX = (this.width - popupWidth) / 2;
            int popupY = (this.height - popupHeight) / 2;
            int optionY = popupY + popupHeight - 30;

            if (mouseY >= optionY - 10 && mouseY <= optionY + 10) {
                int yesX = popupX + popupWidth / 4;
                int noX = popupX + 3 * popupWidth / 4;

                if (mouseX >= yesX - 20 && mouseX <= yesX + 20) {
                    handleFastTravelChoice(true);
                    return true;
                } else if (mouseX >= noX - 20 && mouseX <= noX + 20) {
                    handleFastTravelChoice(false);
                    return true;
                }
            }
        } else {
            if (hoveredFeature != null) {
                handleFeatureClick(hoveredFeature);
                return true;
            }
            isDragging = true;
            dragStartX = (int) mouseX;
            dragStartY = (int) mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        // Store mouse position relative to map center
        int mapSize = renderDistance * 2 * CHUNK_SIZE * blockSize;
        int mapCenterX = this.width / 2;
        int mapCenterZ = this.height / 2;

        double relativeX = mouseX - mapCenterX;
        double relativeZ = mouseY - mapCenterZ;

        // Store the world coordinates under the mouse
        double worldX = centerX + mapOffsetX + (relativeX / blockSize);
        double worldZ = centerZ + mapOffsetZ + (relativeZ / blockSize);

        // Adjust zoom level with smoother transitions
        int oldBlockSize = blockSize;
        boolean zoomChanged = false;

        if (delta > 0 && blockSize < MAX_BLOCK_SIZE) {
            blockSize++;
            renderDistance = Math.max(4, (int)(renderDistance * ((float)oldBlockSize / blockSize)));
            zoomChanged = true;
        } else if (delta < 0 && blockSize > MIN_BLOCK_SIZE) {
            blockSize--;
            renderDistance = Math.min(32, (int)(renderDistance * ((float)oldBlockSize / blockSize)));
            zoomChanged = true;
        }

        if (zoomChanged) {
            // Calculate new offsets to keep the mouse position over the same world coordinates
            // Use floating-point arithmetic for more precise positioning
            double newRelativeX = relativeX * (oldBlockSize / (double)blockSize);
            double newRelativeZ = relativeZ * (oldBlockSize / (double)blockSize);

            mapOffsetX = (int)(worldX - centerX - (newRelativeX / blockSize));
            mapOffsetZ = (int)(worldZ - centerZ - (newRelativeZ / blockSize));

            // Ensure render distance stays within reasonable bounds
            renderDistance = Math.max(4, Math.min(32, renderDistance));
        }

        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (showFastTravelPopup) {
            if (KeysRegistry.SKYRIM_MENU_EAST.matches(keyCode, scanCode) || KeysRegistry.SKYRIM_MENU_WEST.matches(keyCode, scanCode)) {
                selectedOption = 1 - selectedOption;
                return true;
            } else if (KeysRegistry.SKYRIM_MENU_ENTER.matches(keyCode, scanCode)) {
                handleFastTravelChoice(selectedOption == 0);
                return true;
            }
        } else {
            int moveAmount = 16; // Move by one chunk
            boolean handled = true;

            // Check both arrow keys and WASD
            if (KeysRegistry.SKYRIM_MENU_NORTH.matches(keyCode, scanCode) || keyCode == 87) { // W
                mapOffsetZ -= moveAmount;
            } else if (KeysRegistry.SKYRIM_MENU_SOUTH.matches(keyCode, scanCode) || keyCode == 83) { // S
                mapOffsetZ += moveAmount;
            } else if (KeysRegistry.SKYRIM_MENU_WEST.matches(keyCode, scanCode) || keyCode == 68) { // D
                mapOffsetX += moveAmount;
            } else if (KeysRegistry.SKYRIM_MENU_EAST.matches(keyCode, scanCode) || keyCode == 65) { // A
                mapOffsetX -= moveAmount;
            } else {
                handled = false;
            }

            if (handled) {
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void handleFeatureClick(CompassFeature feature) {
        selectedFeature = feature;
        showFastTravelPopup = true;
        selectedOption = 0; // Default to "Yes"
    }

    private void handleFastTravelChoice(boolean choice) {
        if (choice) {
//            if(character.getCurrentTarget() != -1) // Does player current have a target
//                return;
            mc.setScreen(new SkyrimLoadingScreen());
            FastTravel fastTravelPacket = new FastTravel(selectedFeature.getBlockPos());
            Dispatcher.sendToServer(fastTravelPacket);
            System.out.println("Fast traveling to " + selectedFeature.getFeatureName());
            // Implement fast travel logic here
        } else {
            System.out.println("Fast travel cancelled");
        }
        showFastTravelPopup = false;
    }

    private void renderFastTravelPopup(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        int popupWidth = 200;
        int popupHeight = 80;
        int popupX = (this.width - popupWidth) / 2;
        int popupY = (this.height - popupHeight) / 2;

        // Draw popup background
        drawGradientRect(guiGraphics, guiGraphics.pose(), popupX, popupY, popupX + popupWidth, popupY + popupHeight, 0xAA000000, 0xAA000000, 0xFF5D5A51);

        // Draw text
        String message = "Do you wish to fast travel to " + selectedFeature.getFeatureName() + "?";
        drawScaledCenteredString(guiGraphics, message, this.width / 2, popupY + 20, 0xFFFFFFFF, 1.0f);

        // Draw options
        String yesText = selectedOption == 0 ? "< Yes >" : "Yes";
        String noText = selectedOption == 1 ? "< No >" : "No";

        int yesX = popupX + popupWidth / 4;
        int noX = popupX + 3 * popupWidth / 4;
        int optionY = popupY + popupHeight - 30;

        drawScaledCenteredString(guiGraphics, yesText, yesX, optionY, 0xFFFFFFFF, 1.0f);
        drawScaledCenteredString(guiGraphics, noText, noX, optionY, 0xFFFFFFFF, 1.0f);

        // Check for mouse hover
        if (mouseY >= optionY - 10 && mouseY <= optionY + 10) {
            if (mouseX >= yesX - 20 && mouseX <= yesX + 20) {
                selectedOption = 0;
            } else if (mouseX >= noX - 20 && mouseX <= noX + 20) {
                selectedOption = 1;
            }
        }
    }

    private void renderChunkTopLayer(BufferBuilder bufferBuilder, Matrix4f matrix, int chunkX, int chunkZ) {
        LevelChunk chunk = level.getChunk(chunkX, chunkZ);
        if (chunk == null) {
            return;
        }

        int startX = (chunkX - ((centerX + mapOffsetX) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;
        int startZ = (chunkZ - ((centerZ + mapOffsetZ) >> 4) + renderDistance) * CHUNK_SIZE * blockSize;

        for (int blockX = 0; blockX < CHUNK_SIZE; blockX++) {
            for (int blockZ = 0; blockZ < CHUNK_SIZE; blockZ++) {
                int worldX = chunkX * CHUNK_SIZE + blockX;
                int worldZ = chunkZ * CHUNK_SIZE + blockZ;
                int y = chunk.getHeight(Heightmap.Types.WORLD_SURFACE, blockX, blockZ);
                BlockPos pos = new BlockPos(worldX, y, worldZ);
                BlockState state = chunk.getBlockState(pos);

                int color = getBlockColor(state, pos, y);
                float r = ((color >> 16) & 0xFF) / 255.0F;
                float g = ((color >> 8) & 0xFF) / 255.0F;
                float b = (color & 0xFF) / 255.0F;
                float a = ((color >> 24) & 0xFF) / 255.0F;

                int x = startX + blockX * blockSize;
                int z = startZ + blockZ * blockSize;

                // Render block as quad
                bufferBuilder.vertex(matrix, x, z + blockSize, 0).color(r, g, b, a).endVertex();
                bufferBuilder.vertex(matrix, x + blockSize, z + blockSize, 0).color(r, g, b, a).endVertex();
                bufferBuilder.vertex(matrix, x + blockSize, z, 0).color(r, g, b, a).endVertex();
                bufferBuilder.vertex(matrix, x, z, 0).color(r, g, b, a).endVertex();
            }
        }
    }

    private int getBlockColor(BlockState state, BlockPos pos, int height) {
        int baseColor = state.getMapColor(level, pos).col;
        float heightFactor = (float) (height - minHeight) / (maxHeight - minHeight);
        return applyShading(baseColor, heightFactor);
    }

    private int applyShading(int color, float factor) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        // Adjust the shading formula to make higher points slightly darker
        float shadingFactor = 1.0f - (factor * 0.3f);  // 0.3f controls the intensity of the shading effect

        r = (int) (r * shadingFactor);
        g = (int) (g * shadingFactor);
        b = (int) (b * shadingFactor);

        return (r << 16) | (g << 8) | b | 0xFF000000;
    }

    private void renderChunkBorder(GuiGraphics guiGraphics, int x, int y, int width, int height) {
        // Render a subtle border around each chunk
        int borderColor = 0x20FFFFFF; // Very subtle white border
        guiGraphics.fill(x, y, x + width, y + 1, borderColor); // Top
        guiGraphics.fill(x, y + height - 1, x + width, y + height, borderColor); // Bottom
        guiGraphics.fill(x, y, x + 1, y + height, borderColor); // Left
        guiGraphics.fill(x + width - 1, y, x + width, y + height, borderColor); // Right
    }

    private void renderChunkGridLines(GuiGraphics guiGraphics, int mapSize) {
        int lineColor = 0x80FFFFFF; // Semi-transparent white
        for (int i = 0; i <= renderDistance * 2; i++) {
            int linePos = i * CHUNK_SIZE * blockSize;
            // Vertical lines
            guiGraphics.vLine(linePos, 0, mapSize, lineColor);
            // Horizontal lines
            guiGraphics.hLine(0, mapSize, linePos, lineColor);
        }
    }

    private void drawGradientRect(GuiGraphics graphics, PoseStack matrixStack, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int borderColor) {
        matrixStack.pushPose();
        // Draw background
        graphics.fillGradient(startX, startY, endX, endY, colorStart, colorEnd);
        // Draw borders
        graphics.fill(startX, startY, endX, startY+1, borderColor); // top
        graphics.fill(startX, endY-1, endX, endY, borderColor); // bottom
        graphics.fill(startX, startY+1, startX+1, endY-1, borderColor); // left
        graphics.fill(endX-1, startY+1, endX, endY-1, borderColor); // right
        matrixStack.popPose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void drawScaledString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        graphics.drawString(font, str, (int)(x/scale), (int)(y/scale), color);
        graphics.pose().popPose();
    }

    public void drawScaledCenteredString(GuiGraphics graphics, String str, int x, int y, int color, float scale) {
        graphics.pose().pushPose();
        graphics.pose().scale(scale, scale, scale);
        float w = this.font.width(str) * scale;
        x = (int)((x - w / 2) / scale);
        graphics.drawString(font, str, x, (int)(y/scale), color);
        graphics.pose().popPose();
    }

    private String calculateSkyrimTime(Level world) {
        StringBuilder builder = new StringBuilder();
        int dayNum = (int)(this.minecraft.level.getDayTime() / 24000L);
        int monthNum = (int)(this.minecraft.level.getDayTime() / (24000L * 31)) + 1;
        String day = getDayName((dayNum%7) + 1);
        String month = getMonthName(monthNum);
        String year = "4E 201";

        builder.append(day);
        builder.append(", ");
        builder.append(ordinal(dayNum));
        builder.append(" day of ");
        builder.append(month);
        builder.append(", ");
        builder.append(year);

        return builder.toString();
    }

    private String ordinal(int num) {
        String[] suffix = {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th"};
        int m = num % 100;
        return String.valueOf(num) + suffix[(m > 3 && m < 21) ? 0 : (m % 10)];
    }

    private String getDayName(int day) {
        String name = "";
        switch(day) {
            case 1:
                name = "Morndas";
                break;
            case 2:
                name = "Tirdas";
                break;
            case 3:
                name = "Middas";
                break;
            case 4:
                name = "Turdas";
                break;
            case 5:
                name = "Fredas";
                break;
            case 6:
                name = "Loredas";
                break;
            case 7:
                name = "Sundas";
                break;
            default:
                name = "Invalid Day!";
                break;
        }
        return name;
    }

    private String getMonthName(int month) {
        String name = "";
        switch(month) {
            case 1:
                name = "Morning Star";
                break;
            case 2:
                name = "Sun's Dawn";
                break;
            case 3:
                name = "First Seed";
                break;
            case 4:
                name = "Rain's Hand";
                break;
            case 5:
                name = "Second Seed";
                break;
            case 6:
                name = "Midyear";
                break;
            case 7:
                name = "Sun's Height";
                break;
            case 8:
                name = "Last Seed";
                break;
            case 9:
                name = "Heart Fire";
                break;
            case 10:
                name = "Frostfall";
                break;
            case 11:
                name = "Sun's Dusk";
                break;
            case 12:
                name = "Evening Star";
                break;
            default:
                name = "Invalid Month!";
                break;
        }
        return name;
    }
}