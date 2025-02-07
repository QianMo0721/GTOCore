package com.gto.gtocore.mixin.placebo;

import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import dev.shadowsoffire.placebo.patreon.WingsManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(WingsManager.class)
public final class WingsManagerMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static void init(FMLClientSetupEvent e) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static void addLayers(EntityRenderersEvent.AddLayers e) {}

    /**
     * @author .
     * @reason .
     */
    @SubscribeEvent
    @Overwrite(remap = false)
    public static void keys(InputEvent.Key e) {}
}
