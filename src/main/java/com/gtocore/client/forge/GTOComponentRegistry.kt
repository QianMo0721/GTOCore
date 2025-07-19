package com.gtocore.common.forge.render

import com.gtocore.api.gui.g.impl.GTOProgressClientComponent
import com.gtocore.api.gui.g.impl.GTOProgressComponent

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

@OnlyIn(Dist.CLIENT)
object GTOComponentRegistry {
    @SubscribeEvent
    @JvmStatic
    fun onClientSetup(event: RegisterClientTooltipComponentFactoriesEvent) {
        event.register(
            GTOProgressComponent::class.java,
            ::GTOProgressClientComponent,
        )
    }
}
