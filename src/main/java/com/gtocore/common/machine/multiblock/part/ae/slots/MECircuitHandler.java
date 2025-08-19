package com.gtocore.common.machine.multiblock.part.ae.slots;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.CircuitHandler;

public final class MECircuitHandler extends CircuitHandler {

    public MECircuitHandler(MetaMachine machine) {
        super(machine);
    }

    @Override
    public boolean skipParallelComputing() {
        return true;
    }
}
