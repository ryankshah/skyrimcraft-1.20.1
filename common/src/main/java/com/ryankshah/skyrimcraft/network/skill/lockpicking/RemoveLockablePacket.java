package com.ryankshah.skyrimcraft.network.skill.lockpicking;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.platform.Services;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RemoveLockablePacket(int id)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "removelockablepacket");

    public RemoveLockablePacket(final FriendlyByteBuf buffer)
    {
        this(buffer.readInt());
    }

    public static RemoveLockablePacket decode(FriendlyByteBuf buffer) {
        return new RemoveLockablePacket(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(id);
    }

    public static void handle(PacketContext<RemoveLockablePacket> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<RemoveLockablePacket> ctx) {
    }

    public static void handleClient(PacketContext<RemoveLockablePacket> ctx) {
        Minecraft.getInstance().execute(() -> {
            Services.PLATFORM.getLockableHandler(Minecraft.getInstance().level).remove(ctx.message().id);
        });
    }
}