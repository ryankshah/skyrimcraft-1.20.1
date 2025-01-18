package com.ryankshah.skyrimcraft.advancement;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.Skill;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillWrapper;
import com.ryankshah.skyrimcraft.registry.AdvancementTriggersRegistry;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;
import java.util.Optional;

public final class LevelUpTrigger extends SimpleCriterionTrigger<LevelUpTrigger.Instance>
{
    private static final ResourceLocation triggerId = new ResourceLocation(Constants.MODID, "level_up");

    @Override
    public ResourceLocation getId() {
        return triggerId;
    }

    @Override
    public LevelUpTrigger.Instance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext conditions) {
        Skill skill = null;
        int level = 0;

        if (json.has("skill")) {
            ResourceKey<Skill> skillId = ResourceKey.create(SkillRegistry.SKILLS_KEY, new ResourceLocation(GsonHelper.getAsString(json, "skill")));
            skill = SkillRegistry.SKILLS_REGISTRY.get().get(skillId);

            if (skill == null)
                throw new IllegalArgumentException("Invalid Skill ID: '" + skillId + "'");
        }

        if(json.has("level"))
            level = GsonHelper.getAsInt(json, "level");

        return new LevelUpTrigger.Instance(skill, level);
    }

    public void trigger(ServerPlayer player, Skill skill, int level) {
        trigger(player, instance -> instance.matches(skill, level));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Skill skill;
        private final int level;

        public Instance(@Nullable Skill skill, int level, ContextAwarePredicate playerPredicate) {
            super(triggerId, playerPredicate);

            this.skill = skill;
            this.level = level;
        }

        public Instance(@Nullable Skill skill, int level) {
            this(skill, level, ContextAwarePredicate.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject obj = super.serializeToJson(conditions);

            if (skill != null)
                obj.addProperty("skill", SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(skill).toString());

            obj.addProperty("level", level);

            return obj;
        }

        public boolean matches(Skill skill, int level) {
            return (this.skill == null || this.skill.getID() == skill.getID()) && this.level == level;
        }
    }
}