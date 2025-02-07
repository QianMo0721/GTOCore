package com.gto.gtocore.mixin.mc;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EnderDragon.class, priority = 0)
public class EnderDragonMixin extends Mob {

    protected EnderDragonMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tickDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;remove(Lnet/minecraft/world/entity/Entity$RemovalReason;)V"))
    private void tickDeath(CallbackInfo ci) {
        level().explode((EnderDragon) (Object) this, getX(), getY(), getZ(), 80, Level.ExplosionInteraction.NONE);
    }
}
