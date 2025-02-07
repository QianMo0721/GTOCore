package com.gto.gtocore.mixin.mc;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.Recipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(value = ServerRecipeBook.class, priority = 0)
public class ServerRecipeBookMixin {

    @Inject(method = "addRecipes", at = @At("HEAD"), cancellable = true)
    private void addRecipes(Collection<Recipe<?>> recipes, ServerPlayer player, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }

    @Inject(method = "removeRecipes", at = @At("HEAD"), cancellable = true)
    private void removeRecipes(Collection<Recipe<?>> recipes, ServerPlayer player, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(0);
    }

    @Inject(method = "toNbt", at = @At("HEAD"), cancellable = true)
    private void toNbt(CallbackInfoReturnable<CompoundTag> cir) {
        cir.setReturnValue(new CompoundTag());
    }

    @Inject(method = "fromNbt", at = @At("HEAD"), cancellable = true)
    private void fromNbt(CallbackInfo ci) {
        ci.cancel();
    }
}
