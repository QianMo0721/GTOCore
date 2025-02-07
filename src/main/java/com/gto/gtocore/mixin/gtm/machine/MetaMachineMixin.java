package com.gto.gtocore.mixin.gtm.machine;

import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.EnumSet;
import java.util.Set;

@Mixin(MetaMachine.class)
public abstract class MetaMachineMixin {

    @Unique
    private long gTOCore$lastExecutionTime;

    @Shadow(remap = false)
    public abstract boolean isRemote();

    @Shadow(remap = false)
    @Final
    public IMachineBlockEntity holder;

    @Inject(method = "serverTick", at = @At("HEAD"), remap = false, cancellable = true)
    private void tick(CallbackInfo ci) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - gTOCore$lastExecutionTime < 40) {
            ci.cancel();
        }
        gTOCore$lastExecutionTime = currentTime;
    }

    @Inject(method = "onToolClick", at = @At("RETURN"), remap = false, cancellable = true)
    private void onToolClick(Set<@NotNull GTToolType> toolType, ItemStack itemStack, UseOnContext context, CallbackInfoReturnable<Pair<GTToolType, InteractionResult>> cir) {
        if (cir.getReturnValue().getSecond() == InteractionResult.PASS && toolType.contains(GTToolType.WIRE_CUTTER)) {
            Player player = context.getPlayer();
            if (player == null) return;
            if (holder.getMetaMachine() instanceof IGridConnectedMachine gridConnectedMachine) {
                cir.setReturnValue(Pair.of(GTToolType.WIRE_CUTTER, gtoCore$onWireCutterClick(player, context.getHand(), gridConnectedMachine)));
            }
        }
    }

    @Inject(method = "shouldRenderGrid", at = @At("HEAD"), remap = false, cancellable = true)
    private void shouldRenderGrid(Player player, BlockPos pos, BlockState state, ItemStack held, Set<GTToolType> toolTypes, CallbackInfoReturnable<Boolean> cir) {
        if (toolTypes.contains(GTToolType.WIRE_CUTTER)) {
            MetaMachine metaMachine = holder.getMetaMachine();
            if (metaMachine instanceof IGridConnectedMachine) cir.setReturnValue(true);
        }
    }

    @Unique
    private InteractionResult gtoCore$onWireCutterClick(Player playerIn, InteractionHand hand, IGridConnectedMachine machine) {
        playerIn.swing(hand);
        if (holder.self().getPersistentData().getBoolean("isAllFacing")) {
            machine.getMainNode().setExposedOnSides(EnumSet.of(((MetaMachine) machine).getFrontFacing()));
            if (isRemote()) {
                playerIn.displayClientMessage(Component.translatable("gtocore.me_front"), true);
            }
            holder.self().getPersistentData().putBoolean("isAllFacing", false);
        } else {
            machine.getMainNode().setExposedOnSides(EnumSet.allOf(Direction.class));
            if (isRemote()) {
                playerIn.displayClientMessage(Component.translatable("gtocore.me_any"), true);
            }
            holder.self().getPersistentData().putBoolean("isAllFacing", true);
        }
        return InteractionResult.CONSUME;
    }
}
