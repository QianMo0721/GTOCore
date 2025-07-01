package com.gtocore.common.machine.multiblock.electric.assembly;

import com.gtolib.api.machine.multiblock.CrossRecipeMultiblockMachine;
import com.gtolib.api.recipe.Recipe;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SuprachronalAssemblyLineModuleMachine extends CrossRecipeMultiblockMachine {

    SuprachronalAssemblyLineMachine assemblyLineMachine;

    public SuprachronalAssemblyLineModuleMachine(IMachineBlockEntity holder) {
        super(holder, false, false, m -> ((SuprachronalAssemblyLineModuleMachine) m).parallel());
    }

    private int parallel() {
        if (!effective()) return 1;
        return (int) assemblyLineMachine.getMaxParallel();
    }

    private boolean effective() {
        return assemblyLineMachine != null && assemblyLineMachine.isFormed();
    }

    @Override
    public void customText(@NotNull List<Component> textList) {
        super.customText(textList);
        textList.add(effective() ? Component.translatable("gtocore.machine.module.have") : Component.translatable("gtocore.machine.module.null"));
    }

    @Override
    protected Recipe fullModifyRecipe(@NotNull Recipe recipe) {
        if (effective()) return super.fullModifyRecipe(recipe);
        return null;
    }
}
