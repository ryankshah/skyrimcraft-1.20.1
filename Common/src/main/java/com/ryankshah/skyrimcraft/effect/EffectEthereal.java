package com.ryankshah.skyrimcraft.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class EffectEthereal extends MobEffect
{
    protected EffectEthereal(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        // Apply your effect logic here.
        if(!livingEntity.isInvulnerable())
            livingEntity.setInvulnerable(true);
        super.applyEffectTick(livingEntity, amplifier);
    }

    // Whether the effect should apply this tick. Used e.g. by the Regeneration effect that only applies
    // once every x ticks, depending on the tick count and amplifier.
    @Override
    public boolean isDurationEffectTick(int tickCount, int amplifier) {
        return true; //tickCount % 2 == 0; // replace this with whatever check you want
    }
}
