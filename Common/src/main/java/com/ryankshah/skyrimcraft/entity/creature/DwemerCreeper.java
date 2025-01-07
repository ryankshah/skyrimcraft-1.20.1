package com.ryankshah.skyrimcraft.entity.creature;

import com.ryankshah.skyrimcraft.entity.ai.goal.SpiderSprintToNearestAttackableTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class DwemerCreeper extends Monster implements GeoEntity
{
    private static final EntityDataAccessor<Integer> PREV_ANIMATION_STATE = SynchedEntityData.defineId(DwemerCreeper.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(DwemerCreeper.class, EntityDataSerializers.INT);

    private MeleeAttackGoal meleeGoal;
    private WaterAvoidingRandomStrollGoal walkingGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestPlayerGoal;
//    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestAnimalGoal;

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation DUCK = RawAnimation.begin().thenLoop("duck");
    protected static final RawAnimation IDLE_DUCKED = RawAnimation.begin().thenLoop("idle_ducked");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public DwemerCreeper(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 5;

        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    @Override
    public boolean isInvulnerableTo(DamageSource pSource) {
        if (pSource.getDirectEntity() instanceof AbstractArrow) {
            return true;
        } else {
            return super.isInvulnerableTo(pSource);
        }
    }

    protected void registerGoals() {
        meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public void stop() {
                super.stop();
                DwemerCreeper.this.setAnimationState(0);
            }

            @Override
            protected void checkAndPerformAttack(LivingEntity pTarget, double huh) {
                if (this.mob.canAttack(pTarget)) {
                    if (getTicksUntilNextAttack() <= 0) {
                        this.resetAttackCooldown();
//                        this.mob.swing(InteractionHand.MAIN_HAND);
//                        if (DwemerCreeper.this.getAnimationState() != 3 || DwemerCreeper.this.getAnimationState() != 4)
//                            DwemerCreeper.this.setAnimationState(random.nextBoolean() ? 3 : 4);
                        this.mob.doHurtTarget(pTarget);
                    }
                }
            }
        };

        walkingGoal = new WaterAvoidingRandomStrollGoal(this, 0.8D) {
            @Override
            public void stop() {
                super.stop();
                DwemerCreeper.this.setAnimationState(DwemerCreeper.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(DwemerCreeper.this.getAnimationState() != 1)
                    DwemerCreeper.this.setAnimationState(1);
            }
        };

        sprintToNearestPlayerGoal = new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public void stop() {
                super.stop();
                DwemerCreeper.this.setAnimationState(DwemerCreeper.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(DwemerCreeper.this.getAnimationState() != 2)
                    DwemerCreeper.this.setAnimationState(2);
            }
        };

//        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, walkingGoal);
        this.goalSelector.addGoal(4, meleeGoal); //new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, true));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return super.getCustomName();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.JUMP_STRENGTH, 0.1D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 18.0D);
    }

    @Override
    protected float getBlockJumpFactor() {
        return super.getBlockJumpFactor();
    }

    public static boolean checkSpawnRules(
            EntityType<DwemerCreeper> pEntity, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom
    ) {
        if (pPos.getY() >= pLevel.getSeaLevel()) {
            return false;
        } else {
            int i = pLevel.getMaxLocalRawBrightness(pPos);

            return i <= 5 && checkMobSpawnRules(pEntity, pLevel, pSpawnType, pPos, pRandom);
        }
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : pLevel.getBrightness(LightLayer.BLOCK, pPos) - 0.5F;
    }

    public boolean shouldDropExperience() {
        return true;
    }

    protected boolean shouldDropLoot() {
        return true;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 0);
        this.entityData.define(PREV_ANIMATION_STATE, 0);
    }

    public void addAdditionalSaveData(CompoundTag p_213281_1_) {
        super.addAdditionalSaveData(p_213281_1_);
        p_213281_1_.putInt("AnimationState", this.getAnimationState());
        p_213281_1_.putInt("PrevAnimationState", this.getPrevAnimationState());
    }

    public void readAdditionalSaveData(CompoundTag p_70037_1_) {
        super.readAdditionalSaveData(p_70037_1_);
        this.setAnimationState(p_70037_1_.getInt("AnimationState"));
        this.setPrevAnimationState(p_70037_1_.getInt("PrevAnimationState"));
    }

    public void setAnimationState(int animationState) {
        setPrevAnimationState(this.getAnimationState());
        this.entityData.set(PREV_ANIMATION_STATE, this.getAnimationState());
        this.entityData.set(ANIMATION_STATE, animationState);
    }
    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
    }

    public void setPrevAnimationState(int prevAnimationState) {
        this.entityData.set(PREV_ANIMATION_STATE, prevAnimationState);
    }
    public int getPrevAnimationState() { return this.entityData.get(PREV_ANIMATION_STATE); }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData, @Nullable CompoundTag pCompoundTag) {
        this.setAnimationState(0);
        this.setPrevAnimationState(0);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData, pCompoundTag);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "dwemer_creeper_controller", 0, state -> {
            if (this.getAnimationState() == 0) {
                return state.setAndContinue(IDLE);
            } else if (this.getAnimationState() == 1) {
                return state.setAndContinue(WALK);
            } else if (this.getAnimationState() == 2) {
                return state.setAndContinue(DUCK);
            } else if (this.getAnimationState() == 3) {
                return state.setAndContinue(IDLE_DUCKED);
            } else {
                return state.setAndContinue(IDLE);
            }
        }));
    }
}