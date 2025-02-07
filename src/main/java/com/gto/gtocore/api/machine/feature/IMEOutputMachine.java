package com.gto.gtocore.api.machine.feature;

public interface IMEOutputMachine {

    default boolean gTOCore$isItemOutput() {
        return false;
    }

    default boolean gTOCore$isFluidOutput() {
        return false;
    }
}
