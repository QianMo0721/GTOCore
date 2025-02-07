package com.gto.gtocore.mixin.gtm.recipe;

import com.gregtechceu.gtceu.data.recipe.serialized.chemistry.PlatGroupMetalsRecipes;

import net.minecraft.data.recipes.FinishedRecipe;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.function.Consumer;

@Mixin(PlatGroupMetalsRecipes.class)
public final class PlatGroupMetalsRecipesMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static void init(Consumer<FinishedRecipe> provider) {}
}
