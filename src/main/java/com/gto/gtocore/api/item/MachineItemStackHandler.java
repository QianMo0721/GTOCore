package com.gto.gtocore.api.item;

import com.gregtechceu.gtceu.api.transfer.item.CustomItemStackHandler;

import java.util.function.Supplier;

public class MachineItemStackHandler extends CustomItemStackHandler {

    private final Supplier<Integer> supplier;
    private final Runnable runnable;

    public MachineItemStackHandler(Supplier<Integer> slotLimit, Runnable onContentsChanged) {
        super(1);
        this.supplier = slotLimit;
        this.runnable = onContentsChanged;
    }

    @Override
    public int getSlotLimit(int slot) {
        return supplier.get();
    }

    @Override
    public void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        runnable.run();
    }
}
