package com.gtocore.common.data;

import com.gregtechceu.gtceu.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gtceu.common.data.GTRecipeCategories;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

public final class GTORecipeCategories {

    public static void init() {}

    public static final GTRecipeCategory MANA_ASSEMBLER = GTRecipeCategories.register("mana_assembler", GTRecipeTypes.ASSEMBLER_RECIPES);
}
