package com.gtocore.integration.gtmt;

import com.gtolib.api.annotation.Scanned;
import com.gtolib.api.annotation.language.RegisterLanguage;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.gui.GuiTextures;
import com.gregtechceu.gtceu.api.item.component.IItemUIFactory;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate;
import com.gregtechceu.gtceu.common.block.CoilBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import com.hepdd.gtmthings.api.gui.widget.TerminalInputWidget;
import com.hepdd.gtmthings.api.misc.Hatch;
import com.lowdragmc.lowdraglib.gui.editor.ColorPattern;
import com.lowdragmc.lowdraglib.gui.factory.HeldItemUIFactory;
import com.lowdragmc.lowdraglib.gui.modular.ModularUI;
import com.lowdragmc.lowdraglib.gui.widget.DraggableScrollableWidgetGroup;
import com.lowdragmc.lowdraglib.gui.widget.LabelWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Scanned
public class AdvancedTerminalBehavior implements IItemUIFactory {

    @RegisterLanguage(cn = "镜像搭建", en = "Mirror Build")
    private static final String FLIP = "gtocore.auto_build.flip";

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getPlayer() != null && context.getPlayer().isShiftKeyDown()) {
            Level level = context.getLevel();
            BlockPos blockPos = context.getClickedPos();
            if (context.getPlayer() != null && !level.isClientSide() &&
                    MetaMachine.getMachine(level, blockPos) instanceof IMultiController controller) {
                AutoBuildSetting autoBuildSetting = getAutoBuildSetting(context.getPlayer().getMainHandItem());

                if (!controller.isFormed()) {
                    AdvancedBlockPattern.getAdvancedBlockPattern(controller.getPattern()).autoBuild(context.getPlayer(), controller.getMultiblockState(), autoBuildSetting);
                    controller.getMultiblockState().cleanCache();
                } else if (autoBuildSetting.isReplaceCoilMode()) {
                    AdvancedBlockPattern.getAdvancedBlockPattern(controller.getPattern()).autoBuild(context.getPlayer(), controller.getMultiblockState(), autoBuildSetting);
                    controller.getMultiblockState().cleanCache();
                    controller.requestCheck();
                }

            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public ModularUI createUI(HeldItemUIFactory.HeldItemHolder holder, Player entityPlayer) {
        return new ModularUI(176, 166, holder, entityPlayer).widget(createWidget(entityPlayer));
    }

    private static Widget createWidget(Player entityPlayer) {
        ItemStack handItem = entityPlayer.getMainHandItem();
        var group = new WidgetGroup(0, 0, 182 + 8, 117 + 8);
        int rowIndex = 1;
        List<Component> lines = new ArrayList<>(List.of());
        lines.add(Component.translatable("item.gtmthings.advanced_terminal.setting.1.tooltip"));
        GTCEuAPI.HEATING_COILS.entrySet().stream()
                .sorted(Comparator.comparingInt(value -> value.getKey().getTier()))
                .forEach(coil -> lines.add(Component.literal(String.valueOf(coil.getKey().getTier() + 1)).append(":").append(coil.getValue().get().getName())));

        group.addWidget(
                new DraggableScrollableWidgetGroup(4, 4, 182, 117)
                        .setBackground(GuiTextures.DISPLAY)
                        .setYScrollBarWidth(2)
                        .setYBarStyle(null, ColorPattern.T_WHITE.rectTexture().setRadius(1))
                        .addWidget(new LabelWidget(40, 5, "item.gtmthings.advanced_terminal.setting.title"))
                        .addWidget(new LabelWidget(4, 5 + 16 * rowIndex, "item.gtmthings.advanced_terminal.setting.1")
                                .setHoverTooltips(lines))
                        .addWidget(new TerminalInputWidget(140, 5 + 16 * rowIndex++, 20, 16, () -> getCoilTier(handItem),
                                (v) -> setCoilTier(v, handItem))
                                .setMin(0).setMax(GTCEuAPI.HEATING_COILS.size()))
                        .addWidget(new LabelWidget(4, 5 + 16 * rowIndex, "item.gtmthings.advanced_terminal.setting.2")
                                .setHoverTooltips(Component.translatable("item.gtmthings.advanced_terminal.setting.2.tooltip")))
                        .addWidget(new TerminalInputWidget(140, 5 + 16 * rowIndex++, 20, 16, () -> getRepeatCount(handItem),
                                (v) -> setRepeatCount(v, handItem))
                                .setMin(0).setMax(1000))
                        .addWidget(new LabelWidget(4, 5 + 16 * rowIndex, "item.gtmthings.advanced_terminal.setting.3")
                                .setHoverTooltips("item.gtmthings.advanced_terminal.setting.3.tooltip"))
                        .addWidget(new TerminalInputWidget(140, 5 + 16 * rowIndex++, 20, 16, () -> getIsBuildHatches(handItem),
                                (v) -> setIsBuildHatches(v, handItem)).setMin(0).setMax(1))
                        .addWidget(new LabelWidget(4, 5 + 16 * rowIndex, "item.gtmthings.advanced_terminal.setting.4")
                                .setHoverTooltips("item.gtmthings.advanced_terminal.setting.4.tooltip"))
                        .addWidget(new TerminalInputWidget(140, 5 + 16 * rowIndex++, 20, 16, () -> getReplaceCoilMode(handItem),
                                (v) -> setReplaceCoilMode(v, handItem)).setMin(0).setMax(1))
                        .addWidget(new LabelWidget(4, 5 + 16 * rowIndex, "item.gtmthings.advanced_terminal.setting.5")
                                .setHoverTooltips("item.gtmthings.advanced_terminal.setting.5.tooltip"))
                        .addWidget(new TerminalInputWidget(140, 5 + 16 * rowIndex++, 20, 16, () -> getIsUseAE(handItem),
                                (v) -> setIsUseAE(v, handItem)).setMin(0).setMax(1))
                        .addWidget(new LabelWidget(4, 5 + 16 * rowIndex, FLIP))
                        .addWidget(new TerminalInputWidget(140, 5 + 16 * rowIndex++, 20, 16, () -> getIsFlip(handItem),
                                (v) -> setIsFlip(v, handItem)).setMin(0).setMax(1)));

        group.setBackground(GuiTextures.BACKGROUND_INVERSE);
        return group;
    }

    private static AutoBuildSetting getAutoBuildSetting(ItemStack itemStack) {
        AutoBuildSetting autoBuildSetting = new AutoBuildSetting();
        var tag = itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            autoBuildSetting.coilTier = tag.getInt("coilTier");
            autoBuildSetting.repeatCount = tag.getInt("repeatCount");
            autoBuildSetting.noHatchMode = tag.getInt("noHatchMode");
            autoBuildSetting.replaceCoilMode = tag.getInt("replaceCoilMode");
            autoBuildSetting.isUseAE = tag.getInt("isUseAE");
            autoBuildSetting.isFlip = tag.getInt("isFlip");
        }
        return autoBuildSetting;
    }

    private static int getCoilTier(ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            return tag.getInt("coilTier");
        } else {
            return 0;
        }
    }

    private static void setCoilTier(int coilTier, ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag == null) tag = new CompoundTag();
        tag.putInt("coilTier", coilTier);
        itemStack.setTag(tag);
    }

    private static int getRepeatCount(ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            return tag.getInt("repeatCount");
        } else {
            return 0;
        }
    }

    private static void setRepeatCount(int repeatCount, ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag == null) tag = new CompoundTag();
        tag.putInt("repeatCount", repeatCount);
        itemStack.setTag(tag);
    }

    private static int getIsBuildHatches(ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            return tag.getInt("noHatchMode");
        } else {
            return 1;
        }
    }

    private static void setIsBuildHatches(int isBuildHatches, ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag == null) tag = new CompoundTag();
        tag.putInt("noHatchMode", isBuildHatches);
        itemStack.setTag(tag);
    }

    private static int getReplaceCoilMode(ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            return tag.getInt("replaceCoilMode");
        } else {
            return 0;
        }
    }

    private static void setReplaceCoilMode(int isReplaceCoil, ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag == null) tag = new CompoundTag();
        tag.putInt("replaceCoilMode", isReplaceCoil);
        itemStack.setTag(tag);
    }

    private static int getIsUseAE(ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            return tag.getInt("isUseAE");
        } else {
            return 0;
        }
    }

    private static void setIsUseAE(int isUseAE, ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag == null) tag = new CompoundTag();
        tag.putInt("isUseAE", isUseAE);
        itemStack.setTag(tag);
    }

    private static int getIsFlip(ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag != null && !tag.isEmpty()) {
            return tag.getInt("isFlip");
        } else {
            return 0;
        }
    }

    private static void setIsFlip(int isFlip, ItemStack itemStack) {
        var tag = itemStack.getTag();
        if (tag == null) tag = new CompoundTag();
        tag.putInt("isFlip", isFlip);
        itemStack.setTag(tag);
    }

    static class AutoBuildSetting {

        int coilTier, repeatCount, noHatchMode, replaceCoilMode, isUseAE, isFlip;

        private AutoBuildSetting() {
            this.coilTier = 0;
            this.repeatCount = 0;
            this.noHatchMode = 1;
            this.replaceCoilMode = 0;
            this.isUseAE = 0;
            this.isFlip = 0;
        }

        public List<ItemStack> apply(Block[] blocks) {
            List<ItemStack> candidates = new ArrayList<>();
            if (blocks != null) {
                for (Block block : blocks) {
                    if (block instanceof CoilBlock && coilTier > 0) {
                        candidates.add(blocks[coilTier - 1].asItem().getDefaultInstance());
                        return candidates;
                    } else if (block != Blocks.AIR) {
                        candidates.add(SimplePredicate.toItem(block).getDefaultInstance());
                    }
                }
            }
            return candidates;
        }

        boolean isPlaceHatch(Block[] blocks) {
            if (this.noHatchMode == 0) return true;
            if (blocks != null && blocks.length > 0) {
                var block = blocks[0];
                return !(block instanceof MetaMachineBlock machineBlock) || !Hatch.Set.contains(machineBlock);
            }
            return true;
        }

        boolean isReplaceCoilMode() {
            return replaceCoilMode == 1;
        }
    }
}
