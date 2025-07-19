package com.gtocore.integration.jade.provider;

import com.gtocore.client.ClientCache;
import com.gtocore.common.item.StructureDetectBehavior;

import com.gtolib.api.annotation.Scanned;
import com.gtolib.api.annotation.language.RegisterLanguage;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.api.pattern.MultiblockState;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import com.glodblock.github.extendedae.client.render.EAEHighlightHandler;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Scanned
public final class MultiblockStructureProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @RegisterLanguage(cn = "正在检查结构", en = "Checking structure")
    private static final String CHECKING = "gtocore.top.checking";

    @RegisterLanguage(cn = "排队等待检查结构", en = "Waiting in line for check structure")
    private static final String WAITING = "gtocore.top.waiting";

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("hasError")) {
            if (blockAccessor.getServerData().getBoolean("hasError")) {
                iTooltip.add(Component.translatable("gtceu.top.invalid_structure").withStyle(ChatFormatting.RED));
                if (blockAccessor.getServerData().getBoolean("checking")) {
                    iTooltip.add(Component.translatable(CHECKING).withStyle(ChatFormatting.YELLOW));
                } else if (blockAccessor.getServerData().getBoolean("waiting")) {
                    iTooltip.add(Component.translatable(WAITING).withStyle(ChatFormatting.DARK_AQUA));
                } else {
                    for (var error : blockAccessor.getServerData().getList("error", Tag.TAG_STRING)) {
                        var c = Component.Serializer.fromJson(error.getAsString());
                        if (c != null) {
                            iTooltip.add(c);
                        }
                    }
                    if (ClientCache.highlightTime < 1 && blockAccessor.getServerData().contains("errorPos")) {
                        ClientCache.highlightTime = 200;
                        var pos = BlockPos.of(blockAccessor.getServerData().getLong("errorPos"));
                        EAEHighlightHandler.highlight(pos, blockAccessor.getLevel().dimension(), System.currentTimeMillis() + 10000);
                    }
                }
            } else {
                iTooltip.add(Component.translatable("gtceu.top.valid_structure").withStyle(ChatFormatting.GREEN));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof MetaMachineBlockEntity blockEntity && blockEntity.getMetaMachine() instanceof IMultiController controller) {
            if (controller.isFormed()) {
                compoundTag.putBoolean("hasError", false);
            } else {
                compoundTag.putBoolean("hasError", true);
                if (controller.checking()) {
                    compoundTag.putBoolean("checking", true);
                } else if (controller.getMultiblockState().hasError()) {
                    if (controller.getMultiblockState().error == MultiblockState.UNINIT_ERROR && controller.getWaitingTime() == 0) {
                        compoundTag.putBoolean("waiting", true);
                    } else {
                        var tag = new ListTag();
                        for (var error : StructureDetectBehavior.analysis(controller.getMultiblockState().error, controller.getMultiblockState().neededFlip)) {
                            tag.add(StringTag.valueOf(Component.Serializer.toJson(error)));
                        }
                        compoundTag.put("error", tag);
                        var pos = controller.getMultiblockState().error.getPos();
                        if (pos != null) {
                            compoundTag.putLong("errorPos", pos.asLong());
                        }
                    }
                } else {
                    compoundTag.putBoolean("waiting", true);
                }
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return GTCEu.id("multiblock_structure");
    }
}
