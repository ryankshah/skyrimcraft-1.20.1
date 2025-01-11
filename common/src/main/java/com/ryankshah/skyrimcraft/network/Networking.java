package com.ryankshah.skyrimcraft.network;

import com.ryankshah.skyrimcraft.network.character.*;
import com.ryankshah.skyrimcraft.network.recipe.FinishAlchemyRecipe;
import com.ryankshah.skyrimcraft.network.recipe.FinishForgeRecipe;
import com.ryankshah.skyrimcraft.network.recipe.FinishOvenRecipe;
import com.ryankshah.skyrimcraft.network.skill.AddXpToSkill;
import com.ryankshah.skyrimcraft.network.skill.HandlePickpocket;
import com.ryankshah.skyrimcraft.network.spell.*;
import commonnetwork.api.Network;

public class Networking
{
    public static void load() {
        Network.registerPacket(AddToLevelUpdates.TYPE, AddToLevelUpdates.class, AddToLevelUpdates::encode, AddToLevelUpdates::decode, AddToLevelUpdates::handle);
        Network.registerPacket(AddXpToSkill.TYPE, AddXpToSkill.class,AddXpToSkill::encode, AddXpToSkill::decode, AddXpToSkill::handle);
        Network.registerPacket(HandlePickpocket.TYPE, HandlePickpocket.class, HandlePickpocket::encode, HandlePickpocket::decode, HandlePickpocket::handle);
        Network.registerPacket(AddToTargetingEntities.TYPE, AddToTargetingEntities.class, AddToTargetingEntities::encode, AddToTargetingEntities::decode, AddToTargetingEntities::handle);
        Network.registerPacket(RemoveFromTargetingEntities.TYPE, RemoveFromTargetingEntities.class, RemoveFromTargetingEntities::encode, RemoveFromTargetingEntities::decode, RemoveFromTargetingEntities::handle);
        Network.registerPacket(UpdateCurrentTarget.TYPE, UpdateCurrentTarget.class, UpdateCurrentTarget::encode, UpdateCurrentTarget::decode, UpdateCurrentTarget::handle);
        Network.registerPacket(AddToCompassFeatures.TYPE, AddToCompassFeatures.class, AddToCompassFeatures::encode, AddToCompassFeatures::decode, AddToCompassFeatures::handle);
        Network.registerPacket(AddToKnownSpells.TYPE, AddToKnownSpells.class, AddToKnownSpells::encode, AddToKnownSpells::decode, AddToKnownSpells::handle);
        Network.registerPacket(UpdateSelectedSpell.TYPE, UpdateSelectedSpell.class, UpdateSelectedSpell::encode, UpdateSelectedSpell::decode, UpdateSelectedSpell::handle);
        Network.registerPacket(UpdateShoutCooldown.TYPE, UpdateShoutCooldown.class, UpdateShoutCooldown::encode, UpdateShoutCooldown::decode, UpdateShoutCooldown::handle);
        Network.registerPacket(CastSpell.TYPE, CastSpell.class, CastSpell::encode, CastSpell::decode, CastSpell::handle);
        Network.registerPacket(FastTravel.TYPE, FastTravel.class, FastTravel::encode, FastTravel::decode, FastTravel::handle);

        Network.registerPacket(DetectLife.TYPE, DetectLife.class, DetectLife::encode, DetectLife::decode, DetectLife::handle);
        Network.registerPacket(UpdateTelekinesisItem.TYPE, UpdateTelekinesisItem.class, UpdateTelekinesisItem::encode, UpdateTelekinesisItem::decode, UpdateTelekinesisItem::handle);

        Network.registerPacket(ReplenishMagicka.TYPE, ReplenishMagicka.class, ReplenishMagicka::encode, ReplenishMagicka::decode, ReplenishMagicka::handle);
        Network.registerPacket(ConsumeMagicka.TYPE, ConsumeMagicka.class, ConsumeMagicka::encode, ConsumeMagicka::decode, ConsumeMagicka::handle);
        Network.registerPacket(UpdateMagicka.TYPE, UpdateMagicka.class, UpdateMagicka::encode, UpdateMagicka::decode, UpdateMagicka::handle);
        Network.registerPacket(OpenCharacterCreationScreen.TYPE, OpenCharacterCreationScreen.class, OpenCharacterCreationScreen::encode, OpenCharacterCreationScreen::decode, OpenCharacterCreationScreen::handle);

        Network.registerPacket(CreateCharacter.TYPE, CreateCharacter.class, CreateCharacter::encode, CreateCharacter::decode, CreateCharacter::handle);
        Network.registerPacket(UpdateCharacter.TYPE, UpdateCharacter.class, UpdateCharacter::encode, UpdateCharacter::decode, UpdateCharacter::handle);
        Network.registerPacket(UpdateExtraCharacter.TYPE, UpdateExtraCharacter.class, UpdateExtraCharacter::encode, UpdateExtraCharacter::decode, UpdateExtraCharacter::handle);
        Network.registerPacket(UpdateLevelUpdates.TYPE, UpdateLevelUpdates.class, UpdateLevelUpdates::encode, UpdateLevelUpdates::decode, UpdateLevelUpdates::handle);
        Network.registerPacket(UpdateStatIncreases.TYPE, UpdateStatIncreases.class, UpdateStatIncreases::encode, UpdateStatIncreases::decode, UpdateStatIncreases::handle);
        Network.registerPacket(UpdatePlayerQuests.TYPE, UpdatePlayerQuests.class, UpdatePlayerQuests::encode, UpdatePlayerQuests::decode, UpdatePlayerQuests::handle);
        Network.registerPacket(FinishAlchemyRecipe.TYPE, FinishAlchemyRecipe.class, FinishAlchemyRecipe::encode, FinishAlchemyRecipe::decode, FinishAlchemyRecipe::handle);
        Network.registerPacket(FinishOvenRecipe.TYPE, FinishOvenRecipe.class, FinishOvenRecipe::encode, FinishOvenRecipe::decode, FinishOvenRecipe::handle);
        Network.registerPacket(FinishForgeRecipe.TYPE, FinishForgeRecipe.class, FinishForgeRecipe::encode, FinishForgeRecipe::decode, FinishForgeRecipe::handle);
    }
}