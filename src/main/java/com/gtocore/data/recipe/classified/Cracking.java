package com.gtocore.data.recipe.classified;

import com.gtocore.common.data.GTOMaterials;

import com.gregtechceu.gtceu.api.fluids.store.FluidStorageKeys;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import static com.gtocore.common.data.GTORecipeTypes.CRACKING_RECIPES;

final class Cracking {

    public static void init() {
        CRACKING_RECIPES.recipeBuilder("radon_cracked_enriched_aquadah")
                .circuitMeta(1)
                .inputFluids(GTMaterials.EnrichedNaquadahSolution.getFluid(1000))
                .inputFluids(GTMaterials.Radon.getFluid(1000))
                .outputFluids(GTOMaterials.RadonCrackedEnrichedAquadah.getFluid(1000))
                .EUt(1966080)
                .duration(160)
                .save();

        CRACKING_RECIPES.recipeBuilder("steam_cracked_turpentine")
                .circuitMeta(1)
                .inputFluids(GTOMaterials.LeachedTurpentine.getFluid(1000))
                .inputFluids(GTMaterials.Steam.getFluid(1000))
                .outputFluids(GTOMaterials.SteamCrackedTurpentine.getFluid(1000))
                .EUt(480)
                .duration(200)
                .save();

        CRACKING_RECIPES.recipeBuilder("crackedradox")
                .circuitMeta(1)
                .inputFluids(GTOMaterials.SuperLightRadox.getFluid(100))
                .inputFluids(GTMaterials.Silver.getFluid(FluidStorageKeys.PLASMA, 10))
                .outputFluids(GTOMaterials.CrackedRadox.getFluid(100))
                .EUt(491520)
                .duration(300)
                .save();

        CRACKING_RECIPES.recipeBuilder("lightradox")
                .circuitMeta(1)
                .inputFluids(GTOMaterials.HeavyRadox.getFluid(100))
                .inputFluids(GTMaterials.Silver.getFluid(FluidStorageKeys.PLASMA, 10))
                .outputFluids(GTOMaterials.LightRadox.getFluid(20))
                .EUt(491520)
                .duration(200)
                .save();

        CRACKING_RECIPES.recipeBuilder("superlightradox")
                .circuitMeta(1)
                .inputFluids(GTOMaterials.LightRadox.getFluid(100))
                .inputFluids(GTMaterials.Silver.getFluid(FluidStorageKeys.PLASMA, 10))
                .outputFluids(GTOMaterials.SuperLightRadox.getFluid(50))
                .EUt(491520)
                .duration(300)
                .save();

        CRACKING_RECIPES.recipeBuilder("fluorine_cracked_aquadah")
                .circuitMeta(1)
                .inputFluids(GTOMaterials.NaquadahSolution.getFluid(1000))
                .inputFluids(GTMaterials.Fluorine.getFluid(1000))
                .outputFluids(GTOMaterials.FluorineCrackedAquadah.getFluid(1000))
                .EUt(491520)
                .duration(120)
                .save();
    }
}
