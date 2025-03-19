package com.ryankshah.skyrimcraft;

import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.character.feature.render.RenderRaceLayer;
import com.ryankshah.skyrimcraft.character.feature.render.SpectralLayerRenderer;
import com.ryankshah.skyrimcraft.network.character.UpdateExtraCharacter;
import com.ryankshah.skyrimcraft.particle.EmittingLightningParticle;
import com.ryankshah.skyrimcraft.particle.LightningParticle;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.registry.ParticleRegistry;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SkyrimcraftForgeClient
{
    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {
        SkyrimcraftCommonClient.initClient();
        event.enqueueWork(ItemRegistry::registerItemModelProperties);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        SkyrimcraftCommonClient.registerRenderers(event::registerEntityRenderer, event::registerBlockEntityRenderer);
    }

    @SubscribeEvent
    public static void onEntityRenderLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        SkyrimcraftCommonClient.getLayerDefinitions().forEach(
                (layerdef, model) -> event.registerLayerDefinition(layerdef, () -> model)
        );
    }

    @SubscribeEvent
    public static void addPlayerLayers(EntityRenderersEvent.AddLayers event) {
        for(String skin : event.getSkins()) {
            ((PlayerRenderer)event.getSkin(skin)).addLayer(new RenderRaceLayer(event.getSkin(skin)));
            ((PlayerRenderer)event.getSkin(skin)).addLayer(new SpectralLayerRenderer(event.getSkin(skin)));
        }
    }

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleRegistry.LIGHTNING.get(), LightningParticle.Provider::new);
        event.registerSpriteSet(ParticleRegistry.EMITTING_LIGHTNING.get(), EmittingLightningParticle.Provider::new);
//        event.registerSpriteSet(ParticleInit.FIRE.get(), FireParticle.Provider::new);
    }
}