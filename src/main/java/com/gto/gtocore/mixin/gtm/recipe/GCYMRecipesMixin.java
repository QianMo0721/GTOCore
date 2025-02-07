package com.gto.gtocore.mixin.gtm.recipe;

import com.gregtechceu.gtceu.data.recipe.misc.GCYMRecipes;

import net.minecraft.data.recipes.FinishedRecipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Consumer;

@Mixin(GCYMRecipes.class)
public class GCYMRecipesMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    private static void registerMultiblockControllerRecipes(Consumer<FinishedRecipe> provider) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    private static void registerPartsRecipes(Consumer<FinishedRecipe> provider) {}
}
