package com.ryankshah.skyrimcraft.registry;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.advancement.LearnSpellTrigger;
import com.ryankshah.skyrimcraft.advancement.LevelUpTrigger;
import com.ryankshah.skyrimcraft.registration.RegistrationProvider;
import com.ryankshah.skyrimcraft.registration.RegistryObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.advancements.packs.VanillaAdvancementProvider;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;

import java.util.function.Supplier;

public class AdvancementTriggersRegistry
{
    public static void init() {}

    public static final LearnSpellTrigger LEARN_SPELL = (LearnSpellTrigger) CriteriaTriggers.register(new LearnSpellTrigger());
    public static final LevelUpTrigger LEVEL_UP = (LevelUpTrigger) CriteriaTriggers.register(new LevelUpTrigger());

//    public static final RegistrationProvider<CriterionTrigger<?>> TRIGGERS = RegistrationProvider.get(BuiltInRegistries.TRIGGER_TYPE, Constants.MODID);
//    public static final RegistryObject<LearnSpellTrigger> LEARN_SPELL = TRIGGERS.register("learn_spell", LearnSpellTrigger::new);
//    public static final RegistryObject<LevelUpTrigger> LEVEL_UP = TRIGGERS.register("level_up", LevelUpTrigger::new);
//    public static final DeferredHolder<CriterionTrigger<?>, BodyPartShotByArrowTrigger> BODY_PART_SHOT_BY_ARROW = TRIGGERS.register("body_part_shot_by_arrow", BodyPartShotByArrowTrigger::new);
}
