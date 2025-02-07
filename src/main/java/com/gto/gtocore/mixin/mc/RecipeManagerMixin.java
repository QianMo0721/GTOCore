package com.gto.gtocore.mixin.mc;

import com.gto.gtocore.common.data.GTORecipes;
import com.gto.gtocore.integration.kjs.GTKubeJSPlugin;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;

import com.google.gson.JsonElement;
import dev.latvian.mods.kubejs.recipe.RecipesEventJS;
import dev.latvian.mods.kubejs.util.UtilsJS;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(value = RecipeManager.class, priority = 0)
public class RecipeManagerMixin {

    @Shadow
    private Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> recipes;

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At("HEAD"), cancellable = true)
    private void customRecipesHead(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler, CallbackInfo ci) {
        ci.cancel();
        if (RecipesEventJS.instance == null) {
            RecipesEventJS.instance = new RecipesEventJS();
        }
        RecipesEventJS.instance.post(UtilsJS.cast(this), map);
        RecipesEventJS.instance = null;
        if (!GTORecipes.cache || GTKubeJSPlugin.hasKJSGTRecipe) {
            GTORecipes.initLookup(recipes.get(RecipeType.SMELTING));
        }
    }
}
