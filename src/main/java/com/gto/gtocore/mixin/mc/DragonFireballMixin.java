package com.gto.gtocore.mixin.mc;

import net.minecraft.world.entity.projectile.DragonFireball;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(value = DragonFireball.class, priority = 0)
public class DragonFireballMixin {

    @ModifyArg(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/effect/MobEffectInstance;<init>(Lnet/minecraft/world/effect/MobEffect;II)V"), index = 2)
    private int modifyAmplifier(int a) {
        return 4;
    }

    @ModifyArg(method = "onHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/AreaEffectCloud;setRadius(F)V"), index = 0)
    private float modifyRadius(float radius) {
        return 5.0F;
    }
}
