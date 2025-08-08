package com.gtocore.integration.ae

import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

object OneTimeClientDelayedTask {
    fun schedule(delayTicks: Int, task: Runnable) {
        MinecraftForge.EVENT_BUS.register(object : Any() {
            private var counter = 0

            @SubscribeEvent
            fun onTick(event: TickEvent.ClientTickEvent) {
                if (event.phase === TickEvent.Phase.END) {
                    if (++counter >= delayTicks) {
                        task.run()
                        MinecraftForge.EVENT_BUS.unregister(this)
                    }
                }
            }
        })
    }
}
