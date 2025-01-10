package com.ryankshah.skyrimcraft.network.skill;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.skill.Skill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillWrapper;
import com.ryankshah.skyrimcraft.network.character.AddToLevelUpdates;
import commonnetwork.api.Dispatcher;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public record AddXpToSkill(ResourceKey<Skill> skill, int baseXp)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "addxptoskill");

    public AddXpToSkill(final FriendlyByteBuf buffer) {
        this(buffer.readResourceKey(SkillRegistry.SKILLS_KEY), buffer.readInt());
    }

    public static AddXpToSkill decode(FriendlyByteBuf buf) {
        return new AddXpToSkill(buf.readResourceKey(SkillRegistry.SKILLS_KEY), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeResourceKey(SkillRegistry.SKILLS_KEY);
        buf.writeInt(baseXp);
    }

    public static void handle(PacketContext<AddXpToSkill> context) {
        if(context.side() == Side.CLIENT)
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<AddXpToSkill> context) {
        ServerPlayer player = context.sender();
        Character character = Character.get(player);
        AddXpToSkill data = context.message();
        SkillWrapper skill = character.getSkill(SkillRegistry.SKILLS_REGISTRY.get().get(data.skill).getID());

        int oldSkillLevel = Integer.valueOf(skill.getLevel());
        character.giveExperiencePoints(skill.getID(), data.baseXp);
//                        skill.giveExperiencePoints(data.baseXp);

        final AddXpToSkill sendToClient = new AddXpToSkill(data.skill, data.baseXp);
        Dispatcher.sendToClient(sendToClient, player);
//                        PacketDistributor.PLAYER.with(serverPlayer).send(sendToClient);

        if(skill.getLevel() > oldSkillLevel) {
            // The skill has leveled up, so send packet to client to add to the skyrim ingame gui levelUpdates list.
            final AddToLevelUpdates levelUpdates = new AddToLevelUpdates(skill.getName(), skill.getLevel(), 200);
            Dispatcher.sendToServer(levelUpdates);
//                            PacketDistributor.SERVER.noArg().send(levelUpdates); //.with(serverPlayer).send(levelUpdates);

            int level = character.getCharacterLevel();
            int totalXp = character.getCharacterTotalXp();
            int newLevel = (int)Math.floor(-2.5 + Math.sqrt(8 * totalXp + 1225) / 10);

            // TODO: add triggers in AdvancementTriggersRegistry and fix this!
//            if(newLevel == 10)
//                AdvancementTriggersRegistry.LEVEL_UP.get().trigger(player, Optional.of(skill), Optional.of(10));

            character.setCharacterTotalXp(totalXp + skill.getLevel());
            if(newLevel > level) {
                character.setCharacterLevel(newLevel);
                character.addLevelPerkPoint();
                final AddToLevelUpdates charLevelUpdates = new AddToLevelUpdates("characterLevel", character.getCharacterLevel(), 200);
                Dispatcher.sendToClient(charLevelUpdates, player);
//                                PacketDistributor.PLAYER.with(serverPlayer).send(charLevelUpdates);
            }
        }
    }

    public static void handleClient(PacketContext<AddXpToSkill> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            Character character = Character.get(player);
            SkillWrapper skill = character.getSkill(SkillRegistry.SKILLS_REGISTRY.get().get(context.message().skill).getID());

            int oldSkillLevel = skill.getLevel();
            character.giveExperiencePoints(skill.getID(), context.message().baseXp);

            if(skill.getLevel() > oldSkillLevel) {
                int level = character.getCharacterLevel();
                int totalXp = character.getCharacterTotalXp();
                int newLevel = (int)Math.floor(-2.5 + Math.sqrt(8 * totalXp + 1225) / 10);

                character.setCharacterTotalXp(totalXp + skill.getLevel());
                if(newLevel > level) {
                    character.setCharacterLevel(newLevel);
                    character.addLevelPerkPoint();
                }
            }
        });
    }
}
