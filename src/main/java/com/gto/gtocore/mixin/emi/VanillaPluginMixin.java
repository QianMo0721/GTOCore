package com.gto.gtocore.mixin.emi;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.helpers.IPlatformFluidHelper;
import mezz.jei.api.registration.*;
import mezz.jei.common.platform.IPlatformRegistry;
import mezz.jei.common.util.StackHelper;
import mezz.jei.library.plugins.vanilla.VanillaPlugin;
import mezz.jei.library.plugins.vanilla.anvil.AnvilRecipeCategory;
import mezz.jei.library.plugins.vanilla.anvil.SmithingRecipeCategory;
import mezz.jei.library.plugins.vanilla.crafting.CraftingRecipeCategory;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(VanillaPlugin.class)
public class VanillaPluginMixin {

    @Shadow(remap = false)
    private @Nullable CraftingRecipeCategory craftingCategory;

    @Shadow(remap = false)
    private @Nullable SmithingRecipeCategory smithingCategory;

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerItemSubtypes(ISubtypeRegistration registration) {}

    @Redirect(method = "registerIngredients", at = @At(value = "INVOKE", target = "Lmezz/jei/library/plugins/vanilla/ingredients/ItemStackListFactory;create(Lmezz/jei/common/util/StackHelper;)Ljava/util/List;"), remap = false)
    private List<ItemStack> createItem(StackHelper e) {
        return List.of();
    }

    @Redirect(method = "registerFluidIngredients", at = @At(value = "INVOKE", target = "Lmezz/jei/library/plugins/vanilla/ingredients/fluid/FluidStackListFactory;create(Lmezz/jei/common/platform/IPlatformRegistry;Lmezz/jei/api/helpers/IPlatformFluidHelper;)Ljava/util/List;"), remap = false)
    private <T> List<T> createFluid(IPlatformRegistry<Fluid> registry, IPlatformFluidHelper<T> helper) {
        return List.of();
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(craftingCategory = new CraftingRecipeCategory(guiHelper), smithingCategory = new SmithingRecipeCategory(guiHelper), new AnvilRecipeCategory(guiHelper));
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerVanillaCategoryExtensions(IVanillaCategoryExtensionRegistration registration) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerRecipes(IRecipeRegistration registration) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {}
}
