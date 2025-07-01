package com.gtocore.common.machine.trait;

import com.gtocore.common.machine.multiblock.part.RadiationHatchPartMachine;

import com.gtolib.api.machine.feature.multiblock.IMultiblockTraitHolder;
import com.gtolib.api.machine.trait.MultiblockTrait;
import com.gtolib.api.recipe.Recipe;

import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;

import net.minecraft.network.chat.Component;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public final class RadioactivityTrait extends MultiblockTrait {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            RadioactivityTrait.class, MultiblockTrait.MANAGED_FIELD_HOLDER);

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Persisted
    private int recipeRadioactivity;

    private final Set<RadiationHatchPartMachine> radiationHatchPartMachines = new ObjectOpenHashSet<>();

    public RadioactivityTrait(IMultiblockTraitHolder machine) {
        super(machine);
    }

    @Override
    public void onPartScan(IMultiPart part) {
        if (part instanceof RadiationHatchPartMachine radiationHatchPartMachine) {
            radiationHatchPartMachines.add(radiationHatchPartMachine);
        }
    }

    @Override
    public void onStructureInvalid() {
        radiationHatchPartMachines.clear();
    }

    @Override
    public void customText(@NotNull List<Component> textList) {
        super.customText(textList);
        textList.add(Component.translatable("gtocore.recipe.radioactivity", getRecipeRadioactivity()));
    }

    @Override
    public boolean beforeWorking(@Nullable Recipe recipe) {
        if (recipe == null) return true;
        if (recipe.data.contains("radioactivity")) {
            recipeRadioactivity = recipe.data.getInt("radioactivity");
            if (outside()) {
                return true;
            }
        }
        return super.beforeWorking(recipe);
    }

    @Override
    public void afterWorking() {
        recipeRadioactivity = 0;
        super.afterWorking();
    }

    private int getRecipeRadioactivity() {
        int radioactivity = 0;
        for (RadiationHatchPartMachine partMachine : radiationHatchPartMachines) {
            radioactivity += partMachine.getRadioactivity();
        }
        return radioactivity;
    }

    private boolean outside() {
        int radioactivity = getRecipeRadioactivity();
        return radioactivity > recipeRadioactivity + 5 || radioactivity < recipeRadioactivity - 5;
    }
}
