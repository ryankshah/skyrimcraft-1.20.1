package com.ryankshah.skyrimcraft.network.spell;

import com.ryankshah.skyrimcraft.Constants;
import commonnetwork.networking.data.PacketContext;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public record DetectLife(IntList ids)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "detectlife");

    public DetectLife(final FriendlyByteBuf buffer) {
        this(buffer.readIntIdList());
    }

    public static DetectLife decode(FriendlyByteBuf buf) {
        return new DetectLife(buf.readIntIdList());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeIntIdList(ids);
    }

    public static void handle(PacketContext<DetectLife> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
            Player player = Minecraft.getInstance().player;
            for(int id : context.message().ids) {
                Entity entity = player.level().getEntity(id);
                if(entity instanceof LivingEntity livingEntity)
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 2, 1, true, false, false));
            }
        });
    }
}
