package com.gtocore.integration.jade.provider;

import com.gtocore.common.machine.multiblock.part.ParallelHatchPartMachine;

import com.gtolib.api.machine.feature.multiblock.IParallelMachine;
import com.gtolib.api.recipe.RecipeHelper;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.SimpleGeneratorMachine;
import com.gregtechceu.gtceu.api.machine.feature.IRecipeLogicMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiController;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import java.util.Optional;

public final class ParallelProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        long parallel = blockAccessor.getServerData().getLong("parallel");
        if (parallel > 1) {
            Component parallels = Component.literal(FormattingUtil.formatNumbers(parallel)).withStyle(ChatFormatting.DARK_PURPLE);
            String key = "gtceu.multiblock.parallel";
            if (blockAccessor.getServerData().getBoolean("exact")) key += ".exact";
            iTooltip.add(Component.translatable(key, parallels));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof MetaMachineBlockEntity blockEntity) {
            MetaMachine machine = blockEntity.getMetaMachine();
            if (machine instanceof ParallelHatchPartMachine parallelHatchPartMachine) {
                compoundTag.putLong("parallel", parallelHatchPartMachine.getCurrentParallelLong());
                return;
            }
            if (machine instanceof SimpleGeneratorMachine) return;
            if (machine.getDefinition() instanceof MultiblockMachineDefinition multiblockMachineDefinition && multiblockMachineDefinition.isGenerator()) return;
            long parallel = getRecipeParallel(machine);
            if (parallel > 0) {
                compoundTag.putBoolean("exact", true);
            } else if (machine instanceof IMultiController controller) {
                if (controller instanceof IParallelMachine parallelHatch) {
                    parallel = parallelHatch.getParallelLong();
                } else {
                    Optional<IParallelHatch> parallelHatch = controller.getParallelHatch();
                    if (parallelHatch.isPresent()) {
                        parallel = ((ParallelHatchPartMachine) parallelHatch.get()).getCurrentParallelLong();
                    }
                }
            }
            if (parallel > 1) compoundTag.putLong("parallel", parallel);
        }
    }

    private static long getRecipeParallel(MetaMachine machine) {
        long parallel = 0;
        if (machine instanceof IRecipeLogicMachine rlm && rlm.getRecipeLogic().isActive() && rlm.getRecipeLogic().getLastRecipe() != null) {
            parallel = RecipeHelper.getParallel(rlm.getRecipeLogic().getLastRecipe());
        }
        return parallel;
    }

    @Override
    public ResourceLocation getUid() {
        return GTCEu.id("parallel_info");
    }
}
