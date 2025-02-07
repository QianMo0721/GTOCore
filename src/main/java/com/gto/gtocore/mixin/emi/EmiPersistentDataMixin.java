package com.gto.gtocore.mixin.emi;

import com.google.gson.JsonObject;
import com.llamalad7.mixinextras.sugar.Local;
import dev.emi.emi.runtime.EmiPersistentData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.FileWriter;
import java.io.IOException;

@Mixin(EmiPersistentData.class)
public class EmiPersistentDataMixin {

    @Inject(method = "save", at = @At(value = "INVOKE", target = "Ldev/emi/emi/runtime/EmiSidebars;save(Lcom/google/gson/JsonObject;)V"), remap = false, cancellable = true)
    private static void save(CallbackInfo ci, @Local JsonObject json) throws IOException {
        FileWriter writer = new FileWriter(EmiPersistentData.FILE);
        EmiPersistentData.GSON.toJson(json, writer);
        writer.close();
        ci.cancel();
    }

    @Inject(method = "load", at = @At(value = "INVOKE", target = "Ldev/emi/emi/runtime/EmiSidebars;load(Lcom/google/gson/JsonObject;)V"), remap = false, cancellable = true)
    private static void load(CallbackInfo ci) {
        ci.cancel();
    }
}
