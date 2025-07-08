package com.gtocore.common.machine.multiblock.electric;

import com.gtocore.common.machine.multiblock.part.ScanningHolderMachine;

import com.gregtechceu.gtceu.api.capability.IOpticalComputationProvider;
import com.gregtechceu.gtceu.api.capability.IOpticalComputationReceiver;
import com.gregtechceu.gtceu.api.capability.forge.GTCapability;
import com.gregtechceu.gtceu.api.capability.recipe.CWURecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDisplayUIMachine;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.MultiblockDisplayText;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.ActionResult;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ScanningStationMachine extends WorkableElectricMultiblockMachine
                                    implements IOpticalComputationReceiver, IDisplayUIMachine {

    @Override
    public IOpticalComputationProvider getComputationProvider() {
        return computationProvider;
    }

    public ScanningHolderMachine getScanningHolder() {
        return scanningHolder;
    }

    private IOpticalComputationProvider computationProvider;
    private ScanningHolderMachine scanningHolder;

    public ScanningStationMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    @Override
    protected RecipeLogic createRecipeLogic(Object... args) {
        return new ScanningStationRecipeLogic(this);
    }

    @Override
    public ScanningStationRecipeLogic getRecipeLogic() {
        return (ScanningStationRecipeLogic) super.getRecipeLogic();
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        for (IMultiPart part : getParts()) {
            // 扫描部件必须是ScanningHolderMachine且朝向正确
            if (part instanceof ScanningHolderMachine holder) {
                if (holder.getFrontFacing() != getFrontFacing().getOpposite()) {
                    onStructureInvalid();
                    return;
                }
                this.scanningHolder = holder;
                // 添加物品处理器（包含扫描槽、催化剂槽和数据槽）
                addHandlerList(RecipeHandlerList.of(IO.IN, holder.getAsHandler()));
            }

            // 获取计算提供者
            part.self().holder.self()
                    .getCapability(GTCapability.CAPABILITY_COMPUTATION_PROVIDER)
                    .ifPresent(provider -> this.computationProvider = provider);
        }

        // 必须同时有扫描部件和计算提供者
        if (computationProvider == null || scanningHolder == null) {
            onStructureInvalid();
        }
    }

    @Override
    public void onStructureInvalid() {
        computationProvider = null;
        // 重置扫描部件状态
        if (scanningHolder != null) {
            scanningHolder.setLocked(false);
            scanningHolder = null;
        }
        super.onStructureInvalid();
    }

    @Override
    public boolean regressWhenWaiting() {
        return false;
    }

    @Override
    public void addDisplayText(List<Component> textList) {
        MultiblockDisplayText.builder(textList, isFormed())
                .setWorkingStatus(recipeLogic.isWorkingEnabled(), recipeLogic.isActive())
                .setWorkingStatusKeys("gtceu.multiblock.idling", "gtceu.multiblock.work_paused",
                        "gtocore.machine.scanning")
                .addEnergyUsageLine(energyContainer)
                .addEnergyTierLine(tier)
                .addWorkingStatusLine()
                .addProgressLineOnlyPercent(recipeLogic.getProgressPercent());
    }

    public static class ScanningStationRecipeLogic extends RecipeLogic {

        public ScanningStationRecipeLogic(ScanningStationMachine metaTileEntity) {
            super(metaTileEntity);
        }

        @NotNull
        @Override
        public ScanningStationMachine getMachine() {
            return (ScanningStationMachine) super.getMachine();
        }

        @Override
        public boolean checkMatchedRecipeAvailable(GTRecipe match) {
            // 检查数据槽是否为空（最多只能放1个物品）
            if (!getMachine().getScanningHolder().getDataItem(false).isEmpty()) {
                return false;
            }

            var modified = machine.fullModifyRecipe(match);
            if (modified != null) {
                // 检查计算单元需求
                if (!modified.inputs.containsKey(CWURecipeCapability.CAP) &&
                        !modified.tickInputs.containsKey(CWURecipeCapability.CAP)) {
                    return true;
                }
                return checkRecipe(modified).isSuccess();
            }
            return false;
        }

        // 处理配方输入输出
        @Override
        protected ActionResult handleRecipeIO(GTRecipe recipe, IO io) {
            ScanningStationMachine machine = getMachine();
            ScanningHolderMachine holder = machine.getScanningHolder();

            if (io == IO.IN) {
                // 锁定扫描部件
                holder.setLocked(true);
                return ActionResult.SUCCESS;
            }

            // 处理输出
            if (lastRecipe == null) {
                holder.setLocked(false);
                return ActionResult.SUCCESS;
            }

            // 设置输出数据物品（前提是数据槽为空）
            if (holder.getDataItem(false).isEmpty()) {
                ItemStack outputItem = getOutputItem(recipe);
                if (!outputItem.isEmpty()) {
                    // 直接设置到数据槽
                    holder.setDataItem(outputItem);
                }
            } else {
                // 数据槽非空，无法输出
                return ActionResult.FAIL_NO_REASON;
            }

            // 解锁扫描部件
            holder.setLocked(false);
            return ActionResult.SUCCESS;
        }

        // 获取输出物品
        private ItemStack getOutputItem(GTRecipe recipe) {
            var contents = recipe.getOutputContents(ItemRecipeCapability.CAP);
            if (!contents.isEmpty()) {
                return ItemRecipeCapability.CAP.of(contents.get(0).content).getItems()[0];
            }
            return ItemStack.EMPTY;
        }
    }
}
