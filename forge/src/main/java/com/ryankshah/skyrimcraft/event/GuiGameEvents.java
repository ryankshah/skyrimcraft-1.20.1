package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.util.ClientUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value= Dist.CLIENT)
public class GuiGameEvents
{
    @SubscribeEvent
    public static void renderOverlays(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == GuiOverlayManager.findOverlay(VanillaGuiOverlay.BOSS_EVENT_PROGRESS.id()) && !ClientUtil.getMinecraft().options.hideGui && !event.isCanceled()) {
            event.getGuiGraphics().pose().pushPose();
            event.getGuiGraphics().pose().translate(0, 28, 0);
            event.getGuiGraphics().pose().popPose();
        }

        if(event.getOverlay() == GuiOverlayManager.findOverlay(VanillaGuiOverlay.PLAYER_HEALTH.id())
                || event.getOverlay() == GuiOverlayManager.findOverlay(VanillaGuiOverlay.CROSSHAIR.id())
                || event.getOverlay() == GuiOverlayManager.findOverlay(VanillaGuiOverlay.FOOD_LEVEL.id())
                || event.getOverlay() == GuiOverlayManager.findOverlay(VanillaGuiOverlay.ARMOR_LEVEL.id())
                || event.getOverlay() == GuiOverlayManager.findOverlay(VanillaGuiOverlay.AIR_LEVEL.id())
                || event.getOverlay() == GuiOverlayManager.findOverlay(VanillaGuiOverlay.EXPERIENCE_BAR.id())
        ) {
            event.setCanceled(true);
        }
    }
}