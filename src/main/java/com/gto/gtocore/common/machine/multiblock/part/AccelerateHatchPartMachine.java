package com.gto.gtocore.common.machine.multiblock.part;

import com.gto.gtocore.api.machine.part.AmountConfigurationHatchPartMachine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;

public final class AccelerateHatchPartMachine extends AmountConfigurationHatchPartMachine {

    public AccelerateHatchPartMachine(IMachineBlockEntity holder, int tier) {
        super(holder, tier, 100 - 5 * tier, 100);
    }

    @Override
    public GTRecipe modifyRecipe(GTRecipe recipe) {
        recipe.duration = recipe.duration * 100 / getCurrent();
        return recipe;
    }
}
