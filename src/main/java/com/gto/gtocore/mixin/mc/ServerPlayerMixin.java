package com.gto.gtocore.mixin.mc;

import com.gto.gtocore.utils.ServerUtils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(value = ServerPlayer.class, priority = 0)
public class ServerPlayerMixin {

    @Shadow
    @Final
    public MinecraftServer server;

    @Inject(method = "awardRecipes", at = @At("HEAD"), cancellable = true)
    private void awardRecipes(Collection<Recipe<?>> recipes, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }

    @Inject(method = "awardRecipesByKey", at = @At("HEAD"), cancellable = true)
    private void awardRecipesByKey(ResourceLocation[] resourceLocations, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "getPermissionLevel", at = @At("HEAD"), cancellable = true)
    private void getPermissionLevel(CallbackInfoReturnable<Integer> cir) {
        if (ServerUtils.getPersistentData(server).getBoolean("srm")) {
            cir.setReturnValue(0);
        }
    }
}
