package com.ryankshah.skyrimcraft.mixin;

import com.ryankshah.skyrimcraft.character.lockpicking.ILockableHandler;
import com.ryankshah.skyrimcraft.platform.Services;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientChunkCache.class)
public class ChunkCacheMixin {

    @Shadow
    volatile ClientChunkCache.Storage storage;

    @Inject(method = "drop(II)V",at = @At(value = "TAIL"))
    private void drop(int i, int j, CallbackInfo ci) {
        if (storage.inRange(i, j)) {
            int k = this.storage.getIndex(i, j);
            LevelChunk levelChunk = this.storage.getChunk(k);
            if (ClientChunkCache.isValidChunk(levelChunk, i, j)) {
                ILockableHandler handler = Services.PLATFORM.getLockableHandler(levelChunk.getLevel());
                Services.PLATFORM.getLockableStorage(levelChunk).get().values().forEach(lkb ->
                {
                    handler.getLoaded().remove(lkb.id);
                    lkb.deleteObserver(handler);
                });
            }
        }
    }

}