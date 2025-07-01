package com.gtocore.data.recipe.classified;

import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import static com.gtocore.common.data.GTORecipeTypes.DESULFURIZER_RECIPES;

final class Desulfurizer {

    public static void init() {
        DESULFURIZER_RECIPES.recipeBuilder("naphtha")
                .inputFluids(GTMaterials.SulfuricNaphtha.getFluid(12000))
                .outputItems(TagPrefix.dust, GTMaterials.Sulfur)
                .outputFluids(GTMaterials.Naphtha.getFluid(12000))
                .EUt(30)
                .duration(120)
                .save();

        DESULFURIZER_RECIPES.recipeBuilder("light_fuel")
                .inputFluids(GTMaterials.SulfuricLightFuel.getFluid(12000))
                .outputItems(TagPrefix.dust, GTMaterials.Sulfur)
                .outputFluids(GTMaterials.LightFuel.getFluid(12000))
                .EUt(30)
                .duration(120)
                .save();

        DESULFURIZER_RECIPES.recipeBuilder("heavy_fuel")
                .inputFluids(GTMaterials.SulfuricHeavyFuel.getFluid(12000))
                .outputItems(TagPrefix.dust, GTMaterials.Sulfur)
                .outputFluids(GTMaterials.HeavyFuel.getFluid(12000))
                .EUt(30)
                .duration(120)
                .save();

        DESULFURIZER_RECIPES.recipeBuilder("gas")
                .inputFluids(GTMaterials.SulfuricGas.getFluid(12000))
                .outputItems(TagPrefix.dust, GTMaterials.Sulfur)
                .outputFluids(GTMaterials.RefineryGas.getFluid(12000))
                .EUt(30)
                .duration(120)
                .save();
    }
}
