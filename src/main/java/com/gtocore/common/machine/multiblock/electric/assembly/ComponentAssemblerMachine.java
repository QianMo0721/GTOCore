package com.gtocore.common.machine.multiblock.electric.assembly;

import com.gtolib.api.machine.multiblock.TierCasingMultiblockMachine;
import com.gtolib.api.recipe.IdleReason;
import com.gtolib.api.recipe.Recipe;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;

import static com.gtolib.api.GTOValues.COMPONENT_ASSEMBLY_CASING_TIER;

public final class ComponentAssemblerMachine extends TierCasingMultiblockMachine {

    private int maxCasingTier = GTValues.IV;

    public ComponentAssemblerMachine(MetaMachineBlockEntity holder) {
        super(holder, COMPONENT_ASSEMBLY_CASING_TIER);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        if (formeds > 0) maxCasingTier = GTValues.UV;
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        maxCasingTier = GTValues.IV;
    }

    @Override
    protected Recipe getRealRecipe(Recipe recipe) {
        if (recipe.data.getInt(COMPONENT_ASSEMBLY_CASING_TIER) >= maxCasingTier) {
            setIdleReason(IdleReason.VOLTAGE_TIER_NOT_SATISFIES);
            return null;
        }
        return super.getRealRecipe(recipe);
    }
}
