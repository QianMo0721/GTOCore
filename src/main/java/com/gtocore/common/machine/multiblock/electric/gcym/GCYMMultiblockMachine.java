package com.gtocore.common.machine.multiblock.electric.gcym;

import com.gtolib.api.GTOValues;
import com.gtolib.api.machine.multiblock.TierCasingMultiblockMachine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

public class GCYMMultiblockMachine extends TierCasingMultiblockMachine {

    public GCYMMultiblockMachine(IMachineBlockEntity holder) {
        super(holder, GTOValues.INTEGRAL_FRAMEWORK_TIER);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        tier = Math.min(getCasingTier(GTOValues.INTEGRAL_FRAMEWORK_TIER), tier);
    }

    @Override
    public boolean gtocore$canUpgraded() {
        return true;
    }
}
