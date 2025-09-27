package com.gtocore.common.machine.multiblock.part.ae.slots;

import com.gtolib.api.machine.trait.NonStandardHandler;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.CircuitHandler;

public final class MECircuitHandler extends CircuitHandler implements NonStandardHandler {

    public MECircuitHandler(MetaMachine machine) {
        super(machine);
    }

    @Override
    public boolean skipParallelComputing() {
        return true;
    }
}
