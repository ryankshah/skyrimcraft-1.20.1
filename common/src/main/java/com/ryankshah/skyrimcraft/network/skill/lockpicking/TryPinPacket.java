package com.ryankshah.skyrimcraft.network.skill.lockpicking;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.ContainerRegistry;
import com.ryankshah.skyrimcraft.screen.container.LockPickingContainer;
import com.ryankshah.skyrimcraft.util.Lockable;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public record TryPinPacket(byte pin)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "trypinpacket");

    public TryPinPacket(final FriendlyByteBuf buffer)
    {
        this(buffer.readByte());
    }

    public static TryPinPacket decode(FriendlyByteBuf buffer) {
        return new TryPinPacket(buffer.readByte());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(pin);
    }

    public static void handle(PacketContext<TryPinPacket> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<TryPinPacket> ctx) {
        AbstractContainerMenu container = ctx.sender().containerMenu;
        if(container.getType() == ContainerRegistry.LOCK_PICKING.get())
            ((LockPickingContainer) container).tryPin(ctx.message().pin);
    }

    public static void handleClient(PacketContext<TryPinPacket> ctx) {
    }
}
