package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectMagickaRegen extends MobEffect
{
    protected EffectMagickaRegen(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean isDurationEffectTick(int tickCount, int amplifier) {
        return tickCount % 20 == 0; // every 1 second
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int p_76394_2_) {
        super.applyEffectTick(livingEntity, p_76394_2_);
    }
}
