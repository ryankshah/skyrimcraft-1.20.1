package com.ryankshah.skyrimcraft.advancement;

import com.google.gson.JsonObject;
import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.magic.Spell;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nullable;

public final class LearnSpellTrigger extends SimpleCriterionTrigger<LearnSpellTrigger.Instance>
{
    private static final ResourceLocation triggerId = new ResourceLocation(Constants.MODID, "learn_spell");

    @Override
    public ResourceLocation getId() {
        return triggerId;
    }

    @Override
    public Instance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext conditions) {
        Spell spell = null;

        if (json.has("spell")) {
            ResourceKey<Spell> spellId = ResourceKey.create(SpellRegistry.SPELLS_KEY, new ResourceLocation(GsonHelper.getAsString(json, "spell")));
            spell = SpellRegistry.SPELLS_REGISTRY.get().get(spellId);

            if (spell == null)
                throw new IllegalArgumentException("Invalid Spell ID: '" + spellId + "'");
        }

        return new Instance(spell);
    }

    public void trigger(ServerPlayer player, Spell spell) {
        trigger(player, instance -> instance.matches(spell));
    }

//    public static LearnSpellTriggerInstance onLearn(@Nullable ContextAwarePredicate conditions, @Nullable Holder<Spell> spell) {
//        return AdvancementTriggersRegistry.LEARN_SPELL.get().createCriterion(new LearnSpellTriggerInstance(Optional.ofNullable(conditions), spell != null ? Optional.of(spell) : Optional.empty()));
//    }
//
//    public static LearnSpellTriggerInstance onLearn(@Nullable Holder<Spell> spell) {
//        return onLearn(null, spell);
//    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        @Nullable
        private final Spell spell;

        public Instance(@Nullable Spell spell, ContextAwarePredicate playerPredicate) {
            super(triggerId, playerPredicate);

            this.spell = spell;
        }

        public Instance(@Nullable Spell spell) {
            this(spell, ContextAwarePredicate.ANY);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext conditions) {
            JsonObject obj = super.serializeToJson(conditions);

            if (spell != null)
                obj.addProperty("spell", SpellRegistry.SPELLS_REGISTRY.get().getResourceKey(spell).get().location().toString());

            return obj;
        }

        public boolean matches(Spell spell) {
            return (this.spell == null || this.spell == spell);
        }
    }
}