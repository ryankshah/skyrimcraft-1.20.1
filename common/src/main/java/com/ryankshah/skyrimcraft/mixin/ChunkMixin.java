package com.ryankshah.skyrimcraft.mixin;

import com.ryankshah.skyrimcraft.character.lockpicking.ILockableHandler;
import com.ryankshah.skyrimcraft.character.lockpicking.ILockableStorage;
import com.ryankshah.skyrimcraft.network.skill.lockpicking.AddLockableToChunkPacket;
import com.ryankshah.skyrimcraft.platform.Services;
import com.ryankshah.skyrimcraft.util.ILockableProvider;
import com.ryankshah.skyrimcraft.util.Lockable;
import commonnetwork.api.Dispatcher;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelChunk.class)
public class ChunkMixin
{
    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ProtoChunk;Lnet/minecraft/world/level/chunk/LevelChunk$PostLoadProcessor;)V")
    private void init(ServerLevel world, ProtoChunk pChunk, LevelChunk.PostLoadProcessor pPostLoad, CallbackInfo ci)
    {
        LevelChunk ch = (LevelChunk) (Object) this;
        ILockableStorage st = Services.PLATFORM.getLockableStorage(ch); //LocksComponents.LOCKABLE_STORAGE.get(ch);
        ILockableHandler handler = Services.PLATFORM.getLockableHandler(world); //LocksComponents.LOCKABLE_HANDLER.get(world);
        // We trust that all checks pass (such as volume and intersect checks) due to this happening only during world gen
        for(Lockable lkb : ((ILockableProvider) pChunk).getLockables())
        {
            st.add(lkb);
            handler.getLoaded().put(lkb.id, lkb);
            lkb.addObserver(handler);
//			AddLockableToChunkPacket.execute(new AddLockableToChunkPacket(lkb, ch), world);
            world.getServer().getPlayerList().getPlayers().forEach(player -> {
                Dispatcher.sendToClient(new AddLockableToChunkPacket(lkb, ch.getPos().x, ch.getPos().z), player);
            });
        }
    }
}