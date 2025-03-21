package com.ryankshah.skyrimcraft.character.lockpicking;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.network.skill.lockpicking.UpdateSelection;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.Constants;
import commonnetwork.api.Dispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class Selection implements ISelection
{
    public static Codec<Selection> CODEC = RecordCodecBuilder.create(questsInstance -> questsInstance.group(
            BlockPos.CODEC.fieldOf("blockPos").forGetter(Selection::get)
    ).apply(questsInstance, Selection::new));

    public static final ResourceLocation ID = new ResourceLocation(Constants.MOD_ID, "selection");

    public BlockPos pos;

    public Selection(BlockPos pos) {
        this.pos = pos;
    }

    public Selection() {
        this(BlockPos.ZERO);
    }

    @Override
    public BlockPos get() {
        return this.pos;
    }

    @Override
    public void set(BlockPos pos) {
        this.pos = pos;
    }

    public static Selection getSelection(Player player) {
        return Services.PLATFORM.getSelection(player);
    }

    private void syncToSelf(Player owner) {
        syncTo(owner);
    }

    protected void syncTo(Player player) {
        Dispatcher.sendToClient(new UpdateSelection(this), (ServerPlayer) player);
    }

//    protected void syncTo(PacketDistributor.PacketTarget target) {
//        target.send(new UpdateCharacter(this));
//    }

    public static void entityJoinLevel(Player player) {
        if (player.level().isClientSide)
            return;
        getSelection(player).syncToSelf(player);
    }

    public static void playerJoinWorld(Player player) {
        if (player.level().isClientSide)
            return;
        getSelection(player).syncToSelf(player);
    }

    public static void playerChangedDimension(Player player) {
        if (player.level().isClientSide)
            return;
        getSelection(player).syncToSelf(player);
    }

    public static void playerStartTracking(Player player) {
        if (player.level().isClientSide)
            return;
        getSelection(player).syncToSelf(player);
    }

    public static void playerDeath(Player player) {
        var newHandler = getSelection(player);

        Services.PLATFORM.setSelection(player, Services.PLATFORM.getSelection(player));
        Dispatcher.sendToClient(new UpdateSelection(newHandler), (ServerPlayer) player); //.sendToPlayer((ServerPlayer) player, new UpdateCharacter(newHandler));
    }

    public static void playerClone(boolean isWasDeath, Player player, Player oldPlayer) {
        if(!isWasDeath)
            return;
//        oldPlayer.revive();
        Selection oldHandler = Selection.getSelection(oldPlayer);
        Services.PLATFORM.setSelection(player, oldHandler);
        Selection newHandler = Selection.getSelection(player);
        Dispatcher.sendToClient(new UpdateSelection(newHandler), (ServerPlayer) player);
    }
}