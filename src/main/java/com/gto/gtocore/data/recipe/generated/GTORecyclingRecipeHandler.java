package com.gto.gtocore.data.recipe.generated;

import com.gto.gtocore.api.data.tag.GTOTagPrefix;

import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.data.recipe.generated.RecyclingRecipeHandler;

import net.minecraft.data.recipes.FinishedRecipe;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;

public final class GTORecyclingRecipeHandler {

    private static final List<Object> PREFIXES = Arrays.asList(
            ingot, gem, rod, plate, ring, rodLong, foil, bolt, screw,
            nugget, gearSmall, gear, frameGt, plateDense, spring, springSmall,
            block, wireFine, rotor, lens, turbineBlade, round, plateDouble, dust,
            GTOTagPrefix.curvedPlate, GTOTagPrefix.motorEnclosure, GTOTagPrefix.pumpBarrel,
            GTOTagPrefix.pistonHousing, GTOTagPrefix.emitterBases, GTOTagPrefix.sensorCasing,
            GTOTagPrefix.fieldGeneratorCasing,
            (Predicate<TagPrefix>) orePrefix -> orePrefix.name().startsWith("gem"),
            (Predicate<TagPrefix>) orePrefix -> orePrefix.name().startsWith("wireGt"),
            (Predicate<TagPrefix>) orePrefix -> orePrefix.name().startsWith("pipe"));

    public static void init(Consumer<FinishedRecipe> provider) {
        for (TagPrefix orePrefix : values()) {
            if (PREFIXES.stream().anyMatch(object -> {
                if (object instanceof TagPrefix)
                    return object == orePrefix;
                else if (object instanceof Predicate)
                    return ((Predicate<TagPrefix>) object).test(orePrefix);
                else return false;
            })) orePrefix.executeHandler(provider, PropertyKey.DUST, RecyclingRecipeHandler::processCrushing);
        }
    }
}
