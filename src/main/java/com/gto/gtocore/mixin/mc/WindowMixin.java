package com.gto.gtocore.mixin.mc;

import net.minecraft.client.Minecraft;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.platform.Window;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Window.class, priority = 0)
public class WindowMixin {

    @Shadow
    private int framebufferWidth;

    @Shadow
    private int framebufferHeight;

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/lwjgl/glfw/GLFW;glfwWindowHint(II)V", ordinal = 0), remap = false)
    private void onDefaultWindowHints(int hint, int value, Operation<Void> original) {
        if (Minecraft.ON_OSX) {
            original.call(GLFW.GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW.GLFW_FALSE);
        }
        original.call(hint, value);
    }

    @Inject(method = "refreshFramebufferSize", at = @At(value = "RETURN"))
    private void refreshFramebufferSize(CallbackInfo ci) {
        if (Minecraft.ON_OSX) {
            framebufferWidth /= 2;
            framebufferHeight /= 2;
        }
    }
}
