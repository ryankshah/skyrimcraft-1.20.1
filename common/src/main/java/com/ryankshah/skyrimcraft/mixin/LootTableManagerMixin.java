package com.ryankshah.skyrimcraft.mixin;

import com.ryankshah.skyrimcraft.util.CommonUtil;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.LootDataManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(LootDataManager.class)
public class LootTableManagerMixin
{
    @Inject(at = @At("HEAD"), method = "reload")
    private void setResourceManager(PreparableReloadListener.PreparationBarrier pPreparationBarrier, ResourceManager pResourceManager, ProfilerFiller pPreparationsProfiler, ProfilerFiller pReloadProfiler, Executor pBackgroundExecutor, Executor pGameExecutor, CallbackInfoReturnable<CompletableFuture<Void>> cir)
    {
        CommonUtil.resourceManager = pResourceManager;
    }
}