package com.ryankshah.skyrimcraft.mixin.client;


import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ryankshah.skyrimcraft.SkyrimcraftCommonClient;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {


    @Inject(method = "render",at = @At("TAIL"))
    private void render(GuiGraphics guiGraphics, float f, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        // if(e.getType() != RenderGuiOverlayEvent.ElementType.ALL || tooltipLockable == null)
        if (SkyrimcraftCommonClient.tooltipLockable == null)
            return;
        if (SkyrimcraftCommonClient.holdingPick(mc.player)) {
            PoseStack mtx = guiGraphics.pose();
            Vector3f vec = ClientUtil.worldToScreen(SkyrimcraftCommonClient.tooltipLockable.getLockState(mc.level).pos, f);
            if (vec.z() < 0d) {
                mtx.pushPose();
                mtx.translate(vec.x(), vec.y(), 0f);
                SkyrimcraftCommonClient.renderHudTooltip(mtx, Lists.transform(SkyrimcraftCommonClient.tooltipLockable.stack.getTooltipLines(mc.player, mc.options.advancedItemTooltips ? TooltipFlag.ADVANCED : TooltipFlag.NORMAL), Component::getVisualOrderText), mc.font);
                mtx.popPose();
            }
        }
        SkyrimcraftCommonClient.tooltipLockable = null;
    }

}