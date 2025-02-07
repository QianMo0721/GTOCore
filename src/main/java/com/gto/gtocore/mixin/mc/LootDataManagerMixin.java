package com.gto.gtocore.mixin.mc;

import com.gto.gtocore.common.data.GTOLoots;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataManager;
import net.minecraft.world.level.storage.loot.LootDataType;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(value = LootDataManager.class, priority = 0)
public class LootDataManagerMixin {

    @Shadow
    private Multimap<LootDataType<?>, ResourceLocation> typeKeys;

    @Shadow
    private Map<LootDataId<?>, ?> elements;

    @Inject(method = "scheduleElementParse", at = @At("HEAD"), cancellable = true)
    private static <T> void scheduleElementParse(LootDataType<T> lootDataType, ResourceManager resourceManager, Executor backgroundExecutor, Map<LootDataType<?>, Map<ResourceLocation, ?>> elementCollector, CallbackInfoReturnable<CompletableFuture<?>> cir) {
        if (GTOLoots.cache) cir.setReturnValue(CompletableFuture.runAsync(() -> {}, backgroundExecutor));
    }

    @Inject(method = "apply", at = @At("HEAD"), cancellable = true)
    private void apply(Map<LootDataType<?>, Map<ResourceLocation, ?>> collectedElements, CallbackInfo ci) {
        ci.cancel();
        if (!GTOLoots.cache) {
            GTOLoots.cache = true;
            Pair<ImmutableMap<LootDataId<?>, ?>, ImmutableMultimap<LootDataType<?>, ResourceLocation>> pair = GTOLoots.apply(collectedElements);
            GTOLoots.ELEMENTS_CACHE = pair.getFirst();
            GTOLoots.TYPEKEYS_CACHE = pair.getSecond();
        }
        elements = GTOLoots.ELEMENTS_CACHE;
        typeKeys = GTOLoots.TYPEKEYS_CACHE;
    }
}
