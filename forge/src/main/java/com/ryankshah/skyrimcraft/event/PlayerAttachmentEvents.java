package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE) // FORGE or MOD event bus??
public class PlayerAttachmentEvents
{
    @SubscribeEvent
    public static void entityJoinLevel(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            Character.entityJoinLevel(player);
            ExtraCharacter.entityJoinLevel(player);
            LevelUpdates.entityJoinLevel(player);
            StatIncreases.entityJoinLevel(player);
        }
    }

    @SubscribeEvent
    public static void joinWorld(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(player instanceof ServerPlayer) {
            Character.playerJoinWorld(player);
            ExtraCharacter.playerJoinWorld(player);
            LevelUpdates.playerJoinWorld(player);
            StatIncreases.playerJoinWorld(player);
        }
    }

    @SubscribeEvent
    public static void changedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if(player instanceof ServerPlayer) {
            Character.playerChangedDimension(player);
            ExtraCharacter.playerChangedDimension(player);
            LevelUpdates.playerChangedDimension(player);
            StatIncreases.playerChangedDimension(player);
        }
    }

//    @SubscribeEvent
//    public static void track(PlayerEvent.StartTracking event) {
//        if(event.getEntity() instanceof Player player) {
//            Character.playerStartTracking(player);
//            ExtraCharacter.playerStartTracking(player);
//            LevelUpdates.playerStartTracking(player);
//            StatIncreases.playerStartTracking(player);
//        }
//    }

    @SubscribeEvent
    public static void playerDeath(LivingDeathEvent event) {
        if(event.getEntity() instanceof ServerPlayer player) {
            Character.playerDeath(player);
            ExtraCharacter.playerDeath(player);
            LevelUpdates.playerDeath(player);
            StatIncreases.playerDeath(player);
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        Character.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        ExtraCharacter.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        LevelUpdates.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        StatIncreases.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
    }
}