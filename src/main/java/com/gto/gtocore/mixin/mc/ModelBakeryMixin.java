package com.gto.gtocore.mixin.mc;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.mixin.ae2.client.BuiltInModelHooksAccessor;

import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {

    @Shadow
    protected abstract void cacheAndQueueDependencies(ResourceLocation id, UnbakedModel unbakedModel);

    @Inject(method = "loadModel", at = @At("HEAD"), cancellable = true)
    private void loadModelHook(ResourceLocation id, CallbackInfo ci) {
        UnbakedModel model = gtocore$getUnbakedModel(id);
        if (model != null) {
            cacheAndQueueDependencies(id, model);
            ci.cancel();
        }
    }

    @Unique
    private static UnbakedModel gtocore$getUnbakedModel(ResourceLocation id) {
        if (!id.getNamespace().equals(GTOCore.MOD_ID)) return null;
        if (id instanceof ModelResourceLocation modelId) {
            if ("inventory".equals(modelId.getVariant())) {
                return BuiltInModelHooksAccessor.getBuiltInModels().get(GTOCore.id("item/" + modelId.getPath()));
            }
            return null;
        } else {
            return BuiltInModelHooksAccessor.getBuiltInModels().get(id);
        }
    }
}
