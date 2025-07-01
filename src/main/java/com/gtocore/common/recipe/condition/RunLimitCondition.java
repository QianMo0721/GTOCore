package com.gtocore.common.recipe.condition;

import com.gtocore.common.saved.RecipeRunLimitSavaedData;

import com.gtolib.api.recipe.Recipe;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;
import com.gregtechceu.gtceu.api.recipe.condition.RecipeConditionType;
import com.gregtechceu.gtceu.common.machine.owner.MachineOwner;

import net.minecraft.network.chat.Component;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class RunLimitCondition extends AbstractRecipeCondition {

    private final int count;

    public RunLimitCondition(int count) {
        this.count = count;
    }

    @Override
    public RecipeConditionType<?> getType() {
        return RUN_LIMIT;
    }

    @Override
    public Component getTooltips() {
        return Component.translatable("gtocore.recipe.runlimit.count", count);
    }

    @Override
    public boolean test(@NotNull Recipe recipe, @NotNull RecipeLogic recipeLogic) {
        MetaMachine machine = recipeLogic.getMachine();
        MachineOwner owner = machine.getOwner();
        if (owner == null) return false;
        UUID uuid = owner.getUUID();
        if (uuid == null) return false;
        int runLimit = RecipeRunLimitSavaedData.get(uuid, recipe.id);
        if (runLimit < count) {
            RecipeRunLimitSavaedData.set(uuid, recipe.id, runLimit + 1);
            return true;
        }
        return false;
    }
}
