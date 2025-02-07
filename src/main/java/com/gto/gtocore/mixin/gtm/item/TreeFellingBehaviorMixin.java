package com.gto.gtocore.mixin.gtm.item;

import com.gregtechceu.gtceu.common.item.tool.behavior.TreeFellingBehavior;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(TreeFellingBehavior.class)
public class TreeFellingBehaviorMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void addBehaviorNBT(ItemStack stack, CompoundTag tag) {}

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public void addInformation(@NotNull ItemStack stack, @Nullable Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {}
}
