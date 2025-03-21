package com.ryankshah.skyrimcraft.network.skill.lockpicking;

import com.ryankshah.skyrimcraft.Constants;
import com.ryankshah.skyrimcraft.character.lockpicking.ILockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.ILockableStorage;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.Lockable;
import commonnetwork.networking.data.PacketContext;
import commonnetwork.networking.data.Side;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record AddLockableToChunkPacket(Lockable lockable, int x, int z)
{
    public static final ResourceLocation TYPE = new ResourceLocation(Constants.MODID, "addlockabletochunkpacket");

    public AddLockableToChunkPacket(final FriendlyByteBuf buffer)
    {
        this(Lockable.fromBuf(buffer), buffer.readInt(), buffer.readInt());
    }

    public static AddLockableToChunkPacket decode(FriendlyByteBuf buffer) {
        return new AddLockableToChunkPacket(Lockable.fromBuf(buffer), buffer.readInt(), buffer.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        Lockable.toBuf(buf, lockable);
        buf.writeInt(x);
        buf.writeInt(z);
    }

    public static void handle(PacketContext<AddLockableToChunkPacket> context) {
        if(context.side().equals(Side.CLIENT))
            handleClient(context);
        else
            handleServer(context);
    }

    public static void handleServer(PacketContext<AddLockableToChunkPacket> ctx) {
    }

    public static void handleClient(PacketContext<AddLockableToChunkPacket> ctx) {
        Minecraft.getInstance().execute(() -> {
            Minecraft mc = Minecraft.getInstance();
            ILockableStorage st = Services.PLATFORM.getLockableStorage(mc.level.getChunk(ctx.message().x, ctx.message().z));
            ILockableHandler handler = Services.PLATFORM.getLockableHandler(mc.level);
            Int2ObjectMap<Lockable> lkbs = handler.getLoaded();
            Lockable lkb = lkbs.get(ctx.message().lockable.id);
            if(lkb == lkbs.defaultReturnValue())
            {
                lkb = ctx.message().lockable;
                lkb.addObserver(handler);
                lkbs.put(lkb.id, lkb);
            }
            st.add(lkb);
        });
    }
}