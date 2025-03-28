package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.capability.*;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.attachment.ExtraCharacter;
import com.ryankshah.skyrimcraft.character.lockpicking.LockableHandler;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.network.character.AddToCompassFeatures;
import com.ryankshah.skyrimcraft.network.character.OpenCharacterCreationScreen;
import com.ryankshah.skyrimcraft.network.skill.AddXpToSkill;
import com.ryankshah.skyrimcraft.network.spell.UpdateShoutCooldown;
import com.ryankshah.skyrimcraft.registry.AttributeRegistry;
import com.ryankshah.skyrimcraft.registry.TagsRegistry;
import com.ryankshah.skyrimcraft.util.CompassFeature;
import commonnetwork.api.Dispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.TradeWithVillagerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerEvents
{
    public static boolean flag = false;
    private static int magickaTickCounter = 0;

    @SubscribeEvent
    public static void onKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if(Minecraft.getInstance().player.hasEffect(ModEffects.PARALYSIS.get())) {
            event.setSwingHand(false);
            event.setCanceled(true);
        }
    }

    // TODO: There is no such hook, we need to make this patch for neo and/or fabric, or get someone to do it.
//    @SubscribeEvent
//    public static void onEnchant(PlayerEvent.ItemCraftedEvent event) {
//        if(event.getInventory() instanceof EnchantmentMenu enchantmentMenu) {
//            // TODO: Check if event.getEnchantLevel() is suitable for XP amount (or do we multiply by some base)
//            final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.getResourceKey(SkillRegistry.ENCHANTING.get()).get(), (int)enchantmentMenu.getEnchantLevel() * SkillRegistry.BASE_ENCHANT_XP);
//            PacketDistributor.SERVER.noArg().send(xpToSkill);
//        }
//    }

    // TODO: In Fabric, we need to mixin to villager trade succession somewhere to do this
    @SubscribeEvent
    public static void onTradeWithVillager(TradeWithVillagerEvent event) {
        final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.SPEECH.get()).get(), event.getMerchantOffer().getXp() * SkillRegistry.BASE_SPEECH_XP);
        Dispatcher.sendToServer(xpToSkill);
//        PacketDistributor.SERVER.noArg().send(xpToSkill);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START)
            return;

        Player playerEntity = event.player;
        if (!playerEntity.isAlive() || playerEntity == null)
            return;

//        if (event.phase == TickEvent.Phase.END) {

//            Minecraft.getInstance().getConnection().registryAccess().registry(QuestRegistry.QUESTS_REGISTRY_KEY).ifPresent(
//                    registry -> System.out.println(registry.stream().toList())
//            );
        Character character = Character.get(playerEntity);
        ExtraCharacter extraCharacter = ExtraCharacter.get(playerEntity);
        if(playerEntity.level().isClientSide) {
            if (!character.getSpellsOnCooldown().isEmpty()) {
                for (Map.Entry<Spell, Float> entry : character.getSpellsOnCooldown().entrySet()) {
                    if (entry.getValue() <= 0f) {
                        final UpdateShoutCooldown updateShoutCooldown = new UpdateShoutCooldown(SpellRegistry.SPELLS_REGISTRY.get().getResourceKey(entry.getKey()).get(), 0f);
                        Dispatcher.sendToServer(updateShoutCooldown);
//                            PacketDistributor.SERVER.noArg().send(updateShoutCooldown);
                    }
                    if (entry.getValue() > 0f) {
                        float cooldown = character.getSpellCooldown(entry.getKey());
                        final UpdateShoutCooldown updateShoutCooldown = new UpdateShoutCooldown(SpellRegistry.SPELLS_REGISTRY.get().getResourceKey(entry.getKey()).get(), cooldown - 0.05f);
                        Dispatcher.sendToServer(updateShoutCooldown);
//                            PacketDistributor.SERVER.noArg().send(updateShoutCooldown);
                    }
                }
            }
        }

        if ((playerEntity.hasEffect(ModEffects.SPECTRAL.get()) || playerEntity.hasEffect(ModEffects.ETHEREAL.get())) && !flag) {
            flag = true;
            playerEntity.setInvisible(true);
            flag = false;
        } else {
            playerEntity.setInvisible(playerEntity.hasEffect(MobEffects.INVISIBILITY));
        }

        if (!playerEntity.hasEffect(ModEffects.MAGICKA_REGEN.get()))
            playerEntity.getAttribute(AttributeRegistry.MAGICKA_REGEN.get()).removeModifiers();

        if (character.getMagicka() < character.getMaxMagicka()) {
            if (playerEntity.tickCount % 20 == 0) {
                // If in combat, regenerate 1% of max magicka, else 3%
                if (character.getCurrentTarget() != -1)
                    character.setMagicka(character.getMagicka() + ((0.01f * character.getMaxMagicka()) * character.getMagickaRegenModifier()));
                else
                    character.setMagicka(character.getMagicka() + ((0.03f * character.getMaxMagicka()) * character.getMagickaRegenModifier()));
            }
        }

        // check ethereal
        if (!playerEntity.hasEffect(ModEffects.ETHEREAL.get())) {
            if (playerEntity.isInvulnerable() && (!playerEntity.isCreative() || !playerEntity.isSpectator()))
                playerEntity.setInvulnerable(false);
        }

        if (extraCharacter.isVampire() && (!playerEntity.level().isNight() || playerEntity.level().getBrightness(LightLayer.BLOCK, playerEntity.blockPosition()) < 10)) {
            // Increased damage from sunlight for infected players
            playerEntity.hurt(playerEntity.damageSources().onFire(), 2);
        }

        if(Minecraft.getInstance().getConnection() == null)
            return;

        if (playerEntity instanceof ServerPlayer && playerEntity.level().isLoaded(playerEntity.blockPosition())) { //&& event.side == LogicalSide.SERVER) {
            ServerPlayer player = (ServerPlayer) playerEntity;
            ServerLevel level = (ServerLevel) player.level();

            List<ResourceKey<Structure>> structuresList = List.of(BuiltinStructures.VILLAGE_DESERT, BuiltinStructures.VILLAGE_TAIGA,
                    BuiltinStructures.VILLAGE_PLAINS, BuiltinStructures.VILLAGE_SNOWY, BuiltinStructures.VILLAGE_SAVANNA, BuiltinStructures.SHIPWRECK, BuiltinStructures.SHIPWRECK_BEACHED,
                    BuiltinStructures.FORTRESS, BuiltinStructures.MINESHAFT, BuiltinStructures.MINESHAFT_MESA);

            List<TagKey<Structure>> structureTags = List.of(
                    StructureTags.VILLAGE, StructureTags.MINESHAFT, TagsRegistry.StructureTagsInit.NETHER_FORTRESS, StructureTags.SHIPWRECK,
                    TagsRegistry.StructureTagsInit.GIANT_CAMP, TagsRegistry.StructureTagsInit.SHOUT_WALL, TagsRegistry.StructureTagsInit.WATCHTOWER,
                    TagsRegistry.StructureTagsInit.PYRAMID, TagsRegistry.StructureTagsInit.RUINED_PORTAL
            );

            for (TagKey<Structure> structure : structureTags) {
                BlockPos featureStartPos = locateFeatureStartChunkFromPlayerBlockPos(level, player.blockPosition(), structure);
                if (featureStartPos != null && structure != null) {
                    System.out.println("Found Structure: " + structure.toString());
                    List<CompassFeature> playerCompassFeatures = character.getCompassFeatures();
                    CompassFeature compassFeature = new CompassFeature(UUID.randomUUID().toString(), structure, featureStartPos);
                    if (playerCompassFeatures.isEmpty() || playerCompassFeatures.stream().noneMatch(feature -> feature.equals(compassFeature))) {
                        final AddToCompassFeatures features = new AddToCompassFeatures(compassFeature.getId(), compassFeature.getFeature().location(), compassFeature.getBlockPos());
                        Dispatcher.sendToServer(features);
                    }
                }
            }
        }
    }

    private static BlockPos locateFeatureStartChunkFromPlayerBlockPos(ServerLevel world, BlockPos pos, TagKey<Structure> feature) {
        // use 2 since based on min spacing, or we can use 7 in case user makes village.json spacing at every chunk
        BlockPos blockpos1 = world.findNearestMapStructure(feature, pos, 2, true);
        if (blockpos1 != null) {
            return blockpos1;
        } else {
            return null;
        }
    }

    @SubscribeEvent
    public static void playerSmelt(PlayerEvent.ItemSmeltedEvent event) {
        final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.SMITHING.get()).get(), event.getSmelting().getCount() * SkillRegistry.BASE_SMITHING_XP);
        Dispatcher.sendToServer(xpToSkill);
