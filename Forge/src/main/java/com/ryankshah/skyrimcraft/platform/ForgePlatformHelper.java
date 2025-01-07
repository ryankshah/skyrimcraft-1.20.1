package com.ryankshah.skyrimcraft.platform;

import com.ryankshah.skyrimcraft.capability.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.platform.services.IPlatformHelper;
import net.minecraft.nbt.CompoundTag;
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
        return player == null ? new Character() : CharacterCapability.get(player);
    }

    @Override
    public void setCharacterData(Player player, Character characterData) {
        player.getCapability(CharacterCapability.CAPABILITY).orElse(new CharacterCapability()).setCharacter(characterData);
    }

    @Override
    public ExtraCharacter getExtraCharacter(Player player) {
        return player == null ? new ExtraCharacter() : ExtraCharacterCapability.get(player);
    }

    @Override
    public void setExtraCharacterData(Player player, ExtraCharacter characterData) {
        player.getCapability(ExtraCharacterCapability.CAPABILITY).orElse(new ExtraCharacterCapability()).setCharacter(characterData);
    }

    @Override
    public LevelUpdates getLevelUpdates(Player player) {
        return player == null ? new LevelUpdates() : LevelUpdatesCapability.get(player);
    }

    @Override
    public void setLevelUpdates(Player player, LevelUpdates levelUpdates) {
        player.getCapability(LevelUpdatesCapability.CAPABILITY).orElse(new LevelUpdatesCapability()).setLevelUpdates(levelUpdates);
    }

    @Override
    public StatIncreases getStatIncreases(Player player) {
        return player == null ? new StatIncreases() : StatIncreasesCapability.get(player);
    }

    @Override
    public void setStatIncreases(Player player, StatIncreases statIncreases) {
        player.getCapability(StatIncreasesCapability.CAPABILITY).orElse(new StatIncreasesCapability()).setStatIncreases(statIncreases);
    }

    @Override
    public PlayerQuests getQuests(Player player) {
        return player == null ? new PlayerQuests() : PlayerQuestsCapability.get(player);
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
}