package com.gto.gtocore.integration.emi.multipage;

import com.gto.gtocore.api.gui.PatternSlotWidget;
import com.gto.gtocore.client.gui.PatternPreview;

import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;

import net.minecraft.resources.ResourceLocation;

import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class MultiblockInfoEmiRecipe extends ModularEmiRecipe<PatternPreview> {

    private final MultiblockMachineDefinition definition;

    MultiblockInfoEmiRecipe(MultiblockMachineDefinition definition) {
        super(() -> PatternPreview.getPatternWidget(definition));
        this.definition = definition;
    }

    @Override
    public List<Widget> getFlatWidgetCollection(PatternPreview widgetIn) {
        inputs = widgetIn.getScrollableWidgetGroup().widgets.stream().map(p -> ((PatternSlotWidget) p).ingredient).toList();
        return List.of();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MultiblockInfoEmiCategory.CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return definition.getId();
    }
}
