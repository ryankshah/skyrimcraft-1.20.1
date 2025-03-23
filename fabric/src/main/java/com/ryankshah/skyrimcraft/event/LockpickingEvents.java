package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.lockpicking.ILockableHandler;
import com.ryankshah.skyrimcraft.config.ClientConfig;
import com.ryankshah.skyrimcraft.config.CommonConfig;
import com.ryankshah.skyrimcraft.item.LockingItem;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import com.ryankshah.skyrimcraft.screen.container.LockPickingContainer;
import com.ryankshah.skyrimcraft.util.CommonUtil;
import com.ryankshah.skyrimcraft.util.Lockable;
import com.ryankshah.skyrimcraft.util.LocksPredicates;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public final class LockpickingEvents
{
    public static final Component LOCKED_MESSAGE = Component.translatable(Constants.MODID + ".status.locked");

    private LockpickingEvents() {}

    public static InteractionResult onRightClick(Player player, Level world, InteractionHand hand, BlockHitResult result) {
        BlockPos pos = result.getBlockPos();
        ILockableHandler handler = Services.PLATFORM.getLockableHandler(world); //LocksComponents.LOCKABLE_HANDLER.get(world);
        Lockable[] intersect = handler.getInChunk(pos).values().stream().filter(lkb -> lkb.bb.intersects(pos)).toArray(Lockable[]::new);
        if(intersect.length == 0)
            return InteractionResult.PASS;
        if(hand != InteractionHand.MAIN_HAND) { // FIXME Better way to prevent firing multiple times
            return InteractionResult.FAIL;
        }
        ItemStack stack = player.getItemInHand(hand);
        Optional<Lockable> locked = Arrays.stream(intersect).filter(LocksPredicates.LOCKED).findFirst();
        if(locked.isPresent()) {
            Lockable lkb = locked.get();
            Item item = stack.getItem();
            boolean f=true;
            // FIXME erase this ugly ass hard coded shit from the face of the earth and make a proper way to do this (maybe mixin to where the right click event is fired from)
            if(!stack.is(ItemRegistry.LOCKPICK.get()) && item != ItemRegistry.MASTER_KEY.get() && (!stack.is(ItemRegistry.KEY.get()) || LockingItem.getOrSetId(stack) != lkb.lock.id))
            {
                lkb.swing(20);
//                world.playSound(player, pos, LocksSoundEvents.LOCK_RATTLE, SoundSource.BLOCKS, 1f, 1f);
                f=false;
            }
            player.swing(InteractionHand.MAIN_HAND);

            if(!(player instanceof ServerPlayer))
                return InteractionResult.PASS;
//            if(world.isClientSide && ClientConfig.DEAF_MODE.get())
//                player.displayClientMessage(LOCKED_MESSAGE, true);
            if(player.isShiftKeyDown()&&( item == ItemRegistry.MASTER_KEY.get() || (stack.is(ItemRegistry.KEY.get()) && LockingItem.getOrSetId(stack) == lkb.lock.id))) {
                return InteractionResult.PASS;
            }
            if(f) {
                player.openMenu(new LockPickingContainer.Provider(hand, lkb));
                return InteractionResult.CONSUME;
            }
            else
                return InteractionResult.FAIL;
        }
        if(CommonConfig.ALLOW_REMOVING_LOCKS && player.isShiftKeyDown() && stack.isEmpty()) {
            Lockable[] match = Arrays.stream(intersect).filter(LocksPredicates.NOT_LOCKED).toArray(Lockable[]::new);
            if(match.length == 0)
                return InteractionResult.PASS;
            world.playSound(player, pos, SoundEvents.IRON_DOOR_OPEN, SoundSource.BLOCKS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
            player.swing(InteractionHand.MAIN_HAND);
            if(player instanceof ServerPlayer) {
                Constants.LOG.info("Removing lockable");
                for (Lockable lkb : match) {
                    world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, lkb.stack));
                    handler.remove(lkb.id);
                }
                return InteractionResult.CONSUME;
            }else {
                return InteractionResult.PASS;
            }
        }
        return InteractionResult.PASS;
    }


    public static boolean canBreakLockable(Level world,Player player, BlockPos pos) {
        return (CommonConfig.PROTECT_LOCKABLES &&
                !player.isCreative() &&
                CommonUtil.lockedAndRelated(world, pos));
    }

    public static boolean onBlockBreaking(Level world, Player player, BlockPos pos, BlockState state,@Nullable BlockEntity entity) {
        return !canBreakLockable(world,player, pos);
    }

    public static void onBlockBreak(Level world, Player player, BlockPos pos, BlockState state,@Nullable BlockEntity entity) {
//		if(!canBreakLockable(world,player, pos)) {
//			world.setBlockAndUpdate(pos, state);
//		}
    }

    public static void register() {
//        LootTableEvents.MODIFY.register(LockpickingEvents::onLootTableLoad);
        PlayerBlockBreakEvents.BEFORE.register(LockpickingEvents::onBlockBreaking);
        PlayerBlockBreakEvents.AFTER.register(LockpickingEvents::onBlockBreak);
        UseBlockCallback.EVENT.register(LockpickingEvents::onRightClick);
    }

}