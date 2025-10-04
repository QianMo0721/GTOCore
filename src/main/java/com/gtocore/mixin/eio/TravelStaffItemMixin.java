package com.gtocore.mixin.eio;

import com.gtocore.api.travel.TravelMode;
import com.gtocore.integration.eio.ITravelHandlerHook;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.registries.ForgeRegistries;

import com.enderio.base.common.item.tool.TravelStaffItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TravelStaffItem.class)
public class TravelStaffItemMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void gto$onUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide()) {
                TravelMode currentMode = gtocore$getTravelMode(stack);
                TravelMode nextMode = currentMode.next();
                gtocore$setTravelMode(stack, nextMode);

                // 如果切换到方块过滤模式，尝试设置玩家看向的方块作为过滤目标
                if (nextMode == TravelMode.FILTER_BY_BLOCK) {
                    HitResult hitResult = player.pick(20.0D, 0.0F, false);
                    if (hitResult.getType() == HitResult.Type.BLOCK) {
                        BlockHitResult blockHit = (BlockHitResult) hitResult;
                        BlockPos pos = blockHit.getBlockPos();
                        BlockState blockState = level.getBlockState(pos);
                        String blockId = ForgeRegistries.BLOCKS.getKey(blockState.getBlock()).toString();

                        CompoundTag tag = stack.getOrCreateTag();
                        tag.putString(ITravelHandlerHook.FILTER_BLOCK_TAG, blockId);

                        player.displayClientMessage(
                                Component.translatable("gtocore.travel.mode.switched")
                                        .append(": ")
                                        .append(nextMode.getDisplayName())
                                        .append(" [")
                                        .append(Component.literal(blockId).withStyle(ChatFormatting.YELLOW))
                                        .append("]"),
                                true);
                    } else {
                        player.displayClientMessage(
                                Component.translatable("gtocore.travel.mode.switched")
                                        .append(": ")
                                        .append(nextMode.getDisplayName())
                                        .append(" ")
                                        .append(Component.translatable("gtocore.travel.mode.filter.noblock").withStyle(ChatFormatting.RED)),
                                true);
                    }
                } else {
                    player.displayClientMessage(
                            Component.translatable("gtocore.travel.mode.switched")
                                    .append(": ")
                                    .append(nextMode.getDisplayName()),
                            true);
                }
            }
            cir.setReturnValue(InteractionResultHolder.success(stack));
        }
    }

    @Unique
    private static TravelMode gtocore$getTravelMode(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        return TravelMode.fromString(tag.getString(ITravelHandlerHook.MODE_TAG));
    }

    @Unique
    private static void gtocore$setTravelMode(ItemStack stack, TravelMode mode) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(ITravelHandlerHook.MODE_TAG, mode.getSerializedName());
    }
}
