package com.gto.gtocore.integration.ae2.storage;

import com.gto.gtocore.integration.ae2.InfinityCellItem;

import net.minecraft.world.item.ItemStack;

import appeng.api.storage.cells.ICellHandler;
import appeng.api.storage.cells.ISaveProvider;

public final class InfinityCellHandler implements ICellHandler {

    public static final InfinityCellHandler INSTANCE = new InfinityCellHandler();

    @Override
    public boolean isCell(ItemStack is) {
        return is.getItem() instanceof InfinityCellItem;
    }

    @Override
    public InfinityCellInventory getCellInventory(ItemStack is, ISaveProvider container) {
        return InfinityCellInventory.createInventory(is, container);
    }
}
