package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectIncreasedArmorRating extends InstantenousMobEffect
{
    protected EffectIncreasedArmorRating(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int tickCount, int amplifier) {
        return true;
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
