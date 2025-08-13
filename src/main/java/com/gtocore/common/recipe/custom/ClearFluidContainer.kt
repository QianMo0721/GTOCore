package com.gtocore.common.recipe.custom

import net.minecraft.core.RegistryAccess
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.inventory.CraftingContainer
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.crafting.CraftingBookCategory
import net.minecraft.world.item.crafting.CustomRecipe
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level
import net.minecraftforge.common.capabilities.ForgeCapabilities
import net.minecraftforge.fluids.capability.IFluidHandler

import com.google.gson.JsonObject
class ClearFluidContainer(val rs: ResourceLocation) : CustomRecipe(rs, CraftingBookCategory.MISC) {
    private fun findFluidContainer(container: CraftingContainer): ItemStack? {
        return container.items.find { stack ->
            if (stack.isEmpty) return@find false
            val capability = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null)
                ?: return@find false
            !capability.drain(1, IFluidHandler.FluidAction.SIMULATE).isEmpty
        }
    }

    override fun matches(container: CraftingContainer, level: Level): Boolean {
        val nonEmptyItems = container.items.count { !it.isEmpty }
        return nonEmptyItems == 1 && findFluidContainer(container) != null
    }

    override fun assemble(container: CraftingContainer, registryAccess: RegistryAccess): ItemStack {
        val fluidStack = findFluidContainer(container) ?: return ItemStack.EMPTY

        val stackCopy = fluidStack.copy()
        val capability = stackCopy.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).resolve().orElse(null)
            ?: return ItemStack.EMPTY

        capability.drain(Int.MAX_VALUE, IFluidHandler.FluidAction.EXECUTE) // 清空流体
        return capability.container
    }

    override fun canCraftInDimensions(p0: Int, p1: Int): Boolean = p0 * p1 >= 1

    override fun getSerializer(): RecipeSerializer<*> = ClearFluidContainerRecipeSerializer
}
object ClearFluidContainerRecipeSerializer : RecipeSerializer<ClearFluidContainer> {

    override fun fromJson(p0: ResourceLocation, p1: JsonObject): ClearFluidContainer = ClearFluidContainer(p0)

    override fun fromNetwork(p0: ResourceLocation, p1: FriendlyByteBuf): ClearFluidContainer? = ClearFluidContainer(p0)

    override fun toNetwork(p0: FriendlyByteBuf, p1: ClearFluidContainer) {
    }
}
