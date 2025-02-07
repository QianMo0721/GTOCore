package com.gto.gtocore.mixin.ftbu;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import dev.architectury.event.EventResult;
import dev.architectury.utils.value.IntValue;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FTBUltimine.class)
public class FTBUltimineMixin {

    @Inject(method = "playerTick", at = @At("HEAD"), remap = false, cancellable = true)
    private void playerTick(Player player, CallbackInfo ci) {
        if (gTOCore$notDiggerItem(player)) ci.cancel();
    }

    @Inject(method = "blockBroken", at = @At("HEAD"), remap = false, cancellable = true)
    private void blockBroken(Level world, BlockPos pos, BlockState state, ServerPlayer player, IntValue xp, CallbackInfoReturnable<EventResult> cir) {
        if (gTOCore$notDiggerItem(player)) cir.setReturnValue(EventResult.pass());
    }

    @Unique
    private static boolean gTOCore$notDiggerItem(Player player) {
        return !player.isCreative() && !(player.getMainHandItem().getItem() instanceof DiggerItem);
    }
}
