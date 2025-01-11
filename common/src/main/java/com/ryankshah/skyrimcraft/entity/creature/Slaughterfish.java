package com.ryankshah.skyrimcraft.entity.creature;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class Slaughterfish extends AbstractFish implements GeoAnimatable
{
    private static final EntityDataAccessor<Integer> PREV_ANIMATION_STATE = SynchedEntityData.defineId(Slaughterfish.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(Slaughterfish.class, EntityDataSerializers.INT);

    protected static final RawAnimation SWIM = RawAnimation.begin().thenLoop("swim");
    protected static final RawAnimation ATTACK = RawAnimation.begin().thenLoop("attack");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public Slaughterfish(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.GUARDIAN_FLOP;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0f).add(Attributes.ATTACK_DAMAGE, 2.0f);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return null;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0f, false));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<Player>(this, Player.class, true));
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
                return state.setAndContinue(SWIM);
            } else if (this.getAnimationState() == 1) {
                return state.setAndContinue(ATTACK);
            } else {
                return state.setAndContinue(SWIM);
            }
        }));
    }

    @Override
    public void tick() {
        if(this.isSwimming() && this.getAnimationState() != 0) {
            this.setAnimationState(0);
        }
        if(this.getTarget() != null && doHurtTarget(getTarget())) {
            this.setAnimationState(1);
        }
        super.tick();
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }
}
