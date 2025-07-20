package com.gtocore.mixin.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;

import com.mojang.blaze3d.vertex.PoseStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GuiGraphics.class)
public interface GuiGraphicsAccessor {

    @Invoker("<init>")
    static GuiGraphics createGuiGraphics(Minecraft minecraft, PoseStack pose, MultiBufferSource.BufferSource bufferSource) {
        throw new AssertionError("Mixin failed to apply, this should never be called directly.");
    }
}
