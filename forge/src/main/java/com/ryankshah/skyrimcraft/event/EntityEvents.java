package com.ryankshah.skyrimcraft.event;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.SkyrimcraftCommon;
import com.ryankshah.skyrimcraft.character.attachment.Character;
import com.ryankshah.skyrimcraft.character.magic.SpellRegistry;
import com.ryankshah.skyrimcraft.character.skill.SkillRegistry;
import com.ryankshah.skyrimcraft.effect.ModEffects;
import com.ryankshah.skyrimcraft.goal.DismayGoal;
import com.ryankshah.skyrimcraft.goal.UndeadFleeGoal;
import com.ryankshah.skyrimcraft.item.SkyrimArmor;
import com.ryankshah.skyrimcraft.item.SkyrimTwoHandedSword;
import com.ryankshah.skyrimcraft.network.character.AddToTargetingEntities;
import com.ryankshah.skyrimcraft.network.character.UpdateCurrentTarget;
import com.ryankshah.skyrimcraft.network.skill.AddXpToSkill;
import com.ryankshah.skyrimcraft.registry.EntityRegistry;
import commonnetwork.api.Dispatcher;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;

@Mod.EventBusSubscriber(modid = Constants.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntityEvents {
    private static List<EntityType<?>> pickPocketableEntities = StreamSupport.stream(SkyrimcraftCommon.getPickpocketableEntities().spliterator(), false).toList();

    @SubscribeEvent
    public static void entitySetAttackTarget(LivingDamageEvent event) {
        if (event.getSource().getDirectEntity() != null && event.getSource().getDirectEntity() instanceof ServerPlayer && event.getAmount() > 0) {
            ServerPlayer player = (ServerPlayer) event.getSource().getDirectEntity();
            Character character = Character.get(player);
            List<Integer> targetingEntities = character.getTargets();
            if (!targetingEntities.contains(event.getEntity().getId()) //&& cap.getTargetingEntities().size() <= 10
                    && event.getEntity().isAlive()) {
                targetingEntities.add(event.getEntity().getId());
                final AddToTargetingEntities targets = new AddToTargetingEntities(event.getEntity().getId());
                character.addTarget(event.getEntity().getId());
                Dispatcher.sendToClient(targets, player);
//                PacketDistributor.PLAYER.with(player).send(targets);
            }
        }

        if (event.getSource().getDirectEntity() != null && event.getEntity() instanceof ServerPlayer player && event.getAmount() > 0) {
            Character character = Character.get(player);
            final UpdateCurrentTarget currentTarget = new UpdateCurrentTarget(event.getSource().getDirectEntity().getId());
            character.addTarget(event.getSource().getDirectEntity().getId());
            Dispatcher.sendToClient(currentTarget, player);
//            PacketDistributor.PLAYER.with(player).send(currentTarget);
        }

        if (event.getSource().getEntity() instanceof Player) {
            Player playerEntity = (Player) event.getSource().getEntity();
            Character character = Character.get(playerEntity);

            if (event.getEntity() != null) {
                if (playerEntity.hasEffect(ModEffects.ETHEREAL.get()))
                    playerEntity.removeEffect(ModEffects.ETHEREAL.get());

                if (playerEntity.hasEffect(ModEffects.FLAME_CLOAK.get()) && character.getSpellCooldown(SpellRegistry.ANCESTORS_WRATH.get()) > 0) {
                    event.getEntity().setRemainingFireTicks(2 * 20);
                }

                if (playerEntity.isCrouching() && !playerEntity.canBeSeenAsEnemy()) {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.SNEAK.get()).get(), (int) event.getAmount());
                    Dispatcher.sendToServer(xpToSkill);
//                    PacketDistributor.SERVER.noArg().send(xpToSkill);
                }

                if (playerEntity.getMainHandItem().getItem() instanceof ProjectileWeaponItem) {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.ARCHERY.get()).get(), (int) event.getAmount());
                    Dispatcher.sendToServer(xpToSkill);
//                    PacketDistributor.SERVER.noArg().send(xpToSkill);
                } else if (playerEntity.getMainHandItem().getItem() instanceof SwordItem) {
                    if (playerEntity.getMainHandItem().getItem() instanceof SkyrimTwoHandedSword) {
                        final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.TWO_HANDED.get()).get(), (int) event.getAmount());
                        Dispatcher.sendToServer(xpToSkill);
//                        PacketDistributor.SERVER.noArg().send(xpToSkill);
                    } else {
                        final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.ONE_HANDED.get()).get(), (int) event.getAmount());
                        Dispatcher.sendToServer(xpToSkill);
