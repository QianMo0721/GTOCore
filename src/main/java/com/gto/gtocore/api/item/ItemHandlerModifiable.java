package com.gto.gtocore.api.item;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

import org.jetbrains.annotations.NotNull;

public class ItemHandlerModifiable implements IItemHandlerModifiable {

    private ItemStack itemStack;

    public ItemHandlerModifiable(ItemStack stack) {
        itemStack = stack;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        itemStack = stack;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return itemStack;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (itemStack.is(stack.getItem())) {
            itemStack.setCount(itemStack.getCount() + stack.getCount());
        } else {
            itemStack = stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        int count = itemStack.getCount() - amount;
        if (count < 0) return ItemStack.EMPTY;
        ItemStack stack = itemStack.copyWithCount(count);
        if (count == 0) {
            itemStack = ItemStack.EMPTY;
        } else {
            itemStack.setCount(count);
        }
        return stack;
    }

    @Override
    public int getSlotLimit(int slot) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return itemStack.isEmpty();
    }
}
