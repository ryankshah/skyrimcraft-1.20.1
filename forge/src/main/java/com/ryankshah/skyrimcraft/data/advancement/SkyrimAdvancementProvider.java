package com.ryankshah.skyrimcraft.data.advancement;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class SkyrimAdvancementProvider extends ForgeAdvancementProvider {
    /**
     * Constructs an advancement provider using the generators to write the
     * advancements to a file.
     *
     * @param output             the target directory of the data generator
     * @param registries         a future of a lookup for registries and their objects
     * @param existingFileHelper a helper used to find whether a file exists
     * @param subProviders       the generators used to create the advancements
     */
    public SkyrimAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper, List<ForgeAdvancementProvider.AdvancementGenerator> subProviders) {
        super(output, registries, existingFileHelper, subProviders);
    }

    public static class SkyrimAdvancements implements ForgeAdvancementProvider.AdvancementGenerator
    {
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
            Advancement skyrimcraft = Advancement.Builder.advancement()
                    .display(ItemRegistry.SWEET_ROLL.get(),
                            Component.literal("Skyrimcraft"),
                            Component.literal("Your adventure begins here..."),
                            new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("skyrimcraft_login", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.location().setX(MinMaxBounds.Doubles.ANY).build()))
                    .save(saver, new ResourceLocation(Constants.MODID, "root"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(skyrimcraft)
                    .display(
                            Items.ARROW,
                            Component.translatable("Take An Arrow To The Knee"),
                            Component.translatable("I used to be an adventurer like you. Then I took an arrow in the knee..."),
                            null,
                            FrameType.TASK,
                            true,
                            true,
                            false
                    )
                    // TODO: create a new predicate for checking the body part!
                    .addCriterion(
                            "take_arrow_to_knee",
                            EntityHurtPlayerTrigger.TriggerInstance.entityHurtPlayer(
                                    DamagePredicate.Builder.damageInstance()
                                            .type(DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE)))
                            )
                    )
                    .save(saver, Constants.MODID + "/arrow_to_knee");

//            Advancement spells = Advancement.Builder.advancement().parent(skyrimcraft)
//                    .display(ItemRegistry.FIREBALL_SPELLBOOK.get(), Component.literal("Spells"), Component.literal("Learned spells"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true)
//                    .addCriterion("spells_list", ImpossibleTrigger.TriggerInstance::new)
//                    .build()
//                    .save(saver, new ResourceLocation(Constants.MODID, "spell/root"), existingFileHelper);
//            Advancement shouts = Advancement.Builder.advancement().parent(skyrimcraft)
//                    .display(BlockRegistry.SHOUT_BLOCK.get(), Component.literal("Shouts"), Component.literal("Learnt shouts"), (ResourceLocation)null, FrameType.CHALLENGE, true, true, true)
//                    .addCriterion("shouts_list", ImpossibleTrigger.TriggerInstance::new).save(saver, new ResourceLocation(Constants.MODID, "shout/root"), existingFileHelper);
//
//            for(RegistryObject<Spell> spell : SpellRegistry.SPELLS.getEntries()) {
//                if(spell.get().equals(SpellRegistry.EMPTY_SPELL.get()))
//                    continue;
//
//                ItemLike provider = spell.get().getType() == Spell.SpellType.SHOUT ? BlockRegistry.SHOUT_BLOCK.get() : ItemRegistry.FIREBALL_SPELLBOOK.get();
//                Advancement adv = Advancement.Builder.advancement().parent(spell.get().getType() == Spell.SpellType.SHOUT ? shouts : spells)
//                        .display(provider, Component.literal(spell.get().getName()),
//                                Component.literal("Learn the " + spell.get().getName() + " " + (spell.get().getType() == Spell.SpellType.SHOUT ? "shout" : "spell")),
//                                (ResourceLocation)null, FrameType.CHALLENGE, true, true, true)
//                        .addCriterion("spell_learned_" + spell.get().getName().toLowerCase(Locale.ENGLISH).replace(" ", "_"), LearnSpellTrigger.onLearn(spell.asHolder()))
//                        .save(saver, new ResourceLocation(Constants.MODID, (spell.get().getType() == Spell.SpellType.SHOUT ? "shout" : "spell") + "/" + spell.get().getName().toLowerCase(Locale.ENGLISH).replace(" ", "_")), existingFileHelper);
//            }
//
//
//            // Level-Based
            Advancement combat = Advancement.Builder.advancement().parent(skyrimcraft)
                    .display(ItemRegistry.DAEDRIC_SWORD.get(), Component.literal("Combat"), Component.literal("Skyrimcraft Combat Achievements"), (ResourceLocation)null, FrameType.CHALLENGE, false, false, false)
                    .addCriterion("deal_damage", PlayerHurtEntityTrigger.TriggerInstance.playerHurtEntity()).save(saver, new ResourceLocation(Constants.MODID, "combat/root"), existingFileHelper);
//
//            Advancement reach_level_10 = Advancement.Builder.advancement().parent(combat)
//                    .display(ItemRegistry.IRON_SWORD.get(), Component.literal("Level 10"), Component.literal("Reach Combat Level 10"), (ResourceLocation)null, AdvancementType.TASK, true, true, false)
//                    .addCriterion("level_10", LevelUpTrigger.onLevelUp(Optional.of(10))).save(saver, ResourceLocation.fromNamespaceAndPath(Constants.MODID, "combat/reach_level_10"), existingFileHelper);

        }
    }
}
