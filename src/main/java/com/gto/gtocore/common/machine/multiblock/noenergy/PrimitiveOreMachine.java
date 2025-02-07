package com.gto.gtocore.common.machine.multiblock.noenergy;

import com.gto.gtocore.common.data.GTOOres;
import com.gto.gtocore.utils.MachineUtils;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class PrimitiveOreMachine extends WorkableMultiblockMachine {

    private int delay = 1;

    public PrimitiveOreMachine(IMachineBlockEntity holder, Object... args) {
        super(holder, args);
    }

    public static Material selectMaterial(Map<Material, Integer> materials) {
        int totalWeight = materials.values().stream().mapToInt(Integer::intValue).sum();
        List<Material> materialList = new ArrayList<>(materials.keySet());
        List<Integer> cumulativeWeights = new ArrayList<>();
        int cumulativeSum = 0;
        for (Integer weight : materials.values()) {
            cumulativeSum += weight;
            cumulativeWeights.add(cumulativeSum);
        }
        int randomValue = GTValues.RNG.nextInt(totalWeight);
        for (int i = 0; i < cumulativeWeights.size(); i++) {
            if (randomValue < cumulativeWeights.get(i)) {
                return materialList.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean onWorking() {
        if (!super.onWorking()) return false;
        if (getOffsetTimer() % delay == 0) {
            if (MachineUtils.outputItem(this, ChemicalHelper.get(TagPrefix.rawOre, selectMaterial(GTOOres.ALL_ORES.get(Objects.requireNonNull(getLevel()).dimension()))).copyWithCount(64))) {
                delay = 1;
            } else if (delay < 20) {
                delay++;
            }
        }
        return true;
    }
}
