package com.ryankshah.skyrimcraft.platform;

import com.ryankshah.skyrimcraft.SkyrimcraftFabric;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableStorage;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import com.ryankshah.skyrimcraft.platform.services.IPlatformHelper;
import com.ryankshah.skyrimcraft.screen.container.LockPickingContainer;
import com.ryankshah.skyrimcraft.util.Lockable;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Character getCharacter(Player player) {
        return player == null ? new Character() : ((AttachmentTarget)player).getAttachedOrCreate(SkyrimcraftFabric.CHARACTER_DATA, Character::new);
    }

    @Override
    public void setCharacterData(Player player, Character characterData) {
        ((AttachmentTarget)player).setAttached(SkyrimcraftFabric.CHARACTER_DATA, characterData);
    }

    @Override
    public ExtraCharacter getExtraCharacter(Player player) {
        return player == null ? new ExtraCharacter() : ((AttachmentTarget)player).getAttachedOrCreate(SkyrimcraftFabric.EXTRA_CHARACTER_DATA, ExtraCharacter::new);
    }

    @Override
    public void setExtraCharacterData(Player player, ExtraCharacter characterData) {
        ((AttachmentTarget)player).setAttached(SkyrimcraftFabric.EXTRA_CHARACTER_DATA, characterData);
    }

    @Override
    public LevelUpdates getLevelUpdates(Player player) {
        return player == null ? new LevelUpdates() : ((AttachmentTarget)player).getAttachedOrCreate(SkyrimcraftFabric.LEVEL_UPDATES_DATA, LevelUpdates::new);
    }

    @Override
    public void setLevelUpdates(Player player, LevelUpdates levelUpdates) {
        ((AttachmentTarget)player).setAttached(SkyrimcraftFabric.LEVEL_UPDATES_DATA, levelUpdates);
    }

    @Override
    public StatIncreases getStatIncreases(Player player) {
        return player == null ? new StatIncreases() : ((AttachmentTarget)player).getAttachedOrCreate(SkyrimcraftFabric.STAT_INCREASES_DATA, StatIncreases::new);
    }

    @Override
    public void setStatIncreases(Player player, StatIncreases statIncreases) {
        ((AttachmentTarget)player).setAttached(SkyrimcraftFabric.STAT_INCREASES_DATA, statIncreases);
    }

    @Override
    public PlayerQuests getQuests(Player player) {
        return player == null ? new PlayerQuests() : ((AttachmentTarget)player).getAttachedOrCreate(SkyrimcraftFabric.QUEST_DATA, PlayerQuests::new);
    }

    @Override
    public void setQuestData(Player player, PlayerQuests playerQuests) {
        ((AttachmentTarget)player).setAttached(SkyrimcraftFabric.QUEST_DATA, playerQuests);
    }

    @Override
    public boolean doesEntityHavePersistentData(LivingEntity entity, String id) {
        return entity == null ? false : ((AttachmentTarget)entity).hasAttached(SkyrimcraftFabric.CONJURE_FAMILIAR_SPELL_DATA);
    }

    @Override
    public void setEntityPersistentData(LivingEntity entity, String id, long value) {
        ((AttachmentTarget)entity).setAttached(SkyrimcraftFabric.CONJURE_FAMILIAR_SPELL_DATA, value); //getAttachedOrSet
    }

    @Override
    public LockableHandler getLockableHandler(Level level) {
        return level == null ? new LockableHandler(level) : ((AttachmentTarget)level).getAttachedOrCreate(SkyrimcraftFabric.LOCKABLE_HANDLER_DATA, LockableHandler::new);
    }

    @Override
    public void setLockableHandler(Level level, LockableHandler handler) {
        ((AttachmentTarget)level).setAttached(SkyrimcraftFabric.LOCKABLE_HANDLER_DATA, handler);
    }

    @Override
    public LockableStorage getLockableStorage(LevelChunk chunk) {
        return chunk == null ? new LockableStorage(chunk) : ((AttachmentTarget)chunk).getAttachedOrCreate(SkyrimcraftFabric.LOCKABLE_STORAGE_DATA, LockableStorage::new);
    }

    @Override
    public void setLockableStorage(LevelChunk chunk, LockableStorage storage) {
        ((AttachmentTarget)chunk).setAttached(SkyrimcraftFabric.LOCKABLE_STORAGE_DATA, storage);
    }

    @Override
    public Selection getSelection(Player player) {
        return player == null ? new Selection() : ((AttachmentTarget)player).getAttachedOrCreate(SkyrimcraftFabric.SELECTION_DATA, Selection::new);
    }

    @Override
    public void setSelection(Player player, Selection selection) {
        ((AttachmentTarget)player).setAttached(SkyrimcraftFabric.SELECTION_DATA, selection);
    }

    @Override
    public void openLockpickingMenu(ServerPlayer player, MenuProvider provider, InteractionHand hand, Lockable lkb, Consumer<FriendlyByteBuf> bufConsumer) {
        final ExtendedScreenHandlerFactory extendedProvider = new ExtendedScreenHandlerFactory() {

            @Override
            public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                return provider.createMenu(i, inventory, player);
            }

            @Override
            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
                bufConsumer.accept(buf);
            }

            @Override
            public Component getDisplayName() {
                return provider.getDisplayName();
            }

//            @Override
//            public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
//
//                bufConsumer.accept(buf);
//            }
        };

        player.openMenu(extendedProvider);
    }
}
