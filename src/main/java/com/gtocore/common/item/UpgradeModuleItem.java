package com.gtocore.common.item;

import com.gtocore.common.data.GTOItems;

import com.gtolib.api.machine.feature.IUpgradeMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.utils.FormattingUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.annotation.Nullable;

public final class UpgradeModuleItem extends Item {

    public UpgradeModuleItem(Properties properties) {
        super(properties);
    }

    private static ItemStack speed(int tier) {
        var item = GTOItems.SPEED_UPGRADE_MODULE.get().getDefaultInstance();
        item.getOrCreateTag().putDouble("speed", randomMultiple(tier));
        return item;
    }

    private static ItemStack energy(int tier) {
        var item = GTOItems.ENERGY_UPGRADE_MODULE.get().getDefaultInstance();
        item.getOrCreateTag().putDouble("energy", randomMultiple(tier));
        return item;
    }

    private static double randomMultiple(int tier) {
        return 1D - (GTValues.RNG.nextDouble() / (20D / tier));
    }

    @Override
    public @NotNull InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        var player = context.getPlayer();
        if (player instanceof ServerPlayer) {
            var item = player.getItemInHand(context.getHand());
            var tag = item.getTag();
            if (tag != null) {
                var machine = MetaMachine.getMachine(context.getLevel(), context.getClickedPos());
                if (machine instanceof IUpgradeMachine upgradeMachine && upgradeMachine.gtolib$canUpgraded()) {
                    if (tag.contains("speed")) {
                        double speed = upgradeMachine.gtolib$getSpeed();
                        if (speed < 1) {
                            speed = Math.max(speed, randomMultiple(10)) * tag.getDouble("speed");
                        } else {
                            speed = tag.getDouble("speed");
                        }
                        upgradeMachine.gtolib$setSpeed(Math.max(0.5, speed));
                        player.setItemInHand(context.getHand(), item.copyWithCount(item.getCount() - 1));
                        return InteractionResult.CONSUME;
                    }
                    if (tag.contains("energy")) {
                        double energy = upgradeMachine.gtolib$getEnergy();
                        if (energy < 1) {
                            energy = Math.max(energy, randomMultiple(10)) * tag.getDouble("energy");
                        } else {
                            energy = tag.getDouble("energy");
                        }
                        upgradeMachine.gtolib$setEnergy(Math.max(0.5, energy));
                        player.setItemInHand(context.getHand(), item.copyWithCount(item.getCount() - 1));
                        return InteractionResult.CONSUME;
                    }
                }
            } else {
                if (player.experienceLevel > 10) {
                    player.setItemInHand(context.getHand(), item.copyWithCount(item.getCount() - 1));
                    ItemStack itemStack;
                    if (this == GTOItems.SPEED_UPGRADE_MODULE.get()) {
                        itemStack = speed(Math.min(10, player.experienceLevel / 10));
                    } else {
                        itemStack = energy(Math.min(10, player.experienceLevel / 10));
                    }
                    player.experienceLevel = 0;
                    player.totalExperience = 0;
                    context.getLevel().addFreshEntity(new ItemEntity(context.getLevel(), player.getX(), player.getY(), player.getZ(), itemStack));
                }
            }
        }
        return super.onItemUseFirst(stack, context);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, @Nullable Level world, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemstack, world, list, flag);
        var tag = itemstack.getTag();
        if (tag != null) {
            if (tag.contains("speed")) {
                list.add(Component.translatable("gtocore.machine.duration_multiplier.tooltip", FormattingUtil.formatNumbers(tag.getDouble("speed"))));
            } else if (tag.contains("energy")) {
                list.add(Component.translatable("gtocore.machine.eut_multiplier.tooltip", FormattingUtil.formatNumbers(tag.getDouble("energy"))));
            }
        }
    }
}
