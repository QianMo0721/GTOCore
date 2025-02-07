package com.gto.gtocore.mixin.adastra;

import net.minecraft.world.entity.player.Player;

import earth.terrarium.adastra.common.menus.PlanetsMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlanetsMenu.class)
public class PlanetsMenuMixin {

    @Shadow(remap = false)
    @Final
    protected Player player;

    @Shadow(remap = false)
    @Final
    protected int tier;

    @Inject(method = "tier", at = @At("HEAD"), remap = false, cancellable = true)
    private void tier(CallbackInfoReturnable<Integer> cir) {
        if (tier == 100) {
            cir.setReturnValue(10);
        }
    }
}
