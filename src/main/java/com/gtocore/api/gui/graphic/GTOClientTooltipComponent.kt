package com.gtocore.api.gui.graphic.helper

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent

abstract class GTOClientTooltipComponent<T : GTOComponent>(val data: T) : ClientTooltipComponent
