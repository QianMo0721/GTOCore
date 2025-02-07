package com.gto.gtocore.mixin.mc;

import net.minecraft.client.resources.SplashManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = SplashManager.class, priority = 0)
public class SplashManagerMixin {

    @Inject(at = @At("RETURN"), method = "apply(Ljava/util/List;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", cancellable = true)
    private void addSplashes(List<String> splashes, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        ci.cancel();
    }
}