//                        PacketDistributor.SERVER.noArg().send(xpToSkill);
                    }
                }

//                } else if(playerEntity.getMainHandItem().getItem() instanceof SkyrimTwoHandedWeapon) {
//                    Networking.sendToServer(new PacketAddXpToSkillOnServer(SkillRegistry.TWO_HANDED.getID(), (int)event.getAmount()));
//                }

                if (event.getEntity().isAlive()) {
                    final UpdateCurrentTarget target = new UpdateCurrentTarget(event.getEntity().getId());
                    Dispatcher.sendToServer(target);
//                    PacketDistributor.SERVER.noArg().send(target);
                } else {
                    final UpdateCurrentTarget target = new UpdateCurrentTarget(-1);
                    Dispatcher.sendToServer(target);
//                    PacketDistributor.SERVER.noArg().send(target);
                }
            }
        } else if (event.getEntity() instanceof Player) {
            Player playerEntity = (Player) event.getEntity();
            if (playerEntity.isDamageSourceBlocked(event.getSource())) {
                final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.BLOCK.get()).get(), SkillRegistry.BASE_BLOCK_XP);
                Dispatcher.sendToServer(xpToSkill);
//                PacketDistributor.SERVER.noArg().send(xpToSkill);
            }

            if (playerEntity.getArmorValue() > 0) {
                // minecraft armors will default to light armor for now.
                // TODO: Check all the slots and check the type of armor (perhaps leather, iron and gold will be
                //       classed as "light armor" with netherite as the heavy armor for default mc.
                //       All other mod armors outside of skyrim will be classed as light armor. Perhaps instead,
                //       there may be a different way we can define these...
                AtomicInteger heavySlots = new AtomicInteger();
                for (Iterator<ItemStack> it = playerEntity.getArmorSlots().iterator(); it.hasNext(); ) {
                    ItemStack itemStack = it.next();
                    if (itemStack.getItem() instanceof ArmorItem) {
                        ArmorItem item = (ArmorItem) itemStack.getItem();
                        if ((item instanceof SkyrimArmor && ((SkyrimArmor) item).isHeavy()) || item.getMaterial() == ArmorMaterials.NETHERITE)
                            heavySlots.set(heavySlots.get() + 1);
                    }
                }

                if (heavySlots.get() >= 3) {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.HEAVY_ARMOR.get()).get(), (int) (playerEntity.getArmorValue() * playerEntity.getArmorCoverPercentage()));
                    Dispatcher.sendToServer(xpToSkill);
//                    PacketDistributor.SERVER.noArg().send(xpToSkill);
                } else {
                    final AddXpToSkill xpToSkill = new AddXpToSkill(SkillRegistry.SKILLS_REGISTRY.get().getResourceKey(SkillRegistry.LIGHT_ARMOR.get()).get(), (int) (playerEntity.getArmorValue() * playerEntity.getArmorCoverPercentage()));
                    Dispatcher.sendToServer(xpToSkill);
//                    PacketDistributor.SERVER.noArg().send(xpToSkill);
                }
            }
        }
    }

    /**
     * Used to add panic AI task for undead mobs (for UndeadFleeEffect to activate)
     */
    @SubscribeEvent
    public static void entityJoin(EntityJoinLevelEvent event) {
        if (pickPocketableEntities.contains(event.getEntity().getType())) {
            event.getEntity().addTag(EntityRegistry.PICKPOCKET_TAG);
        }

        if (event.getEntity() instanceof PathfinderMob mob) {
            mob.goalSelector.addGoal(0, new DismayGoal(mob, 16.0F, 0.8D, 1.33D));
        }

        if (event.getEntity() instanceof Monster) {
            Monster monsterEntity = (Monster) event.getEntity();
            if (monsterEntity.getType().is(EntityTypeTags.SKELETONS)) { // TODO: make this undead
                monsterEntity.goalSelector.addGoal(0, new UndeadFleeGoal(monsterEntity, 16.0F, 0.8D, 1.33D));
            }
        }
    }

    @SubscribeEvent
    public static void onEntityHit(AttackEntityEvent event) {
    }
}