package com.ryankshah.skyrimcraft.entity.creature;

import com.ryankshah.skyrimcraft.entity.ai.goal.ValeSabreCatSprintToNearestAttackableTargetGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class ValeSabreCat extends PathfinderMob implements GeoEntity
{
    private static final EntityDataAccessor<Integer> PREV_ANIMATION_STATE = SynchedEntityData.defineId(ValeSabreCat.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(ValeSabreCat.class, EntityDataSerializers.INT);

    private MeleeAttackGoal meleeGoal;
    private WaterAvoidingRandomStrollGoal walkingGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestPlayerGoal;
    private NearestAttackableTargetGoal<? extends LivingEntity> sprintToNearestAnimalGoal;

    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.sabre_cat.idle");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.sabre_cat.walk");
    protected static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.sabre_cat.run");
    protected static final RawAnimation CLAW = RawAnimation.begin().thenLoop("animation.sabre_cat.claw");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public ValeSabreCat(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
        this.noCulling = true;
        this.xpReward = 5;
//        this.setMaxUpStep(1.25f); // 1.5 works.. but does 1.25f? if so then this comment may still be here xox

        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, -1.0F);
    }

    protected void registerGoals() {
        meleeGoal = new MeleeAttackGoal(this, 1.2D, false) {
            @Override
            public void stop() {
                super.stop();
                ValeSabreCat.this.setAnimationState(0);
            }

            @Override
            protected void checkAndPerformAttack(LivingEntity pTarget, double huh) {
                if (this.mob.canAttack(pTarget)) {
                    if (getTicksUntilNextAttack() <= 0) {
                        this.resetAttackCooldown();
                        this.mob.swing(InteractionHand.MAIN_HAND);
                        if (ValeSabreCat.this.getAnimationState() != 3)
                            ValeSabreCat.this.setAnimationState(3);
                        this.mob.doHurtTarget(pTarget);
                    }
                }
            }
        };

        walkingGoal = new WaterAvoidingRandomStrollGoal(this, 1.0D) {
            @Override
            public void stop() {
                super.stop();
                ValeSabreCat.this.setAnimationState(ValeSabreCat.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(ValeSabreCat.this.getAnimationState() != 1)
                    ValeSabreCat.this.setAnimationState(1);
            }
        };

        sprintToNearestPlayerGoal = new NearestAttackableTargetGoal<>(this, Player.class, true) {
            @Override
            public void stop() {
                super.stop();
                ValeSabreCat.this.setAnimationState(ValeSabreCat.this.getPrevAnimationState());
            }

            @Override
            public void tick() {
                super.tick();
                if(ValeSabreCat.this.getAnimationState() != 2)
                    ValeSabreCat.this.setAnimationState(2);
            }
        };

        this.goalSelector.addGoal(3, new FloatGoal(this));
        //this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(4, meleeGoal); //new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, walkingGoal);
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new ValeSabreCatSprintToNearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(4, new ValeSabreCatSprintToNearestAttackableTargetGoal<>(this, Animal.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.JUMP_STRENGTH, 0.5D).add(Attributes.MOVEMENT_SPEED, 0.3D).add(Attributes.MAX_HEALTH, 40.0D).add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    protected float getBlockJumpFactor() {
        return super.getBlockJumpFactor();
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) ? 10.0F : pLevel.getBrightness(LightLayer.BLOCK, pPos) - 0.5F;
    }

    public Holder<Biome> getCurrentBiome() {
        return this.level().getBiome(this.blockPosition());
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
//        setPrevAnimationState(this.getAnimationState());
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
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "vale_sabre_cat_controller", 0, state -> {
            if(this.getAnimationState() == 0) {
                return state.setAndContinue(IDLE);
            } else if (this.getAnimationState() == 1 && state.isMoving()) {
                return state.setAndContinue(WALK);
            } else if(this.getAnimationState() == 2 && state.isMoving()) {
                return state.setAndContinue(RUN);
            } else if(this.getAnimationState() == 3) {
                return state.setAndContinue(CLAW);
            } else {
                return state.setAndContinue(IDLE);
            }
        }
        ));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}