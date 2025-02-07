package com.gto.gtocore.mixin.placebo;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import dev.shadowsoffire.placebo.patreon.PatreonPreview;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PatreonPreview.class)
public final class PatreonPreviewMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent e) {}
}
