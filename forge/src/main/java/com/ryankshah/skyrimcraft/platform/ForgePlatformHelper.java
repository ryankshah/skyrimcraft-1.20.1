package com.ryankshah.skyrimcraft.platform;

import com.ryankshah.skyrimcraft.capability.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableStorage;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import com.ryankshah.skyrimcraft.platform.services.IPlatformHelper;
import com.ryankshah.skyrimcraft.screen.container.LockPickingContainer;
import com.ryankshah.skyrimcraft.util.Lockable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.network.NetworkHooks;

import java.util.function.Consumer;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public Character getCharacter(Player player) {
        return player.getCapability(CharacterCapability.CAPABILITY).resolve().get().getCharacter(); // CharacterCapability.(player);
    }

    @Override
    public void setCharacterData(Player player, Character characterData) {
        player.getCapability(CharacterCapability.CAPABILITY).orElse(new CharacterCapability()).setCharacter(characterData);
    }

    @Override
    public ExtraCharacter getExtraCharacter(Player player) {
        return player.getCapability(ExtraCharacterCapability.CAPABILITY).resolve().get().getCharacter();
    }

    @Override
    public void setExtraCharacterData(Player player, ExtraCharacter characterData) {
        player.getCapability(ExtraCharacterCapability.CAPABILITY).orElse(new ExtraCharacterCapability()).setCharacter(characterData);
    }

    @Override
    public LevelUpdates getLevelUpdates(Player player) {
        return player.getCapability(LevelUpdatesCapability.CAPABILITY).resolve().get().getLevelUpdatesAttachment();
    }

    @Override
    public void setLevelUpdates(Player player, LevelUpdates levelUpdates) {
        player.getCapability(LevelUpdatesCapability.CAPABILITY).orElse(new LevelUpdatesCapability()).setLevelUpdates(levelUpdates);
    }

    @Override
    public StatIncreases getStatIncreases(Player player) {
        return player.getCapability(StatIncreasesCapability.CAPABILITY).resolve().get().getStatIncreases();
    }

    @Override
    public void setStatIncreases(Player player, StatIncreases statIncreases) {
        player.getCapability(StatIncreasesCapability.CAPABILITY).orElse(new StatIncreasesCapability()).setStatIncreases(statIncreases);
    }

    @Override
    public PlayerQuests getQuests(Player player) {
        return player.getCapability(PlayerQuestsCapability.CAPABILITY).resolve().get().getPlayerQuests();
    }

    @Override
    public void setQuestData(Player player, PlayerQuests playerQuests) {
        player.getCapability(PlayerQuestsCapability.CAPABILITY).orElse(new PlayerQuestsCapability()).setPlayerQuests(playerQuests);
    }

    @Override
    public boolean doesEntityHavePersistentData(LivingEntity entity, String id) {
        return entity.getPersistentData().contains(id); //SkyrimcraftNeoForge.CONJURE_FAMILIAR_SPELL_DATA); //entity.getPersistentData().contains(id);
    }

    @Override
    public void setEntityPersistentData(LivingEntity entity, String id, long value) {
        CompoundTag tag = new CompoundTag();
        tag.putLong(id, value);
        entity.getPersistentData().put(id, tag);
//        entity.setData(SkyrimcraftNeoForge.CONJURE_FAMILIAR_SPELL_DATA, value);
    }

    @Override
    public LockableHandler getLockableHandler(Level level) {
        return level.getCapability(LockableHandlerCapability.CAPABILITY).resolve().get().getLockableHandler();
    }

    @Override
    public void setLockableHandler(Level level, LockableHandler handler) {
        level.getCapability(LockableHandlerCapability.CAPABILITY).orElse(new LockableHandlerCapability(level)).setLockableHandler(handler);
    }

    @Override
    public LockableStorage getLockableStorage(LevelChunk chunk) {
        return chunk.getCapability(LockableStorageCapability.CAPABILITY).resolve().get().getLockableStorage();
    }

    @Override
    public void setLockableStorage(LevelChunk chunk, LockableStorage storage) {
        chunk.getCapability(LockableStorageCapability.CAPABILITY).orElse(new LockableStorageCapability(chunk)).setLockableStorage(storage);
    }

    @Override
    public Selection getSelection(Player player) {
        return player.getCapability(SelectionCapability.CAPABILITY).resolve().get().getSelection();
    }

    @Override
    public void setSelection(Player player, Selection selection) {
        player.getCapability(SelectionCapability.CAPABILITY).orElse(new SelectionCapability()).setSelection(selection);
    }

    @Override
    public void openLockpickingMenu(ServerPlayer player, MenuProvider provider, InteractionHand hand, Lockable lkb, Consumer<FriendlyByteBuf> buf) {
        NetworkHooks.openScreen(player, new LockPickingContainer.Provider(hand, lkb), new LockPickingContainer.Writer(hand, lkb));
    }
}