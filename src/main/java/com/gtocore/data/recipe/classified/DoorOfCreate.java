package com.gtocore.data.recipe.classified;

import com.gtolib.api.data.GTODimensions;

import static com.gtocore.common.data.GTORecipeTypes.DOOR_OF_CREATE_RECIPES;

final class DoorOfCreate {

    public static void init() {
        DOOR_OF_CREATE_RECIPES.recipeBuilder("1")
                .circuitMeta(1)
                .EUt(8053063680L)
                .duration(20)
                .dimension(GTODimensions.OVERWORLD)
                .save();
    }
}
