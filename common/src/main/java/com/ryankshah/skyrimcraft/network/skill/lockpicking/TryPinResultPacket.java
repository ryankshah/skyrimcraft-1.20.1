package com.ryankshah.skyrimcraft.network.skill.lockpicking;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.registry.ContainerRegistry;
import com.ryankshah.skyrimcraft.screen.container.LockPickingContainer;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;

public record TryPinResultPacket(boolean correct, boolean reset)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "trypinresultpacket");

    public TryPinResultPacket(final FriendlyByteBuf buffer)
    {
        this(buffer.readBoolean(), buffer.readBoolean());
    }

    public static TryPinResultPacket decode(FriendlyByteBuf buffer) {
        return new TryPinResultPacket(buffer.readBoolean(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBoolean(correct);
        buf.writeBoolean(reset);
    }

    public static void handle(PacketContext<TryPinResultPacket> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<TryPinResultPacket> ctx) {
    }

    public static void handleClient(PacketContext<TryPinResultPacket> ctx) {
        Minecraft.getInstance().execute(() -> {
            AbstractContainerMenu container = Minecraft.getInstance().player.containerMenu;
            if(container.getType() == ContainerRegistry.LOCK_PICKING.get())
                ((LockPickingContainer) container).handlePin(ctx.message().correct, ctx.message().reset);
        });
    }
}