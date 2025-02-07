package com.gto.gtocore.mixin.ae2.gui;

import com.gto.gtocore.client.gui.IPatterEncodingTermMenu;
import com.gto.gtocore.client.gui.ModifyIcon;
import com.gto.gtocore.client.gui.ModifyIconButton;

import net.minecraft.network.chat.Component;

import appeng.client.gui.WidgetContainer;
import appeng.client.gui.me.items.EncodingModePanel;
import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.client.gui.me.items.ProcessingEncodingPanel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author EasterFG on 2024/9/12
 */

@Mixin(ProcessingEncodingPanel.class)
public abstract class ProcessingEncodingPanelMixin extends EncodingModePanel {

    @Unique
    private ModifyIconButton gtoCore$multipleTow;
    @Unique
    private ModifyIconButton gtoCore$multipleThree;
    @Unique
    private ModifyIconButton gtoCore$multipleFive;
    @Unique
    private ModifyIconButton gtoCore$dividingTow;
    @Unique
    private ModifyIconButton gtoCore$dividingThree;
    @Unique
    private ModifyIconButton gtoCore$dividingFive;

    protected ProcessingEncodingPanelMixin(PatternEncodingTermScreen<?> screen, WidgetContainer widgets) {
        super(screen, widgets);
    }

    @Inject(method = "<init>", at = @At("TAIL"), remap = false)
    private void init(PatternEncodingTermScreen<?> screen, WidgetContainer widgets, CallbackInfo ci) {
        gtoCore$multipleTow = new ModifyIconButton(b -> ((IPatterEncodingTermMenu) menu).gtoCore$modifyPatter(2), ModifyIcon.MULTIPLY_2,
                Component.translatable("gtocore.pattern.multiply", 2),
                Component.translatable("gtocore.pattern.tooltip.multiply", 2));

        gtoCore$multipleThree = new ModifyIconButton(b -> ((IPatterEncodingTermMenu) menu).gtoCore$modifyPatter(3), ModifyIcon.MULTIPLY_3,
                Component.translatable("gtocore.pattern.multiply", 3),
                Component.translatable("gtocore.pattern.tooltip.multiply", 3));

        gtoCore$multipleFive = new ModifyIconButton(b -> ((IPatterEncodingTermMenu) menu).gtoCore$modifyPatter(5), ModifyIcon.MULTIPLY_5,
                Component.translatable("gtocore.pattern.multiply", 5),
                Component.translatable("gtocore.pattern.tooltip.multiply", 5));

        gtoCore$dividingTow = new ModifyIconButton(b -> ((IPatterEncodingTermMenu) menu).gtoCore$modifyPatter(-2), ModifyIcon.DIVISION_2,
                Component.translatable("gtocore.pattern.divide", 2),
                Component.translatable("gtocore.pattern.tooltip.divide", 2));

        gtoCore$dividingThree = new ModifyIconButton(b -> ((IPatterEncodingTermMenu) menu).gtoCore$modifyPatter(-3), ModifyIcon.DIVISION_3,
                Component.translatable("gtocore.pattern.divide", 3),
                Component.translatable("gtocore.pattern.tooltip.divide", 3));

        gtoCore$dividingFive = new ModifyIconButton(b -> ((IPatterEncodingTermMenu) menu).gtoCore$modifyPatter(-5), ModifyIcon.DIVISION_5,
                Component.translatable("gtocore.pattern.divide", 5),
                Component.translatable("gtocore.pattern.tooltip.divide", 5));

        widgets.add("modify1", gtoCore$multipleTow);
        widgets.add("modify2", gtoCore$multipleThree);
        widgets.add("modify3", gtoCore$multipleFive);
        widgets.add("modify4", gtoCore$dividingTow);
        widgets.add("modify5", gtoCore$dividingThree);
        widgets.add("modify6", gtoCore$dividingFive);
    }

    @Inject(method = "setVisible", at = @At("TAIL"), remap = false)
    private void setVisibleHooks(boolean visible, CallbackInfo ci) {
        gtoCore$multipleTow.setVisibility(visible);
        gtoCore$multipleThree.setVisibility(visible);
        gtoCore$multipleFive.setVisibility(visible);
        gtoCore$dividingTow.setVisibility(visible);
        gtoCore$dividingThree.setVisibility(visible);
        gtoCore$dividingFive.setVisibility(visible);
    }
}
