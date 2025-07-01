package com.gtocore.data.recipe.classified;

import com.gtocore.common.data.GTOMaterials;

import com.gregtechceu.gtceu.common.data.GTMaterials;

import static com.gtocore.common.data.GTORecipeTypes.WATER_PURIFICATION_PLANT_RECIPES;

final class WaterPurificationPlant {

    public static void init() {
        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("a")
                .inputFluids(GTMaterials.Water.getFluid(1000))
                .outputFluids(GTOMaterials.FilteredSater.getFluid(1000))
                .duration(2400)
                .addData("tier", 1)
                .save();

        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("b")
                .inputFluids(GTOMaterials.FilteredSater.getFluid(1000))
                .outputFluids(GTOMaterials.OzoneWater.getFluid(1000))
                .duration(2400)
                .addData("tier", 2)
                .save();

        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("c")
                .inputFluids(GTOMaterials.OzoneWater.getFluid(1000))
                .outputFluids(GTOMaterials.FlocculentWater.getFluid(1000))
                .duration(2400)
                .addData("tier", 3)
                .save();

        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("d")
                .inputFluids(GTOMaterials.FlocculentWater.getFluid(1000))
                .outputFluids(GTOMaterials.PHNeutralWater.getFluid(1000))
                .duration(2400)
                .addData("tier", 4)
                .save();

        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("e")
                .inputFluids(GTOMaterials.PHNeutralWater.getFluid(1000))
                .outputFluids(GTOMaterials.ExtremeTemperatureWater.getFluid(1000))
                .duration(2400)
                .addData("tier", 5)
                .save();

        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("f")
                .inputFluids(GTOMaterials.ExtremeTemperatureWater.getFluid(1000))
                .outputFluids(GTOMaterials.ElectricEquilibriumWater.getFluid(1000))
                .duration(2400)
                .addData("tier", 6)
                .save();

        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("g")
                .inputFluids(GTOMaterials.ElectricEquilibriumWater.getFluid(1000))
                .outputFluids(GTOMaterials.DegassedWater.getFluid(1000))
                .duration(2400)
                .addData("tier", 7)
                .save();

        WATER_PURIFICATION_PLANT_RECIPES.recipeBuilder("h")
                .inputFluids(GTOMaterials.DegassedWater.getFluid(1000))
                .outputFluids(GTOMaterials.BaryonicPerfectionWater.getFluid(1000))
                .duration(2400)
                .addData("tier", 8)
                .save();
    }
}
