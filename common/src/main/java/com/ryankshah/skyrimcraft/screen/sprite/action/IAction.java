package com.ryankshah.skyrimcraft.screen.sprite.action;


import com.ryankshah.skyrimcraft.screen.sprite.Sprite;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.BiConsumer;

//控制精灵的行为
@OnlyIn(Dist.CLIENT)
public interface IAction<S extends Sprite>
{
    boolean isFinished(S sprite);

    void update(S sprite);

    void finish(S sprite);

    IAction<S> then(BiConsumer<IAction<S>, S> cb);
}