package com.gtocore.api.player

import com.gtocore.common.data.OrganItems

import net.minecraft.world.item.ItemStack

import com.gtolib.api.player.organ.capability.OrganCapability
import com.gtolib.api.player.organ.function.OrganItem
import com.gtolib.api.player.organ.function.OrganType
import com.gtolib.api.player.organ.function.item.impl.TierOrganItem
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap

private val organStackCache = Object2ObjectOpenHashMap<Int, Map<OrganType, List<ItemStack>>>()
private val tierOrganSetCache = Object2ObjectOpenHashMap<Pair<Int, Int>, Boolean>()
private val organItemCache = Object2ObjectOpenHashMap<Pair<Int, OrganItem>, ItemStack>()

fun OrganCapability.ktGetOrganStack(): Map<OrganType, List<ItemStack>> {
    val cacheKey = this.organStacks.hashCode()
    return organStackCache.getOrPut(cacheKey) {
        this.organStacks
            .groupBy { OrganType.of(it) }
            .filterKeys { it.isPresent }
            .mapKeys { it.key.get() }
            .mapValues { it.value.filterNotNull() }
    }
}

fun OrganCapability.ktMatchTierOrganSet(tier: Int): Boolean {
    val cacheKey = Pair(this.organStacks.hashCode(), tier)
    return tierOrganSetCache.getOrPut(cacheKey) {
        val mapValues: Map<OrganType, List<TierOrganItem>> = OrganItems.ORGAN_TIER_MAP
            .mapValues { it.value.slice(tier until it.value.size) }
            .mapValues { it.value.map { it.get() } }
        val organStack = ktGetOrganStack()
        mapValues.all { match ->
            organStack[match.key]?.any { match.value.map { it.asItem() }.contains(it.item.asItem()) } ?: false
        }
    }
}

fun OrganCapability.ktMatchLowTierOrganSet(tierLow: Int): Boolean = (tierLow..4).any { ktMatchTierOrganSet(it) }

fun OrganCapability.ktMatchOrganItem(item: OrganItem): ItemStack {
    val cacheKey = Pair(this.organStacks.hashCode(), item)
    return organItemCache.getOrPut(cacheKey) {
        ktGetOrganStack().flatMap { it.value }.firstOrNull { (it.item is OrganItem) && (it.item as OrganItem) == item } ?: ItemStack.EMPTY
    }
}
