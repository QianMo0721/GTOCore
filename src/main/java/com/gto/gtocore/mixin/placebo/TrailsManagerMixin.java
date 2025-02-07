package com.gto.gtocore.mixin.placebo;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import dev.shadowsoffire.placebo.patreon.TrailsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(TrailsManager.class)
public final class TrailsManagerMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static void init() {}

    /**
     * @author .
     * @reason .
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public static void clientTick(TickEvent.ClientTickEvent e) {}

    /**
     * @author .
     * @reason .
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public static void keys(InputEvent.Key e) {}
}
