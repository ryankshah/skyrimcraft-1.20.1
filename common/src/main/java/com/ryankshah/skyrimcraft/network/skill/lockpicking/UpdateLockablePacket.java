package com.ryankshah.skyrimcraft.network.skill.lockpicking;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record UpdateLockablePacket(int id, boolean locked)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "updatelockablepacket");

    public UpdateLockablePacket(final FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readBoolean());
    }

    public static UpdateLockablePacket decode(FriendlyByteBuf buffer) {
        return new UpdateLockablePacket(buffer.readInt(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
        buf.writeBoolean(locked);
    }

    public static void handle(PacketContext<UpdateLockablePacket> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<UpdateLockablePacket> ctx) {
    }

    public static void handleClient(PacketContext<UpdateLockablePacket> ctx) {
        Minecraft.getInstance().execute(() -> {
            Services.PLATFORM.getLockableHandler(Minecraft.getInstance().level).getLoaded().get(ctx.message().id).lock.setLocked(ctx.message().locked);
        });
    }
}