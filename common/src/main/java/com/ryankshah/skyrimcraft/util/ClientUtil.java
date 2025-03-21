package com.ryankshah.skyrimcraft.util;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.ryankshah.skyrimcraft.item.LockItem;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ClientUtil
{
    public static ClientLevel getClientLevel() {
        return Minecraft.getInstance().level;
    }

    public static AbstractClientPlayer getClientPlayer() {
        return Minecraft.getInstance().player;
    }

    public static Minecraft getMinecraft() {
        return Minecraft.getInstance();
    }

    public static Set<Vec3> circle(Vec3 origin, Vec3 normal, double radius, double amount){
        normal.normalize();
        //First tangent vector is derived from positive y, giving "up" from the player's view.
        //Subtracting off the normal vector removes the part of the vector that is not in the plane
        //It's unlikely the player will be looking straight up, but if they are this will fail
        ///since it will give the zero vector. You should add some additional logic to detect
        //if the player is "looking up" and use x or z instead.
        Vec3 tangent1 = new Vec3(0, 1, 0).subtract(normal).normalize();
        //Second tangent is simply orthogonal to both normal and tangent vectors.
        //This is obtained by a cross product. There is no need to normalize again
        //since the cross product of orthonormal vectors produces a unit vector
        Vec3 tangent2 = normal.cross(tangent1);

        Set<Vec3> circlePoints = new HashSet<>();
        for(double angle = 0.0D; angle < 2 * Math.PI; angle += amount / 180D * (2 * Math.PI)) {
            //Iterate over angles and use the tangent vectors as your coordinates
            //Think of these vectors as hands on a clock rotating around to produce the circle.
            Vec3 x = tangent1.scale(Math.cos(angle) * radius); //Think of this as x, but projected in the plane
            Vec3 y = tangent2.scale(Math.sin(angle) * radius); //Think of this as y, but projected in the plane
            //Add these contributions to the origin, making a circle around the point defined by origin
            circlePoints.add(origin.add(x).add(y));
        }
        return circlePoints;
    }

    public static Set<Vec3> sphere(int samples){
        Set<Vec3> spherePoints = new HashSet<>();
        float phi = Mth.PI * (Mth.sqrt(5f) - 1f); // golden angle in rads

        for (int i = 0; i < samples; i++)
        {
            float y = 1 - ((float) i / (samples - 1)) * 2;
            float radius = Mth.sqrt(1 - y * y);
            float theta = phi * i;
            float x = Mth.cos(theta) * radius;
            float z = Mth.sin(theta) * radius;
            spherePoints.add(new Vec3(x, y, z));
        }
        return spherePoints;
    }

    public static int[] getRGBAArrayFromHexColor(int color)  {
        int[] ints = new int[4];
        ints[0] = (color >> 24 & 255);
        ints[1] = (color >> 16 & 255);
        ints[2] = (color >>  8 & 255);
        ints[3] = (color       & 255);
        return ints;
    }

    public static boolean canPlayerBeSeen() {
        Player clientPlayer = getClientPlayer();
        double maxSeenBound = 20D;
        List<LivingEntity> entityList = clientPlayer.level().getEntities(clientPlayer, new AABB(clientPlayer.getX() - (double)maxSeenBound, clientPlayer.getY() - (double)maxSeenBound, clientPlayer.getZ() - (double)maxSeenBound, clientPlayer.getX() + (double)maxSeenBound, clientPlayer.getY() + (double)maxSeenBound, clientPlayer.getZ() + (double)maxSeenBound)).stream().filter(entity -> entity instanceof LivingEntity).map(LivingEntity.class::cast).collect(Collectors.toList());

        return entityList.stream().anyMatch(e -> canEntitySee(e, clientPlayer));
    }

    public static boolean canEntitySee(LivingEntity viewer, LivingEntity beingViewed) {
        double dx = beingViewed.getX() - viewer.getX();
        double dz;
        for (dz = beingViewed.getX() - viewer.getZ(); dx * dx + dz * dz < 1.0E-4D; dz = (Math.random() - Math.random()) * 0.01D) {
            dx = (Math.random() - Math.random()) * 0.01D;
        }
        while (viewer.getYRot() > 360) {
            viewer.setYRot(viewer.getYRot() - 360);
        }
        while (viewer.getYRot() < -360) {
            viewer.setYRot(viewer.getYRot() + 360);
        }
        float yaw = (float) (Math.atan2(dz, dx) * 180.0D / Math.PI) - viewer.getYRot();
        yaw = yaw - 90;
        while (yaw < -180) {
            yaw += 360;
        }
        while (yaw >= 180) {
            yaw -= 360;
        }

        return yaw < 60 && yaw > -60 && BehaviorUtils.canSee(viewer, beingViewed);
    }

    public static BlockPos blockPos(double pX, double pY, double pZ) {
        return new BlockPos(Mth.floor(pX), Mth.floor(pY), Mth.floor(pZ));
    }
    public static BlockPos blockPos(Vec3 pVec3) {
        return blockPos(pVec3.x, pVec3.y, pVec3.z);
    }


    public static String wrap( String src, int lineLength, String newLineStr, boolean wrapLongWords,
                               String longWordBreak, String longWordLinePrefix ) {
        // Trivial case
        if ( src == null ) return null;

        if ( newLineStr == null )
            newLineStr = System.getProperty( "line.separator" );

        if ( longWordBreak == null )
            longWordBreak = "";

        if ( longWordLinePrefix == null )
            longWordLinePrefix = "";

        // Adjust maximum line length to accommodate the newLine string
        lineLength -= newLineStr.length();
        if ( lineLength < 1 )
            lineLength = 1;

        // Guard for long word break or prefix that would create an infinite loop
        if ( wrapLongWords && lineLength - longWordBreak.length() - longWordLinePrefix.length() < 1 )
            lineLength += longWordBreak.length() + longWordLinePrefix.length();

        int
                remaining = lineLength,
                breakLength = longWordBreak.length();

        Matcher m = Pattern.compile( ".+?[ \\t]|.+?(?:" + newLineStr + ")|.+?$" ).matcher( src );

        StringBuilder cache = new StringBuilder();

        while ( m.find() ) {
            String word = m.group();

            // Breakup long word
            while ( wrapLongWords && word.length() > lineLength ) {
                cache
                        .append( word.substring( 0, remaining - breakLength ) )
                        .append( longWordBreak )
                        .append( newLineStr );
                word = longWordLinePrefix + word.substring( remaining - breakLength );
                remaining = lineLength;
            } // if

            // Linefeed if word exceeds remaining space
            if ( word.length() > remaining ) {
                cache
                        .append( newLineStr )
                        .append( word );
                remaining = lineLength;
            } // if

            // Word fits in remaining space
            else
                cache.append( word );

            remaining -= word.length();
        } // while

        return cache.toString();
    } // wrap()

    public static float noUse(ItemStack sword, ClientLevel clientWorld, LivingEntity entity, int i) {
        return entity != null && entity.getMainHandItem() == sword && entity.getOffhandItem() != ItemStack.EMPTY ? 1.0F : 0.0F;
    }

    public static float blocking(ItemStack itemStack, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
        return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
    }

    public static float pulling(ItemStack bow, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
        return livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == bow ? 1.0F : 0.0F;
    }

    public static float pull(ItemStack bow, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
        if (livingEntity == null) {
            return 0.0F;
        } else {
            return livingEntity.getUseItem() != bow ? 0.0F : (float)(bow.getUseDuration() - livingEntity.getUseItemRemainingTicks()) / 20.0F;
        }
    }

    public static float isOpen(ItemStack stack, ClientLevel clientWorld, LivingEntity livingEntity, int i) {
        return LockItem.isOpen(stack) ? 1f : 0f;
    }


    public static Camera getCamera() {
        return Minecraft.getInstance().gameRenderer.getMainCamera();
    }

    public static boolean holdingPick(Player player) {
        for (InteractionHand InteractionHand : InteractionHand.values())
            if (player.getItemInHand(InteractionHand).is(ItemRegistry.LOCKPICK.get()))
                return true;
        return false;
    }

    public static double distanceToLineSq(Vec3 p, Vec3 l1, Vec3 l2) {
        Vec3 l = l2.subtract(l1);
        return l.cross(p.subtract(l1)).lengthSqr() / l.lengthSqr();
    }

    public static Frustum getFrustum(LevelRenderer lr) {
        return lr.capturedFrustum != null ? lr.capturedFrustum : lr.cullingFrustum;
    }

    public static void renderHudTooltip(PoseStack mtx, List<? extends FormattedCharSequence> lines, Font font) {
        if (lines.isEmpty())
            return;
        int width = 0;
        for (FormattedCharSequence line : lines) {
            int j = font.width(line);
            if (j > width)
                width = j;
        }

        int x = 36;
        int y = -36;
        int height = 8;
        if (lines.size() > 1)
            height += 2 + (lines.size() - 1) * 10;

        mtx.pushPose();

        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        square(buf, mtx, 0f, 0f, 4f, 0.05f, 0f, 0.3f, 0.8f);
        line(buf, mtx, 1f, -1f, x / 3f + 0.6f, y / 2f, 2f, 0.05f, 0f, 0.3f, 0.8f);
        line(buf, mtx, x / 3f, y / 2f, x - 3f, y / 2f, 2f, 0.05f, 0f, 0.3f, 0.8f);
        // line(buf, last, 1f, -1f, x - 3f, y / 2f, 2f, 0.05f, 0f, 0.3f, 0.8f);
        vGradient(buf, mtx, x - 3, y - 4, x + width + 3, y - 3, 0.0627451f, 0f, 0.0627451f, 0.9411765f, 0.0627451f, 0f, 0.0627451f, 0.9411765f);
        vGradient(buf, mtx, x - 3, y + height + 3, x + width + 3, y + height + 4, 0.0627451f, 0f, 0.0627451f, 0.9411765f, 0.0627451f, 0f, 0.0627451f, 0.9411765f);
        vGradient(buf, mtx, x - 3, y - 3, x + width + 3, y + height + 3, 0.0627451f, 0f, 0.0627451f, 0.9411765f, 0.0627451f, 0f, 0.0627451f, 0.9411765f);
        vGradient(buf, mtx, x - 4, y - 3, x - 3, y + height + 3, 0.0627451f, 0f, 0.0627451f, 0.9411765f, 0.0627451f, 0f, 0.0627451f, 0.9411765f);
        vGradient(buf, mtx, x + width + 3, y - 3, x + width + 4, y + height + 3, 0.0627451f, 0f, 0.0627451f, 0.9411765f, 0.0627451f, 0f, 0.0627451f, 0.9411765f);
        vGradient(buf, mtx, x - 3, y - 3 + 1, x - 3 + 1, y + height + 3 - 1, 0.3137255f, 0f, 1f, 0.3137255f, 0.15686275f, 0f, 0.49803922f, 0.3137255f);
        vGradient(buf, mtx, x + width + 2, y - 3 + 1, x + width + 3, y + height + 3 - 1, 0.3137255f, 0f, 1f, 0.3137255f, 0.15686275f, 0f, 0.49803922f, 0.3137255f);
        vGradient(buf, mtx, x - 3, y - 3, x + width + 3, y - 3 + 1, 0.3137255f, 0f, 1f, 0.3137255f, 0.3137255f, 0f, 1f, 0.3137255f);
        vGradient(buf, mtx, x - 3, y + height + 2, x + width + 3, y + height + 3, 0.15686275f, 0f, 0.49803922f, 0.3137255f, 0.15686275f, 0f, 0.49803922f, 0.3137255f);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderTexture(0, 0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        // RenderSystem.shadeModel(7425);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        BufferUploader.draw(buf.end());
        // RenderSystem.shadeModel(7424);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableBlend();
        RenderSystem.setShaderTexture(0, 7424);
        MultiBufferSource.BufferSource buf1 = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());

        Matrix4f last = mtx.last().pose();
        for (int a = 0; a < lines.size(); ++a) {
            FormattedCharSequence line = lines.get(a);
            if (line != null)
                font.drawInBatch(line, (float) x, (float) y, -1, true, last, buf1, Font.DisplayMode.NORMAL, 0, 15728880);
            if (a == 0)
                y += 2;
            y += 10;
        }

        buf1.endBatch();

        mtx.popPose();
    }

    public static Frustum getFrustum(PoseStack mtx, Matrix4f proj) {
        LevelRenderer lr = Minecraft.getInstance().levelRenderer;
        Frustum ch = getFrustum(lr);
        if(ch != null)
            return ch;
        ch = new Frustum(mtx.last().pose(), proj);
        Vec3 pos = getCamera().getPosition();
        ch.prepare(pos.x, pos.y, pos.z);
        return ch;
    }

    public static Vector3f worldToScreen(Vec3 pos, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        Camera cam = getCamera();
        Vec3 o = cam.getPosition();

        Vector3f pos1 = new Vector3f((float) (o.x - pos.x), (float) (o.y - pos.y), (float) (o.z - pos.z));
        Quaternionf rot = new Quaternionf(cam.rotation());
        rot.conjugate();
        pos1.rotate(rot);

        // Account for view bobbing
        if (mc.options.bobView().get() && mc.getCameraEntity() instanceof Player)
        {
            Player player = (Player) mc.getCameraEntity();
            float f = player.walkDist - player.walkDistO;
            float f1 = -(player.walkDist + f * partialTicks);
            float f2 = Mth.lerp(partialTicks, player.oBob, player.bob);

            float angle1 = Math.abs(Mth.cos(f1 * (float) Math.PI - 0.2f) * f2) * 5f;
            Quaternionf rot1 = new Quaternionf().rotateX(angle1);

            float angle2 = Mth.sin(f1 * (float) Math.PI) * f2 * 3f;
            Quaternionf rot2 = new Quaternionf().rotateZ(angle2);
            rot1.conjugate();
            rot2.conjugate();
            pos1.rotate(rot1);
            pos1.rotate(rot2);
            pos1.add(Mth.sin(f1 * (float) Math.PI) * f2 * 0.5f, Math.abs(Mth.cos(f1 * (float) Math.PI) * f2), 0f);
        }

        Window w = mc.getWindow();
        float sc = w.getGuiScaledHeight() / 2f / pos1.z() / (float) Math.tan(Math.toRadians(mc.gameRenderer.getFov(cam, partialTicks, true) / 2f));
        pos1.mul(-sc, -sc, 1f);
        pos1.add(w.getGuiScaledWidth() / 2f, w.getGuiScaledHeight() / 2f, 0f);

        return pos1;
    }

    // TODO: FIXME Cant batch like the others? Why? ;-;
    public static void texture(PoseStack mtx, float x, float y, int u, int v, int width, int height, int texWidth, int texHeight, float alpha) {
        Matrix4f last = mtx.last().pose();
        float f = 1f / texWidth;
        float f1 = 1f / texHeight;

        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buf.vertex(last, x, y + height, 0f).uv(u * f, (v + height) * f1).color(1f, 1f, 1f, alpha).endVertex();
        buf.vertex(last, x + width, y + height, 0f).uv((u + width) * f, (v + height) * f1).color(1f, 1f, 1f, alpha).endVertex();
        buf.vertex(last, x + width, y, 0f).uv((u + width) * f,  v * f1).color(1f, 1f, 1f, alpha).endVertex();
        buf.vertex(last, x, y, 0f).uv(u * f, v * f1).color(1f, 1f, 1f, alpha).endVertex();
        buf.end();
        //RenderSystem.enableBlend();
        //RenderSystem.defaultBlendFunc();
        //BufferUploader.draw(buf.end());
    }

    // https://stackoverflow.com/questions/7854043/drawing-rectangle-between-two-points-with-arbitrary-width
    public static void line(BufferBuilder buf, PoseStack mtx, float x1, float y1, float x2, float y2, float width, float r, float g, float b, float a) {
        Matrix4f last = mtx.last().pose();
        // Construct perpendicular
        float pX = y2 - y1;
        float pY = x1 - x2;
        // Normalize and scale by half width
        float pL = Mth.sqrt(pX * pX + pY * pY);
        pX *= width / 2f / pL;
        pY *= width / 2f / pL;

        buf.vertex(last, x1 + pX, y1 + pY, 0f).color(r, g, b, a).endVertex();
        buf.vertex(last, x1 - pX, y1 - pY, 0f).color(r, g, b, a).endVertex();
        buf.vertex(last, x2 - pX, y2 - pY, 0f).color(r, g, b, a).endVertex();
        buf.vertex(last, x2 + pX, y2 + pY, 0f).color(r, g, b, a).endVertex();
    }

    public static void square(BufferBuilder buf, PoseStack mtx, float x, float y, float length, float r, float g, float b, float a) {
        Matrix4f last = mtx.last().pose();
        length /= 2f;
        buf.vertex(last, x - length, y - length, 0f).color(r, g, b, a).endVertex();
        buf.vertex(last, x - length, y + length, 0f).color(r, g, b, a).endVertex();
        buf.vertex(last, x + length, y + length, 0f).color(r, g, b, a).endVertex();
        buf.vertex(last, x + length, y - length, 0f).color(r, g, b, a).endVertex();
    }

    public static void vGradient(BufferBuilder bld, PoseStack mtx, int x1, int y1, int x2, int y2, float r1, float g1, float b1, float a1, float r2, float g2, float b2, float a2) {
        Matrix4f last = mtx.last().pose();
        bld.vertex(last, x2, y1, 0f).color(r1, g1, b1, a1).endVertex();
        bld.vertex(last, x1, y1, 0f).color(r1, g1, b1, a1).endVertex();
        bld.vertex(last, x1, y2, 0f).color(r2, g2, b2, a2).endVertex();
        bld.vertex(last, x2, y2, 0f).color(r2, g2, b2, a2).endVertex();
    }

    public static float lerp(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    public static double lerp(double start, double end, double progress) {
        return start + (end - start) * progress;
    }

    /*
     * Make 2d bezier??
     * Implement 2d cubic bezier function
     * https://stackoverflow.com/questions/11696736/recreating-css3-transitions-cubic-bezier-curve
     * https://math.stackexchange.com/questions/26846/is-there-an-explicit-form-for-cubic-b%C3%A9zier-curves
     * https://www.gamedev.net/forums/topic/572263-bezier-curve-for-animation/
     * https://math.stackexchange.com/questions/2571471/understanding-of-cubic-b%C3%A9zier-curves-in-one-dimension
     */
    public static float cubicBezier1d(float anchor1, float anchor2, float progress) {
        float omp = 1f - progress;
        return 3f * omp * omp * progress * anchor1 + 3f * omp * progress * progress * anchor2 + progress * progress * progress;
    }
}