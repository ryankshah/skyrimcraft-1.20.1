package com.ryankshah.skyrimcraft.platform.services;

import com.ryankshah.skyrimcraft.character.attachment.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableStorage;
import com.ryankshah.skyrimcraft.character.lockpicking.Selection;
import com.ryankshah.skyrimcraft.util.Lockable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.function.Consumer;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {

        return isDevelopmentEnvironment() ? "development" : "production";
    }

    Character getCharacter(Player player);
    void setCharacterData(Player player, Character characterData);

    ExtraCharacter getExtraCharacter(Player player);
    void setExtraCharacterData(Player player, ExtraCharacter characterData);

    LevelUpdates getLevelUpdates(Player player);
    void setLevelUpdates(Player player, LevelUpdates levelUpdates);

    StatIncreases getStatIncreases(Player player);
    void setStatIncreases(Player player, StatIncreases statIncreases);

    PlayerQuests getQuests(Player player);
    void setQuestData(Player player, PlayerQuests playerQuests);

    boolean doesEntityHavePersistentData(LivingEntity entity, String id);
    void setEntityPersistentData(LivingEntity entity, String id, long value);

    LockableHandler getLockableHandler(Level level);
    void setLockableHandler(Level level, LockableHandler handler);

    LockableStorage getLockableStorage(LevelChunk chunk);
    void setLockableStorage(LevelChunk chunk, LockableStorage storage);

    Selection getSelection(Player player);
    void setSelection(Player player, Selection selection);

    void openLockpickingMenu(ServerPlayer player, MenuProvider provider, InteractionHand hand, Lockable lkb, Consumer<FriendlyByteBuf> buf);
}