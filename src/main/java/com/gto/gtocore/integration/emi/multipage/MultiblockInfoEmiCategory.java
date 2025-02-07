package com.gto.gtocore.integration.emi.multipage;

import com.gto.gtocore.config.GTOConfig;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.machines.GTMultiMachines;

import net.minecraft.network.chat.Component;

import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;

public final class MultiblockInfoEmiCategory extends EmiRecipeCategory {

    public static final MultiblockInfoEmiCategory CATEGORY = new MultiblockInfoEmiCategory();

    private MultiblockInfoEmiCategory() {
        super(GTCEu.id("multiblock_info"), EmiStack.of(GTMultiMachines.ELECTRIC_BLAST_FURNACE.getItem()));
    }

    public static void registerDisplays(EmiRegistry registry) {
        if (GTOConfig.INSTANCE.disableMultiBlockPage) return;
        for (MachineDefinition machine : GTRegistries.MACHINES.values()) {
            if (machine instanceof MultiblockMachineDefinition definition && definition.isRenderXEIPreview()) {
                registry.addRecipe(new MultiblockInfoEmiRecipe(definition));
                if (GTOConfig.INSTANCE.fastMultiBlockPage) continue;
                registry.addWorkstation(CATEGORY, EmiStack.of(definition.asStack()));
            }
        }
    }

    @Override
    public Component getName() {
        return Component.translatable("gtceu.jei.multiblock_info");
    }
}