//        PacketDistributor.SERVER.noArg().send(xpToSkill);
    }

    // Open the character creation screen if first login / world created
    @SubscribeEvent
    public static void playerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if(player instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer) player;
            Character character = Character.get(serverPlayer);
            if (!character.getHasSetup()) {
                final OpenCharacterCreationScreen packet = new OpenCharacterCreationScreen(character.getHasSetup());
                Dispatcher.sendToClient(packet, serverPlayer);
//                PacketDistributor.PLAYER.with(serverPlayer).send(packet);
            }
        }
    }

    @SubscribeEvent
    public static void attachCaps(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            CharacterCapability characterCapability = new CharacterCapability();
            event.addCapability(CharacterCapability.ID, characterCapability);
//            event.addListener(characterCapability::onInvalidate);

            ExtraCharacterCapability extraCharacterCapability = new ExtraCharacterCapability();
            event.addCapability(ExtraCharacterCapability.ID, extraCharacterCapability);
//            event.addListener(extraCharacterCapability::onInvalidate);

            StatIncreasesCapability siC = new StatIncreasesCapability();
            event.addCapability(StatIncreasesCapability.ID, siC);
//            event.addListener(siC::onInvalidate);

            LevelUpdatesCapability lUC = new LevelUpdatesCapability();
            event.addCapability(LevelUpdatesCapability.ID, lUC);
//            event.addListener(lUC::onInvalidate);

            PlayerQuestsCapability pQC = new PlayerQuestsCapability();
            event.addCapability(PlayerQuestsCapability.ID, pQC);
//            event.addListener(pQC::onInvalidate);

            ConjureFamiliarCapability cFC = new ConjureFamiliarCapability();
            event.addCapability(ConjureFamiliarCapability.ID, cFC);
//            event.addListener(cFC::onInvalidate);

            SelectionCapability sC = new SelectionCapability();
            event.addCapability(SelectionCapability.ID, sC);
        }
    }

    @SubscribeEvent
    public static void attachCapabilitiesToWorld(AttachCapabilitiesEvent<Level> e) {
        LockableHandlerCapability lHC = new LockableHandlerCapability(e.getObject());
        e.addCapability(LockableHandlerCapability.ID, lHC);
    }

    @SubscribeEvent
    public static void attachCapabilitiesToChunk(AttachCapabilitiesEvent<LevelChunk> e) {
        LockableStorageCapability lHC = new LockableStorageCapability(e.getObject());
        e.addCapability(LockableStorageCapability.ID, lHC);
    }

}