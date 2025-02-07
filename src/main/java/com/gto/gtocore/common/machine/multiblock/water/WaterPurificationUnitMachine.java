package com.gto.gtocore.common.machine.multiblock.water;

import com.gto.gtocore.api.machine.multiblock.NoEnergyCustomParallelMultiblockMachine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.sound.SoundEntry;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;

import net.minecraft.MethodsReturnNonnullByDefault;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

@MethodsReturnNonnullByDefault
public abstract class WaterPurificationUnitMachine extends NoEnergyCustomParallelMultiblockMachine {

    static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            WaterPurificationUnitMachine.class, NoEnergyCustomParallelMultiblockMachine.MANAGED_FIELD_HOLDER);

    abstract long before();

    GTRecipe recipe;

    @Persisted
    long eut;

    WaterPurificationUnitMachine(IMachineBlockEntity holder) {
        super(holder, false, m -> Integer.MAX_VALUE);
    }

    void setWorking(boolean isWorkingAllowed) {
        super.setWorkingEnabled(isWorkingAllowed);
    }

    @Override
    public SoundEntry getSound() {
        return GTSoundEntries.COOLING;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {}

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }
}
