package com.gto.gtocore.data.recipe;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.common.data.GTOMachines;
import com.gto.gtocore.common.data.GTOMaterials;
import com.gto.gtocore.data.CraftingComponents;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.UnificationEntry;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.machines.GTMachineUtils;
import com.gregtechceu.gtceu.data.recipe.VanillaRecipeHelper;

import net.minecraft.data.recipes.FinishedRecipe;

import com.hepdd.gtmthings.data.CustomMachines;

import java.util.function.Consumer;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.DUAL_EXPORT_HATCH;
import static com.gregtechceu.gtceu.common.data.GTMachines.DUAL_IMPORT_HATCH;
import static com.gregtechceu.gtceu.common.data.GTRecipeTypes.ASSEMBLER_RECIPES;
import static com.gregtechceu.gtceu.data.recipe.GTCraftingComponents.*;

final class HatchRecipe {

    public static void init(Consumer<FinishedRecipe> provider) {
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTOCore.id("rotor_holder_uhv"), GTMachines.ROTOR_HOLDER[UHV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[UHV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, GTOMaterials.Orichalcum), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTMaterials.Neutronium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTOCore.id("rotor_holder_uev"), GTMachines.ROTOR_HOLDER[UEV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[UEV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, GTOMaterials.AstralTitanium), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTOMaterials.Quantanium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTOCore.id("rotor_holder_uiv"), GTMachines.ROTOR_HOLDER[UIV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[UIV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, GTOMaterials.CelestialTungsten), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTOMaterials.Infuscolium));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTOCore.id("rotor_holder_uxv"), GTMachines.ROTOR_HOLDER[UXV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[UXV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, GTOMaterials.Vibramantium), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTOMaterials.HastelloyK243));
        VanillaRecipeHelper.addShapedRecipe(provider, true, GTOCore.id("rotor_holder_opv"), GTMachines.ROTOR_HOLDER[OpV].asStack(),
                "SGS", "GHG", "SGS", 'H', GTMachines.HULL[OpV].asStack(), 'G',
                new UnificationEntry(TagPrefix.gear, GTOMaterials.HexaphaseCopper), 'S',
                new UnificationEntry(TagPrefix.gearSmall, GTOMaterials.TranscendentMetal));

        for (int tier : tiersBetween(LV, MAX)) {
            ASSEMBLER_RECIPES.recipeBuilder(GTOCore.id("dual_import_bus_" + VN[tier].toLowerCase()))
                    .inputItems(FRAME.get(tier))
                    .inputItems(GTMachines.ITEM_IMPORT_BUS[tier].asStack())
                    .inputItems(GTMachines.FLUID_IMPORT_HATCH[tier].asStack())
                    .inputItems(CraftingComponents.BUFFER.get(tier))
                    .inputItems(PIPE_NONUPLE.get(tier))
                    .circuitMeta(1)
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(DUAL_IMPORT_HATCH[tier].asStack())
                    .duration(300)
                    .EUt(VA[tier])
                    .save(provider);

            ASSEMBLER_RECIPES.recipeBuilder(GTOCore.id("dual_export_bus_" + VN[tier].toLowerCase()))
                    .inputItems(FRAME.get(tier))
                    .inputItems(GTMachines.ITEM_IMPORT_BUS[tier].asStack())
                    .inputItems(GTMachines.FLUID_IMPORT_HATCH[tier].asStack())
                    .inputItems(CraftingComponents.BUFFER.get(tier))
                    .inputItems(PIPE_NONUPLE.get(tier))
                    .circuitMeta(2)
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(144))
                    .outputItems(DUAL_EXPORT_HATCH[tier].asStack())
                    .duration(300)
                    .EUt(VA[tier])
                    .save(provider);

            if (tier == MAX) continue;
            ASSEMBLER_RECIPES.recipeBuilder(GTOCore.id("huge_dual_hatch_" + VN[tier].toLowerCase()))
                    .inputItems(FRAME.get(tier), 4)
                    .inputItems(CustomMachines.HUGE_ITEM_IMPORT_BUS[tier].asStack())
                    .inputItems(GTOMachines.HUGE_FLUID_IMPORT_HATCH[tier].asStack())
                    .inputItems(GTMachines.BUFFER[tier].asStack(4))
                    .inputItems(PIPE_LARGE.get(tier), 4)
                    .inputItems(GLASS.get(tier))
                    .outputItems(CustomMachines.HUGE_INPUT_DUAL_HATCH[tier].asStack())
                    .inputFluids(GTMaterials.SolderingAlloy.getFluid(576))
                    .duration(600)
                    .EUt(VA[tier + 1])
                    .save(provider);
        }

