package com.ryankshah.skyrimcraft.network.skill.lockpicking;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.Lockable;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record AddLockablePacket(Lockable lockable)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "addlockablepacket");

    public AddLockablePacket(final FriendlyByteBuf buffer)
    {
        this(Lockable.fromBuf(buffer));
    }

    public static AddLockablePacket decode(FriendlyByteBuf buffer) {
        return new AddLockablePacket(Lockable.fromBuf(buffer));
    }

    public void encode(FriendlyByteBuf buf) {
        Lockable.toBuf(buf, lockable);
    }

    public static void handle(PacketContext<AddLockablePacket> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<AddLockablePacket> ctx) {
    }

    public static void handleClient(PacketContext<AddLockablePacket> ctx) {
        Minecraft.getInstance().execute(() -> {
            Services.PLATFORM.getLockableHandler(Minecraft.getInstance().level).add(ctx.message().lockable);
        });
    }
}