package com.gto.gtocore.api.machine.trait;

import com.gto.gtocore.common.data.GTOItems;
import com.gto.gtocore.common.item.VirtualItemProviderBehavior;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;

import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.NotNull;

public class NotifiableCircuitItemStackHandler extends NotifiableItemStackHandler {

    public NotifiableCircuitItemStackHandler(MetaMachine machine) {
        super(machine, 1, IO.IN, IO.IN);
    }

    @NotNull
    @Override
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.is(GTOItems.VIRTUAL_ITEM_PROVIDER.get())) {
            storage.setStackInSlot(slot, VirtualItemProviderBehavior.getVirtualItem(stack));
            return ItemStack.EMPTY;
        }
        return stack;
    }

    @NotNull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return simulate ? super.extractItem(slot, amount, true) : ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }
}
