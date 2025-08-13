package com.gtocore.api.lang

import com.gtocore.common.data.translation.MultiblockSlang

import net.minecraft.network.chat.Component

import com.gtolib.api.annotation.NewDataAttributes
import com.gtolib.api.annotation.dynamic.DynamicInitialData

import java.util.function.Supplier

class TooltipsSortedWrapper {

    private val tooltips = mutableListOf<Supplier<List<Component?>?>>()
    var isInitialized = false // 被引用

    companion object {
        private fun <T> MutableList<T>.sortByConditions(vararg conditions: (T) -> Boolean) {
            sortWith(
                compareBy { item ->
                    conditions.indexOfLast { it(item) }.takeIf { it >= 0 }?.let { it + 1 } ?: 0
                },
            )
        }
    }

    fun addTooltipSupplier(supplier: Supplier<List<Component?>?>): TooltipsSortedWrapper {
        tooltips.add(supplier)

        val conditions = arrayOf<(Supplier<List<Component?>?>) -> Boolean>(
            { s -> s.get()?.any { it?.string?.contains(DynamicInitialData.PREFIX_DYNAMIC_VALUE) ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains(NewDataAttributes.PREFIX_TEMPLATE) ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.ALLOW_PARALLEL_SPECIAL.key}") ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.ALLOW_PARALLEL.key}") ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.ALLOW_PARALLEL_NUMBER.key}") ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.LASER_ENERGY_HATCH.key}") ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains(MultiblockSlang.not_allow_standard_energy_hatch.translationPrefix) ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.PREFECT_OVERCLOCK.key}") ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.ALLOW_MULTI_RECIPE_PARALLEL.key}") ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.NOT_ALLOW_SHARED.key}") ?: false } ?: false },
            { s -> s.get()?.any { it?.string?.contains("${NewDataAttributes.PREFIX_TEMPLATE}.${NewDataAttributes.RECIPES_TYPE.key}") ?: false } ?: false },
        )

        tooltips.sortByConditions(*conditions)
        return this
    }

    fun addTooltipSupplier(components: List<Component>): TooltipsSortedWrapper = addTooltipSupplier { components }

    fun addTooltip(component: Component): TooltipsSortedWrapper = addTooltipSupplier(listOf(component))

    fun getTooltips(): List<Supplier<List<Component?>?>> = tooltips
}
