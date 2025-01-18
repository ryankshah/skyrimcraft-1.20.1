package com.ryankshah.skyrimcraft.network.character;

import com.ryankshah.skyrimcraft.Constants;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.levelgen.Heightmap;

public record FastTravel(BlockPos blockPos)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "fasttravel");

    public FastTravel(final FriendlyByteBuf buffer) {
        this(buffer.readBlockPos());
    }

    public static FastTravel decode(FriendlyByteBuf buf) {
        return new FastTravel(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(blockPos);
    }

    public static void handle(PacketContext<FastTravel> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<FastTravel> context) {
        ServerPlayer player = (ServerPlayer) context.sender();
        BlockPos pos = context.message().blockPos();
        int y = player.level().getHeight(Heightmap.Types.WORLD_SURFACE, pos.getX(), pos.getZ());
        player.teleportTo(pos.getX(), y, pos.getZ());
    }

    public static void handleClient(PacketContext<FastTravel> context) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.execute(() -> {
        });
    }
}

