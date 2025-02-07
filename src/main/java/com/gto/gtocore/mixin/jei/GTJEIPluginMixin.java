package com.gto.gtocore.mixin.jei;

import com.gto.gtocore.common.data.GTORecipes;
import com.gto.gtocore.integration.jei.multipage.MultiblockInfoCategory;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.recipe.category.GTRecipeCategory;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.item.IntCircuitBehaviour;
import com.gregtechceu.gtceu.config.ConfigHolder;
import com.gregtechceu.gtceu.integration.jei.GTJEIPlugin;
import com.gregtechceu.gtceu.integration.jei.circuit.GTProgrammedCircuitCategory;
import com.gregtechceu.gtceu.integration.jei.oreprocessing.GTOreProcessingInfoCategory;
import com.gregtechceu.gtceu.integration.jei.orevein.GTBedrockFluidInfoCategory;
import com.gregtechceu.gtceu.integration.jei.orevein.GTOreVeinInfoCategory;
import com.gregtechceu.gtceu.integration.jei.recipe.GTRecipeJEICategory;

import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(GTJEIPlugin.class)
public class GTJEIPluginMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerCategories(IRecipeCategoryRegistration registry) {
        if (GTCEu.Mods.isREILoaded() || GTCEu.Mods.isEMILoaded()) return;
        GTCEu.LOGGER.info("JEI register categories");
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();
        registry.addRecipeCategories(new MultiblockInfoCategory(jeiHelpers));
        if (!ConfigHolder.INSTANCE.compat.hideOreProcessingDiagrams)
            registry.addRecipeCategories(new GTOreProcessingInfoCategory(jeiHelpers));
        registry.addRecipeCategories(new GTOreVeinInfoCategory(jeiHelpers));
        registry.addRecipeCategories(new GTBedrockFluidInfoCategory(jeiHelpers));
        for (GTRecipeCategory category : GTRegistries.RECIPE_CATEGORIES) {
            if (category.shouldRegisterDisplays()) {
                registry.addRecipeCategories(new GTRecipeJEICategory(jeiHelpers, category));
            }
        }
        registry.addRecipeCategories(new GTProgrammedCircuitCategory(jeiHelpers));
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        if (GTCEu.Mods.isREILoaded() || GTCEu.Mods.isEMILoaded()) return;
        GTRecipeJEICategory.registerRecipeCatalysts(registration);
        GTORecipes.clearCategoryMap(true);
        GTOreVeinInfoCategory.registerRecipeCatalysts(registration);
        GTBedrockFluidInfoCategory.registerRecipeCatalysts(registration);
        registration.addRecipeCatalyst(IntCircuitBehaviour.stack(0), GTProgrammedCircuitCategory.RECIPE_TYPE);
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerRecipes(IRecipeRegistration registration) {
        if (GTCEu.Mods.isREILoaded() || GTCEu.Mods.isEMILoaded()) return;
        GTORecipes.initCategoryMap();
        GTCEu.LOGGER.info("JEI register");
        MultiblockInfoCategory.registerRecipes(registration);
        GTRecipeJEICategory.registerRecipes(registration);
        if (!ConfigHolder.INSTANCE.compat.hideOreProcessingDiagrams)
            GTOreProcessingInfoCategory.registerRecipes(registration);
        GTOreVeinInfoCategory.registerRecipes(registration);
        GTBedrockFluidInfoCategory.registerRecipes(registration);
        registration.addRecipes(GTProgrammedCircuitCategory.RECIPE_TYPE, List.of(new GTProgrammedCircuitCategory.GTProgrammedCircuitWrapper()));
    }
}
