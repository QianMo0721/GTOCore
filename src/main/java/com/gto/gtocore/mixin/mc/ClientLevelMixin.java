package com.gto.gtocore.mixin.mc;

import com.gto.gtocore.common.saved.DysonSphereSavaedData;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(value = ClientLevel.class, priority = 0)
public abstract class ClientLevelMixin extends Level {

    @Shadow
    @Final
    private ClientLevel.ClientLevelData clientLevelData;

    private ClientLevelMixin(WritableLevelData levelData, ResourceKey<Level> dimension, RegistryAccess registryAccess, Holder<DimensionType> dimensionTypeRegistration, Supplier<ProfilerFiller> profiler, boolean isClientSide, boolean isDebug, long biomeZoomSeed, int maxChainedNeighborUpdates) {
        super(levelData, dimension, registryAccess, dimensionTypeRegistration, profiler, isClientSide, isDebug, biomeZoomSeed, maxChainedNeighborUpdates);
    }

    @Inject(method = "setDayTime", at = @At("HEAD"), cancellable = true)
    private void setDayTime(long time, CallbackInfo ci) {
        if (DysonSphereSavaedData.getDimensionLaunchData(dimension()) > 100) {
            long dayTime = clientLevelData.getDayTime();
            clientLevelData.setDayTime(dayTime + (24000L - (dayTime % 24000L) + 18000L) % 24000L);
            ci.cancel();
        }
    }
}
