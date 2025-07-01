package com.gtocore.integration.jade.provider;

import com.gtolib.api.annotation.Scanned;
import com.gtolib.api.annotation.language.RegisterLanguage;
import com.gtolib.api.machine.feature.multiblock.ICheckPatternMachine;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Scanned
public final class MultiblockStructureProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @RegisterLanguage(cn = "正在检查结构", en = "Checking structure")
    private static final String CHECKING = "gtocore.top.checking";

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        if (blockAccessor.getServerData().contains("hasError")) {
            boolean hasError = blockAccessor.getServerData().getBoolean("hasError");
            if (hasError) {
                iTooltip.add(Component.translatable("gtceu.top.invalid_structure").withStyle(ChatFormatting.RED));
                if (blockAccessor.getServerData().getBoolean("checking")) {
                    iTooltip.add(Component.translatable(CHECKING).withStyle(ChatFormatting.YELLOW));
                }
            } else {
                iTooltip.add(Component.translatable("gtceu.top.valid_structure").withStyle(ChatFormatting.GREEN));
            }
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof MetaMachineBlockEntity blockEntity) {
            if (blockEntity.getMetaMachine() instanceof ICheckPatternMachine controller) {
                compoundTag.putBoolean("hasError", !controller.isFormed());
                compoundTag.putBoolean("checking", controller.gtocore$Checking());
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return GTCEu.id("multiblock_structure");
    }
}