        for (int tier = LV; tier < LuV; tier++) {
            String tierName = VN[tier].toLowerCase();
            MachineDefinition inputBuffer = DUAL_IMPORT_HATCH[tier];
            MachineDefinition outputBuffer = DUAL_EXPORT_HATCH[tier];
            VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("dual_hatch_output_to_input_" + tierName), inputBuffer.asStack(), "d", "B", 'B', outputBuffer.asStack());
            VanillaRecipeHelper.addShapedRecipe(provider, GTOCore.id("dual_hatch_input_to_output_" + tierName), outputBuffer.asStack(), "d", "B", 'B', inputBuffer.asStack());
        }

        Material[] multiHatchMaterials = {
                GTMaterials.Neutronium, GTOMaterials.Enderium, GTOMaterials.Enderium,
                GTOMaterials.HeavyQuarkDegenerateMatter,
                GTOMaterials.HeavyQuarkDegenerateMatter,
        };
        for (int i = 0; i < multiHatchMaterials.length; i++) {
            var tier = GTMachineUtils.MULTI_HATCH_TIERS[i + 6];
            var tierName = VN[tier].toLowerCase();

            var material = multiHatchMaterials[i];

            var importHatch = GTMachines.FLUID_IMPORT_HATCH[tier];
            var exportHatch = GTMachines.FLUID_EXPORT_HATCH[tier];

            var importHatch4x = GTMachines.FLUID_IMPORT_HATCH_4X[tier];
            var exportHatch4x = GTMachines.FLUID_EXPORT_HATCH_4X[tier];
            var importHatch9x = GTMachines.FLUID_IMPORT_HATCH_9X[tier];
            var exportHatch9x = GTMachines.FLUID_EXPORT_HATCH_9X[tier];

            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTOCore.id("fluid_import_hatch_4x_" + tierName),
                    importHatch4x.asStack(), "P", "M",
                    'M', importHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeQuadrupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTOCore.id("fluid_export_hatch_4x_" + tierName),
                    exportHatch4x.asStack(), "M", "P",
                    'M', exportHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeQuadrupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTOCore.id("fluid_import_hatch_9x_" + tierName),
                    importHatch9x.asStack(), "P", "M",
                    'M', importHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeNonupleFluid, material));
            VanillaRecipeHelper.addShapedRecipe(
                    provider, true, GTOCore.id("fluid_export_hatch_9x_" + tierName),
                    exportHatch9x.asStack(), "M", "P",
                    'M', exportHatch.asStack(),
                    'P', new UnificationEntry(TagPrefix.pipeNonupleFluid, material));
        }

        for (int tier = 1; tier < 4; tier++) {
            var hatch = GTOMachines.ENERGY_INPUT_HATCH_4A[tier];

            ASSEMBLER_RECIPES.recipeBuilder("energy_hatch_4a_" + VN[tier].toLowerCase())
                    .inputItems(GTMachines.ENERGY_INPUT_HATCH[tier])
                    .inputItems(WIRE_QUAD.get(tier), 2)
                    .inputItems(PLATE.get(tier), 2)
                    .outputItems(hatch)
                    .duration(100).EUt(VA[tier]).save(provider);
        }

        for (int tier = 1; tier < 4; tier++) {
            MachineDefinition hatch = GTOMachines.ENERGY_INPUT_HATCH_16A[tier];
            MachineDefinition transformer;
            transformer = GTMachines.TRANSFORMER[tier];
            ASSEMBLER_RECIPES.recipeBuilder(GTOCore.id("energy_hatch_16a_" + VN[tier].toLowerCase()))
                    .inputItems(transformer)
                    .inputItems(GTOMachines.ENERGY_INPUT_HATCH_4A[tier])
                    .inputItems(WIRE_OCT.get(tier), 2)
                    .inputItems(PLATE.get(tier), 4)
                    .outputItems(hatch)
                    .duration(200).EUt(VA[tier]).save(provider);
        }

        for (int tier = 1; tier < 4; tier++) {
            var hatch = GTOMachines.ENERGY_OUTPUT_HATCH_4A[tier];
            ASSEMBLER_RECIPES.recipeBuilder(GTOCore.id("dynamo_hatch_4a_" + VN[tier].toLowerCase()))
                    .inputItems(GTMachines.ENERGY_OUTPUT_HATCH[tier])
                    .inputItems(WIRE_QUAD.get(tier), 2)
                    .inputItems(PLATE.get(tier), 2)
                    .outputItems(hatch)
                    .duration(100)
                    .EUt(VA[tier])
                    .save(provider);
        }

        for (int tier = 1; tier < 4; tier++) {
            MachineDefinition hatch = GTOMachines.ENERGY_OUTPUT_HATCH_16A[tier];

            MachineDefinition transformer;
            transformer = GTMachines.TRANSFORMER[tier];

            ASSEMBLER_RECIPES.recipeBuilder(GTOCore.id("dynamo_hatch_16a_" + VN[tier].toLowerCase()))
                    .inputItems(transformer)
                    .inputItems(GTOMachines.ENERGY_OUTPUT_HATCH_4A[tier])
                    .inputItems(WIRE_OCT.get(tier), 2)
                    .inputItems(PLATE.get(tier), 4)
                    .outputItems(hatch)
                    .duration(200).EUt(VA[tier]).save(provider);
        }
    }
}
