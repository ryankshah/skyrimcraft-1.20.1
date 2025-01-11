package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.SkyrimGuiOverlayForge;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value= Dist.CLIENT)
public class GuiModEvents
{
    @SubscribeEvent
    public static void registerOverlays(RegisterGuiOverlaysEvent event){
        event.registerAboveAll("skyrim_compass", new SkyrimGuiOverlayForge.SkyrimCompass());
        event.registerAboveAll( "skyrim_magicka", new SkyrimGuiOverlayForge.SkyrimMagicka());
        event.registerAboveAll( "current_spells", new SkyrimGuiOverlayForge.SkyrimSpells());
        event.registerAboveAll( "target_health", new SkyrimGuiOverlayForge.SkyrimTargetHealth());
        event.registerAboveAll( "level_updates", new SkyrimGuiOverlayForge.SkyrimLevelUpdates());
        event.registerAbove(VanillaGuiOverlay.PLAYER_HEALTH.id(),  "skyrim_health", new SkyrimGuiOverlayForge.SkyrimHealth());
        event.registerAbove(VanillaGuiOverlay.FOOD_LEVEL.id(),  "skyrim_stamina", new SkyrimGuiOverlayForge.SkyrimStamina());
        event.registerAbove(VanillaGuiOverlay.ARMOR_LEVEL.id(),  "skyrim_armor", new SkyrimGuiOverlayForge.SkyrimArmorIcons());
        event.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(),  "skyrim_air", new SkyrimGuiOverlayForge.SkyrimAir());
        event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(),  "skyrim_crosshair", new SkyrimGuiOverlayForge.SkyrimCrosshair());
        event.registerAbove(VanillaGuiOverlay.EXPERIENCE_BAR.id(),  "skyrim_xpbar", new SkyrimGuiOverlayForge.SkyrimXPBar());
    }
}