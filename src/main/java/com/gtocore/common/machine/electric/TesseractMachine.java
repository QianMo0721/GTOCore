package com.gtocore.common.machine.electric;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.core.BlockPos;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

public class TesseractMachine extends MetaMachine {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            TesseractMachine.class, MetaMachine.MANAGED_FIELD_HOLDER);

    @Persisted
    public BlockPos pos;

    public TesseractMachine(IMachineBlockEntity holder) {
        super(holder);
    }
}
