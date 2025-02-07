package com.gto.gtocore.utils;

import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;

public final class FluidUtils {

    public static String getId(Fluid fluid) {
        return getIdLocation(fluid).toString();
    }

    public static ResourceLocation getIdLocation(Fluid fluid) {
        return ForgeRegistries.FLUIDS.getKey(fluid);
    }

    public static Fluid getFirst(FluidIngredient fluidIngredient) {
        for (FluidIngredient.Value value : fluidIngredient.values) {
            for (Fluid fluid : value.getFluids()) {
                return fluid;
            }
        }
        return null;
    }
}
