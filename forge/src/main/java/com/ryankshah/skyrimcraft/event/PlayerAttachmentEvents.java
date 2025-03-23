package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.character.attachment.LevelUpdates;
import com.ryankshah.skyrimcraft.character.attachment.StatIncreases;
import com.ryankshah.skyrimcraft.character.lockpicking.ILockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.ISelection;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import com.ryankshah.skyrimcraft.config.CommonConfig;
import com.ryankshah.skyrimcraft.item.LockingItem;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import com.ryankshah.skyrimcraft.util.CommonUtil;
import com.ryankshah.skyrimcraft.util.Lockable;
import com.ryankshah.skyrimcraft.util.LocksPredicates;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Arrays;
import java.util.Optional;

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
            Selection.entityJoinLevel(player);
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
            Selection.playerJoinWorld(player);
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
            Selection.playerChangedDimension(player);
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
            Selection.playerDeath(player);
        }
    }

    @SubscribeEvent
    public static void playerClone(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        Character.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        ExtraCharacter.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        LevelUpdates.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        StatIncreases.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        Selection.playerClone(event.isWasDeath(), event.getEntity(), event.getOriginal());
        event.getOriginal().invalidateCaps();
    }

    public static final Component LOCKED_MESSAGE = Component.translatable(Constants.MODID + ".status.locked");

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload e) {
        LevelChunk ch = (LevelChunk) e.getChunk();
        ILockableHandler handler = Services.PLATFORM.getLockableHandler(ch.getLevel()); //ch.getLevel().getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
//        ch.getCapability(LocksCapabilities.LOCKABLE_STORAGE).orElse(null).get()
        Services.PLATFORM.getLockableStorage(ch).get().values().forEach(lkb ->
        {
            handler.getLoaded().remove(lkb.id);
            lkb.deleteObserver(handler);
        });
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRightClick(PlayerInteractEvent.RightClickBlock e) {
        BlockPos pos = e.getPos();
        Level world = e.getLevel();
        Player player = e.getEntity();
        ILockableHandler handler = Services.PLATFORM.getLockableHandler(world); //world.getCapability(LocksCapabilities.LOCKABLE_HANDLER).orElse(null);
        Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
        if (intersect.length == 0)
            return;
//        if(intersect.length > 1) {
//            for(Lockable lkb : Arrays.stream(intersect).toList().subList(1, intersect.length))
//            {
//                handler.remove(lkb.id);
//            }
//        }
        if (e.getHand() != InteractionHand.MAIN_HAND) // FIXME Better way to prevent firing multiple times
        {
            e.setUseBlock(Event.Result.DENY);
            return;
        }
        ItemStack stack = e.getItemStack();
        Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
        if (locked.isPresent()) {
            Lockable lkb = locked.get();
            e.setUseBlock(Event.Result.DENY);
            Item item = stack.getItem();
            // FIXME erase this ugly ass hard coded shit from the face of the earth and make a proper way to do this (maybe mixin to where the right click event is fired from)
            if (!stack.is(ItemRegistry.LOCKPICK.get()) && item != ItemRegistry.MASTER_KEY.get() && (!stack.is(TagsRegistry.ItemTagsInit.KEYS) || LockingItem.getOrSetId(stack) != lkb.lock.id)) {
                lkb.swing(20);
//                world.playSound(player, pos, LocksSoundEvents.LOCK_RATTLE.get(), SoundSource.BLOCKS, 1f, 1f);
            }
            player.swing(InteractionHand.MAIN_HAND);
            if (world.isClientSide)
                player.displayClientMessage(LOCKED_MESSAGE, true);
            return;
        }
        if (CommonConfig.ALLOW_REMOVING_LOCKS && player.isShiftKeyDown() && stack.isEmpty()) {
            Lockable[] match = Arrays.stream(intersect).filter(LocksPredicates.NOT_LOCKED).toArray(Lockable[]::new);
            if (match.length == 0)
                return;
            e.setUseBlock(Event.Result.DENY);
//            world.playSound(player, pos, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
            player.swing(InteractionHand.MAIN_HAND);
            if (!world.isClientSide)
                for (Lockable lkb : match) {
                    world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, lkb.stack));
                    handler.remove(lkb.id);
                }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.START)
            return;
        ISelection select = Services.PLATFORM.getSelection(e.player);
        if (select == null || select.get() == null)
            return;
        for (ItemStack stack : e.player.getHandSlots())
            if (stack.is(TagsRegistry.ItemTagsInit.LOCKS))
                return;
        select.set(null);
    }

    public static boolean canBreakLockable(Player player, BlockPos pos) {
        return CommonConfig.PROTECT_LOCKABLES &&
                !player.isCreative() &&
                CommonUtil.lockedAndRelated(player.level(), pos);
    }

    @SubscribeEvent
    public static void onBlockBreaking(PlayerEvent.BreakSpeed e) {
        e.setCanceled(canBreakLockable(e.getEntity(), e.getPosition().get()));
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e) {
        e.setCanceled(canBreakLockable(e.getPlayer(), e.getPos()));
    }
}