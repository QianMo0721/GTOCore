package com.gto.gtocore.integration.ae2.storage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import static com.gto.gtocore.api.GTOValues.INFINITY_CELL_AMOUNTS;
import static com.gto.gtocore.api.GTOValues.INFINITY_CELL_KEYS;

public final class InfinityCellDataStorage {

    public static final InfinityCellDataStorage EMPTY = new InfinityCellDataStorage();

    public ListTag keys;
    public long[] amounts;

    public InfinityCellDataStorage() {
        this(new ListTag(), new long[0]);
    }

    private InfinityCellDataStorage(ListTag keys, long[] amounts) {
        this.keys = keys;
        this.amounts = amounts;
    }

    public CompoundTag toNbt() {
        CompoundTag nbt = new CompoundTag();
        nbt.put(INFINITY_CELL_KEYS, keys);
        nbt.putLongArray(INFINITY_CELL_AMOUNTS, amounts);
        return nbt;
    }

    public static InfinityCellDataStorage fromNbt(CompoundTag nbt) {
        ListTag stackKeys = nbt.getList(INFINITY_CELL_KEYS, Tag.TAG_COMPOUND);
        long[] stackAmounts = nbt.getLongArray(INFINITY_CELL_AMOUNTS);
        return new InfinityCellDataStorage(stackKeys, stackAmounts);
    }
}
