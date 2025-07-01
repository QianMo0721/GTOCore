package com.gtocore.data.recipe;

import com.gtocore.common.data.machines.SimpleModeMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.common.data.GTMachines;
import com.gregtechceu.gtceu.common.data.GTMaterials;

import net.minecraft.world.item.ItemStack;

import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;

import static com.gtocore.common.data.GTORecipeTypes.ASSEMBLER_RECIPES;

public final class SimpleModeRecipe {

    public static void init() {
        ASSEMBLER_RECIPES.builder("me_simple_pattern_buffer")
                .inputItems(GTMachines.DUAL_IMPORT_HATCH[GTValues.MV].asStack())
                .inputItems(new ItemStack(AEBlocks.PATTERN_PROVIDER.block().asItem()))
                .inputItems(new ItemStack(AEItems.CAPACITY_CARD.asItem()))
                .inputItems(new ItemStack(AEItems.SPEED_CARD.asItem()))
                .outputItems(SimpleModeMachine.ME_SIMPLE_PATTERN_BUFFER.asStack())
                .inputFluids(GTMaterials.SolderingAlloy, 72)
                .EUt(120)
                .duration(200)
                .save();
    }
}
