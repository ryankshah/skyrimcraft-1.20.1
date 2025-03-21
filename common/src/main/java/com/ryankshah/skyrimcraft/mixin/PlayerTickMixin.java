package com.ryankshah.skyrimcraft.mixin;

import com.ryankshah.skyrimcraft.character.lockpicking.ISelection;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class PlayerTickMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {

        ISelection select = Services.PLATFORM.getSelection((Player)(Object)this); //LocksComponents.SELECTION.get((Player) (Object) this);
        if (select == null)
            return;
        for (ItemStack stack : ((Player) (Object) this).getHandSlots())
            if(stack.is(ItemRegistry.LOCKPICK.get()))
                return;
        select.set(null);
    }
}