package com.gto.gtocore.mixin.kubejs;

import com.gto.gtocore.integration.kjs.GTKubeJSPlugin;

import com.gregtechceu.gtceu.data.pack.GTDynamicDataPack;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;

import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.sugar.Local;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(RecipesEventJS.class)
public class RecipesEventJSMixin {

    @Inject(method = "post", at = @At(value = "INVOKE", target = "Ljava/util/HashMap;putAll(Ljava/util/Map;)V", ordinal = 1), remap = false)
    private void post(CallbackInfo ci, @Local HashMap<ResourceLocation, Recipe<?>> recipesByName) {
        if (!GTKubeJSPlugin.cache) {
            GTKubeJSPlugin.RECIPES_CACHE = ImmutableMap.copyOf(recipesByName);
            GTDynamicDataPack.clearServer();
            recipesByName.clear();
            GTKubeJSPlugin.cache = true;
        }
    }
}
