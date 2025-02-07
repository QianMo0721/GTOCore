package com.gto.gtocore.api.machine.feature;

import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;

import net.minecraft.core.BlockPos;

import java.util.Set;

public interface IFluidRendererMachine {

    default WorkableMultiblockMachine getMultiblock() {
        return (WorkableMultiblockMachine) this;
    }

    Set<BlockPos> getFluidBlockOffsets();
}
