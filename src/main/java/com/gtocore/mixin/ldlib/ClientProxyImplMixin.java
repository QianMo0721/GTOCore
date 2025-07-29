package com.gtocore.mixin.ldlib;

import com.gtocore.config.GTOConfig;

import net.minecraftforge.client.event.ModelEvent;

import com.lowdragmc.lowdraglib.client.forge.ClientProxyImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientProxyImpl.class)
public class ClientProxyImplMixin {

    @Inject(method = "modelBake", at = @At("HEAD"), remap = false, cancellable = true)
    private void modelBake(ModelEvent.ModifyBakingResult event, CallbackInfo ci) {
        if (GTOConfig.INSTANCE.disableCTM) ci.cancel();
    }
}
