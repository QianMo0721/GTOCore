package com.gto.gtocore.mixin.gtm.registry;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.api.data.GTOWorldGenLayers;
import com.gto.gtocore.utils.RLUtils;

import com.gregtechceu.gtceu.api.data.DimensionMarker;
import com.gregtechceu.gtceu.common.data.GTDimensionMarkers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import com.kyanite.deeperdarker.DeeperDarker;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

import static com.gregtechceu.gtceu.common.data.GTDimensionMarkers.END_MARKER;
import static com.gregtechceu.gtceu.common.data.GTDimensionMarkers.NETHER_MARKER;

@Mixin(GTDimensionMarkers.class)
public final class GTDimensionMarkersMixin {

    @Shadow(remap = false)
    public static DimensionMarker createAndRegister(ResourceLocation dim, int tier, ResourceLocation itemKey, @Nullable String overrideName) {
        return null;
    }

    @Unique
    private static DimensionMarker gtoCore$createAndRegister(ResourceLocation dim, int tier, Supplier<ItemLike> supplier) {
        DimensionMarker marker = new DimensionMarker(tier, supplier, null);
        marker.register(dim);
        return marker;
    }

    @Inject(method = "init", at = @At("HEAD"), remap = false)
    private static void init(CallbackInfo ci) {
        createAndRegister(GTOWorldGenLayers.MOON,
                1, RLUtils.ad("moon_stone"), "dimension.ad_astra.moon");
        createAndRegister(GTOWorldGenLayers.MARS,
                2, RLUtils.ad("mars_stone"), "dimension.ad_astra.mars");
        createAndRegister(GTOWorldGenLayers.VENUS,
                3, RLUtils.ad("venus_stone"), "dimension.ad_astra.venus");
        createAndRegister(GTOWorldGenLayers.MERCURY,
                3, RLUtils.ad("mercury_stone"), "dimension.ad_astra.mercury");
        createAndRegister(GTOWorldGenLayers.GLACIO,
                7, RLUtils.ad("glacio_stone"), "dimension.ad_astra.glacio");
        createAndRegister(GTOWorldGenLayers.ANCIENT_WORLD,
                0, GTOCore.id("reactor_core"), "biome.gtocore.ancient_world_biome");
        createAndRegister(GTOWorldGenLayers.TITAN,
                6, GTOCore.id("titan_stone"), "biome.gtocore.titan_biome");
        createAndRegister(GTOWorldGenLayers.PLUTO,
                6, GTOCore.id("pluto_stone"), "biome.gtocore.pluto_biome");
        createAndRegister(GTOWorldGenLayers.IO,
                5, GTOCore.id("io_stone"), "biome.gtocore.io_biome");
        createAndRegister(GTOWorldGenLayers.GANYMEDE,
                5, GTOCore.id("ganymede_stone"), "biome.gtocore.ganymede_biome");
        createAndRegister(GTOWorldGenLayers.ENCELADUS,
                6, GTOCore.id("enceladus_stone"), "biome.gtocore.enceladus_biome");
        createAndRegister(GTOWorldGenLayers.CERES,
                4, GTOCore.id("ceres_stone"), "biome.gtocore.ceres_biome");
        createAndRegister(GTOWorldGenLayers.BARNARDA_C,
                8, GTOCore.id("barnarda_c_log"), "biome.gtocore.barnarda_c_biome");
        createAndRegister(GTOWorldGenLayers.OTHERSIDE,
                9, DeeperDarker.rl("sculk_stone"), "Otherside");
        createAndRegister(GTOWorldGenLayers.CREATE,
                10, GTOCore.id("dimension_creation_casing"), "biome.gtocore.create");
        createAndRegister(GTOWorldGenLayers.VOID,
                0, RLUtils.mc("obsidian"), "biome.gtocore.void");
        createAndRegister(GTOWorldGenLayers.FLAT,
                0, RLUtils.mc("crying_obsidian"), "biome.gtocore.flat");
    }

    @Inject(method = "createAndRegister(Lnet/minecraft/resources/ResourceLocation;ILjava/util/function/Supplier;Ljava/lang/String;)Lcom/gregtechceu/gtceu/api/data/DimensionMarker;", at = @At("HEAD"), remap = false, cancellable = true)
    private static void createAndRegister(ResourceLocation dim, int tier, Supplier<ItemLike> supplier, @Nullable String overrideName, CallbackInfoReturnable<DimensionMarker> cir) {
        if (dim == Level.NETHER.location()) {
            cir.setReturnValue(gtoCore$createAndRegister(Level.NETHER.location(), 3, () -> NETHER_MARKER));
        } else if (dim == Level.END.location()) {
            cir.setReturnValue(gtoCore$createAndRegister(Level.END.location(), 6, () -> END_MARKER));
        }
    }
}
