package com.gtocore.data.recipe.classified;

import com.gtolib.api.data.GTODimensions;

import static com.gtocore.common.data.GTORecipeTypes.CREATE_AGGREGATION_RECIPES;

final class CreateAggregation {

    public static void init() {
        CREATE_AGGREGATION_RECIPES.recipeBuilder("1")
                .circuitMeta(1)
                .EUt(32212254720L)
                .duration(20)
                .dimension(GTODimensions.CREATE)
                .save();
    }
}
