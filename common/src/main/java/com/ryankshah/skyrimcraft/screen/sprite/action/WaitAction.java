package com.ryankshah.skyrimcraft.screen.sprite.action;

import com.ryankshah.skyrimcraft.screen.sprite.Sprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//暂停动作
@OnlyIn(Dist.CLIENT)
public class WaitAction<S extends Sprite> extends TimedAction<S>
{
    public static <Z extends Sprite> WaitAction<Z> ticks(int ticks)
    {
        return (WaitAction<Z>) new WaitAction().time(ticks);
    }
}