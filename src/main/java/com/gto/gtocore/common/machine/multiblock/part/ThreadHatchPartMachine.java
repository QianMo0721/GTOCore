package com.gto.gtocore.common.machine.multiblock.part;

import com.gto.gtocore.api.machine.part.AmountConfigurationHatchPartMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

public final class ThreadHatchPartMachine extends AmountConfigurationHatchPartMachine {

    public ThreadHatchPartMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier, 1, 1 << (tier - GTValues.ZPM));
    }

    public int getCurrentThread() {
        return getCurrent();
    }
}
