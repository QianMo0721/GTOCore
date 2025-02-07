package com.gto.gtocore.mixin.apotheosis;

import net.minecraftforge.eventbus.api.IEventBus;

import dev.shadowsoffire.apotheosis.adventure.AdventureModule;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AdventureModule.class)
public class AdventureModuleMixin {

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/eventbus/api/IEventBus;register(Ljava/lang/Object;)V", ordinal = 1), remap = false)
    private void register(IEventBus instance, Object o) {}
}
