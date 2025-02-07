package com.gto.gtocore.mixin.apotheosis;

import dev.shadowsoffire.apotheosis.Apotheosis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Apotheosis.class)
public class ApotheosisMixin {

    @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)
    private static void init(CallbackInfo ci) {
        Apotheosis.enableSpawner = false;
    }
}
