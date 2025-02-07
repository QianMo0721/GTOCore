package com.gto.gtocore.mixin.eio;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.enderio.api.glider.GliderMovementInfo;
import com.enderio.base.common.hangglider.PlayerMovementHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Optional;

@Mixin(PlayerMovementHandler.class)
public final class PlayerMovementHandlerMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent playerTickEvent) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static Optional<GliderMovementInfo> calculateGliderMovementInfo(Player player, boolean displayDisabledMessage) {
        return Optional.empty();
    }
}
