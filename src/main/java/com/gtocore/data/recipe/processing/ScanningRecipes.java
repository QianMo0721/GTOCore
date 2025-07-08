package com.gtocore.data.recipe.processing;

import com.gtolib.api.data.chemical.GTOChemicalHelper;
import com.gtolib.api.recipe.builder.DataCrystalConstruction;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.world.item.ItemStack;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.api.GTValues.IV;

public final class ScanningRecipes {

    public static void init() {
        DataCrystalConstruction.buildDataCrystal(false)
                .inputScanning(new ItemStack(GTOChemicalHelper.getItem(TagPrefix.dust, GTMaterials.Naquadah)), 1, 1)
                .EUt(VA[IV])
                .duration(200)
                .save();
    }
}
