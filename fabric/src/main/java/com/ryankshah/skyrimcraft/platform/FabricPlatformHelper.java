package com.ryankshah.skyrimcraft.platform;

import com.ryankshah.skyrimcraft.SkyrimcraftFabric;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableStorage;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import com.ryankshah.skyrimcraft.platform.services.IPlatformHelper;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

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
        ((AttachmentTarget)entity).getAttachedOrSet(SkyrimcraftFabric.CONJURE_FAMILIAR_SPELL_DATA, value);
    }

    @Override
    public LockableHandler getLockableHandler(Level level) {
        return null;
    }

    @Override
    public void setLockableHandler(Level level, LockableHandler handler) {

    }

    @Override
    public LockableStorage getLockableStorage(LevelChunk chunk) {
        return null;
    }

    @Override
    public void setLockableStorage(LevelChunk chunk, LockableStorage storage) {

    }

    @Override
    public Selection getSelection(Player player) {
        return player == null ? new Selection() : ((AttachmentTarget)player).getAttachedOrCreate(SkyrimcraftFabric.SELECTION_DATA, Selection::new);
    }

    @Override
    public void setSelection(Player player, Selection selection) {
        ((AttachmentTarget)player).setAttached(SkyrimcraftFabric.SELECTION_DATA, selection);
    }
}
