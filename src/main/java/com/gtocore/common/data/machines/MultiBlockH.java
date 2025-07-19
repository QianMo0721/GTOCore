package com.gtocore.common.data.machines;

import com.gtocore.api.machine.part.GTOPartAbility;
import com.gtocore.api.pattern.GTOPredicates;
import com.gtocore.common.data.*;
import com.gtocore.common.machine.multiblock.electric.processing.EncapsulatorExecutionModuleMachine;
import com.gtocore.common.machine.multiblock.electric.processing.ProcessingEncapsulatorMachine;

import com.gtolib.GTOCore;
import com.gtolib.api.machine.multiblock.NoRecipeLogicMultiblockMachine;
import com.gtolib.utils.MultiBlockFileReader;
import com.gtolib.utils.RegistriesUtils;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.common.data.GCYMBlocks;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;

import net.minecraft.world.level.block.Blocks;

import static com.gregtechceu.gtceu.api.machine.multiblock.PartAbility.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gtolib.utils.register.MachineRegisterUtils.multiblock;

public final class MultiBlockH {

    public static void init() {}

    public static final MultiblockMachineDefinition LHC = multiblock("lhc", "LHC", NoRecipeLogicMultiblockMachine::new)
            .nonYAxisRotation()
            .recipeTypes(GTORecipeTypes.DUMMY_RECIPES)
            .block(GTOBlocks.IRIDIUM_CASING)
            .pattern(definition -> MultiBlockFileReader.start(definition, RelativeDirection.BACK, RelativeDirection.UP, RelativeDirection.LEFT)
                    .where('A', blocks(GTOBlocks.IRIDIUM_CASING.get()))
                    .where('B', blocks(GTOBlocks.OXIDATION_RESISTANT_HASTELLOY_N_MECHANICAL_CASING.get()))
                    .where('C', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.NaquadahAlloy)))
                    .where('D', blocks(GTOBlocks.NAQUADAH_ALLOY_CASING.get()))
                    .where('E', blocks(GTOBlocks.ELECTRON_PERMEABLE_AMPROSIUM_COATED_GLASS.get()))
                    .where('F', blocks(GTBlocks.HIGH_POWER_CASING.get()))
                    .where('G', blocks(GTBlocks.CASING_STEEL_TURBINE.get()))
                    .where('H', blocks(GTBlocks.HERMETIC_CASING_UHV.get()))
                    .where('I', blocks(GTBlocks.FUSION_CASING.get()))
                    .where('J', blocks(GTOBlocks.MOLECULAR_COIL.get()))
                    .where('K', blocks(GTOBlocks.SHIELDED_ACCELERATOR.get()))
                    .where('L', blocks(RegistriesUtils.getBlock("gtceu:magnetic_neodymium_block")))
                    .where('M', blocks(RegistriesUtils.getBlock("gtceu:magnetic_samarium_block")))
                    .where('N', blocks(RegistriesUtils.getBlock("gtocore:energetic_netherite_block")))
                    .where('O', blocks(GTOBlocks.MAGTECH_CASING.get()))
                    .where('P', blocks(GTOBlocks.RADIATION_ABSORBENT_CASING.get()))
                    .where('Q', blocks(GTOBlocks.AMPROSIUM_PIPE_CASING.get()))
                    .where('R', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTOMaterials.HastelloyN)))
                    .where('S', blocks(GTOBlocks.BORON_CARBIDE_CERAMIC_RADIATION_RESISTANT_MECHANICAL_CUBE.get()))
                    .where('T', blocks(GTOBlocks.HOLLOW_CASING.get()))
                    .where('U', blocks(GCYMBlocks.CASING_ATOMIC.get()))
                    .where('V', blocks(GTBlocks.CASING_TUNGSTENSTEEL_TURBINE.get()))
                    .where('W', blocks(Blocks.SNOW_BLOCK))
                    .where('X', blocks(Blocks.LIME_CONCRETE))
                    .where('Y', controller(blocks(definition.get())))
                    .where(' ', any())
                    .build())
            .workableCasingRenderer(GTOCore.id("block/casings/iridium_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public static final MultiblockMachineDefinition ENCAPSULATOR_EXECUTION_MODULE = multiblock("encapsulator_execution_module", "产线封装者执行模块", EncapsulatorExecutionModuleMachine::new)
            .nonYAxisRotation()
            .recipeTypes(GTRecipeTypes.DUMMY_RECIPES)
            .perfectOverclock()
            .perfectOCTooltips()
            .laserTooltips()
            .block(GTOBlocks.NAQUADAH_REINFORCED_PLANT_CASING)
            .pattern(definition -> FactoryBlockPattern.start(definition)
                    .aisle("AAAAA", "AAAAA", "AAAAA")
                    .aisle("BBBBB", "BBCBB", "BBBBB")
                    .where('A', blocks(GTOBlocks.STRENGTHEN_THE_BASE_BLOCK.get()))
                    .where('B', blocks(GTOBlocks.NAQUADAH_REINFORCED_PLANT_CASING.get())
                            .or(abilities(GTOPartAbility.ACCELERATE_HATCH).setMaxGlobalLimited(1))
                            .or(abilities(IMPORT_ITEMS))
                            .or(abilities(EXPORT_ITEMS))
                            .or(abilities(IMPORT_FLUIDS))
                            .or(abilities(EXPORT_FLUIDS))
                            .or(abilities(INPUT_ENERGY).setMaxGlobalLimited(2).setPreviewCount(1))
                            .or(abilities(INPUT_LASER).setMaxGlobalLimited(2)))
                    .where('C', controller(blocks(definition.get())))
                    .build())
            .workableCasingRenderer(GTOCore.id("block/casings/naquadah_reinforced_plant_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();

    public static final MultiblockMachineDefinition PROCESSING_ENCAPSULATOR = multiblock("processing_encapsulator", "产线封装者", ProcessingEncapsulatorMachine::new)
            .nonYAxisRotation()
            .recipeTypes(GTRecipeTypes.DUMMY_RECIPES)
            .block(GCYMBlocks.CASING_ATOMIC)
            .pattern(definition -> MultiBlockFileReader.start(definition, RelativeDirection.FRONT, RelativeDirection.UP, RelativeDirection.RIGHT)
                    .where('A', blocks(GTOBlocks.MOLECULAR_CASING.get()))
                    .where('B', blocks(GTOBlocks.DIMENSIONALLY_TRANSCENDENT_CASING.get()))
                    .where('C', blocks(GTOBlocks.GRAVITY_STABILIZATION_CASING.get()))
                    .where('D', blocks(GTOBlocks.CONTAINMENT_FIELD_GENERATOR.get()))
                    .where('E', blocks(GTOBlocks.DIMENSIONAL_BRIDGE_CASING.get()))
                    .where('F', blocks(GTOBlocks.DIMENSION_INJECTION_CASING.get()))
                    .where('G', blocks(GTOBlocks.DEGENERATE_RHENIUM_CONSTRAINED_CASING.get()))
                    .where('H', blocks(GTOBlocks.STRENGTHEN_THE_BASE_BLOCK.get()))
                    .where('I', blocks(GTOBlocks.QUARK_PIPE.get()))
                    .where('J', blocks(GTOBlocks.RHENIUM_REINFORCED_ENERGY_GLASS.get()))
                    .where('K', blocks(GTOBlocks.ENHANCE_HYPER_MECHANICAL_CASING.get()))
                    .where('L', GTOPredicates.glass())
                    .where('M', blocks(GTOBlocks.NAQUADAH_REINFORCED_PLANT_CASING.get()))
                    .where('N', blocks(GTOBlocks.SPACETIME_ASSEMBLY_LINE_UNIT.get()))
                    .where('O', blocks(GTOBlocks.HERMETIC_CASING_UXV.get()))
                    .where('P', blocks(GCYMBlocks.CASING_ATOMIC.get()))
                    .where('Q', blocks(GTOBlocks.MANIPULATOR.get()))
                    .where('R', blocks(GTOBlocks.ADVANCED_ASSEMBLY_LINE_UNIT.get()))
                    .where('S', blocks(GTOBlocks.SPACETIME_ASSEMBLY_LINE_CASING.get()))
                    .where('T', blocks(GCYMBlocks.CASING_ATOMIC.get())
                            .or(blocks(GTOMachines.MACHINE_ACCESS_TERMINAL.getBlock()).setMaxGlobalLimited(4).setMinGlobalLimited(1))
                            .or(abilities(PARALLEL_HATCH).setMaxGlobalLimited(1))
                            .or(abilities(INPUT_ENERGY).setMaxGlobalLimited(2).setPreviewCount(1))
                            .or(abilities(MAINTENANCE).setExactLimit(1)))
                    .where('U', blocks(ChemicalHelper.getBlock(TagPrefix.frameGt, GTMaterials.NaquadahAlloy)))
                    .where('V', blocks(GTOBlocks.QUARK_EXCLUSION_CASING.get()))
                    .where('W', GTOPredicates.integralFramework())
                    .where('X', blocks(GTOBlocks.HOLLOW_CASING.get()))
                    .where('Y', controller(blocks(definition.get())))
                    .where(' ', any())
                    .build())
            .workableCasingRenderer(GTCEu.id("block/casings/gcym/atomic_casing"), GTCEu.id("block/multiblock/fusion_reactor"))
            .register();
}
