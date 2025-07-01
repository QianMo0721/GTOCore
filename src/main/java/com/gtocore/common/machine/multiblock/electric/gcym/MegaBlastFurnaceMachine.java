package com.gtocore.common.machine.multiblock.electric.gcym;

import com.gtolib.api.machine.feature.multiblock.ICoilMachine;
import com.gtolib.api.machine.trait.CoilTrait;

import com.gregtechceu.gtceu.api.block.ICoilType;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

public final class MegaBlastFurnaceMachine extends GCYMMultiblockMachine implements ICoilMachine {

    private final CoilTrait coilTrait;

    public MegaBlastFurnaceMachine(IMachineBlockEntity holder) {
        super(holder);
        coilTrait = new CoilTrait(this, true, true);
    }

    @Override
    public int gto$getTemperature() {
        return coilTrait.getTemperature();
    }

    @Override
    public int getCoilTier() {
        return coilTrait.getCoilTier();
    }

    @Override
    public ICoilType getCoilType() {
        return coilTrait.getCoilType();
    }
}
