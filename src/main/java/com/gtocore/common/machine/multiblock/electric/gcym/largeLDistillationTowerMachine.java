package com.gtocore.common.machine.multiblock.electric.gcym;

import com.gtocore.common.machine.multiblock.electric.DistillationTowerMachine;

import com.gtolib.api.GTOValues;
import com.gtolib.api.machine.feature.multiblock.ITierCasingMachine;
import com.gtolib.api.machine.trait.TierCasingTrait;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

import it.unimi.dsi.fastutil.objects.Object2IntMap;

public final class largeLDistillationTowerMachine extends DistillationTowerMachine implements ITierCasingMachine {

    private final TierCasingTrait tierCasingTrait;

    public largeLDistillationTowerMachine(IMachineBlockEntity holder) {
        super(holder);
        tierCasingTrait = new TierCasingTrait(this, GTOValues.INTEGRAL_FRAMEWORK_TIER);
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        tier = Math.min(getCasingTier(GTOValues.INTEGRAL_FRAMEWORK_TIER), tier);
    }

    @Override
    public boolean gtocore$canUpgraded() {
        return true;
    }

    @Override
    public Object2IntMap<String> getCasingTiers() {
        return tierCasingTrait.getCasingTiers();
    }
}
