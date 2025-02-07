package com.gto.gtocore.common.machine.multiblock.part;

import com.gto.gtocore.api.machine.part.AmountConfigurationHatchPartMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.IParallelHatch;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

public final class ParallelHatchPartMachine extends AmountConfigurationHatchPartMachine implements IParallelHatch {

    public ParallelHatchPartMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier, 1, 1 << (2 * (tier - GTValues.HV)));
    }

    @Override
    public int getCurrentParallel() {
        return getCurrent();
    }
}
