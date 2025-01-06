package com.ryankshah.skyrimcraft.platform;

import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.platform.services.IPlatformHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

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
        return player == null ? new Character() : player.getData(SkyrimcraftNeoForge.CHARACTER);
    }

    @Override
    public void setCharacterData(Player player, Character characterData) {
        player.setData(SkyrimcraftNeoForge.CHARACTER, characterData);
    }

    @Override
    public ExtraCharacter getExtraCharacter(Player player) {
        return player == null ? new ExtraCharacter() : player.getData(SkyrimcraftNeoForge.EXTRA_CHARACTER);
    }

    @Override
    public void setExtraCharacterData(Player player, ExtraCharacter characterData) {
        player.setData(SkyrimcraftNeoForge.EXTRA_CHARACTER, characterData);
    }

    @Override
    public LevelUpdates getLevelUpdates(Player player) {
        return player == null ? new LevelUpdates() : player.getData(SkyrimcraftNeoForge.LEVEL_UPDATES);
    }

    @Override
    public void setLevelUpdates(Player player, LevelUpdates levelUpdates) {
        player.setData(SkyrimcraftNeoForge.LEVEL_UPDATES, levelUpdates);
    }

    @Override
    public StatIncreases getStatIncreases(Player player) {
        return player == null ? new StatIncreases() : player.getData(SkyrimcraftNeoForge.STAT_INCREASES);
    }

    @Override
    public void setStatIncreases(Player player, StatIncreases statIncreases) {
        player.setData(SkyrimcraftNeoForge.STAT_INCREASES, statIncreases);
    }

    @Override
    public PlayerQuests getQuests(Player player) {
        return player == null ? new PlayerQuests() : player.getData(SkyrimcraftNeoForge.QUESTS);
    }

    @Override
    public void setQuestData(Player player, PlayerQuests playerQuests) {
        player.setData(SkyrimcraftNeoForge.QUESTS, playerQuests);
    }

    @Override
    public boolean doesEntityHavePersistentData(LivingEntity entity, String id) {
        return entity.hasData(SkyrimcraftNeoForge.CONJURE_FAMILIAR_SPELL_DATA); //entity.getPersistentData().contains(id);
    }

    @Override
    public void setEntityPersistentData(LivingEntity entity, String id, long value) {
        entity.setData(SkyrimcraftNeoForge.CONJURE_FAMILIAR_SPELL_DATA, value);
    }
}