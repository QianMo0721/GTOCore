package com.gtocore.common.wireless;

import com.gtolib.utils.MathUtil;

import com.hepdd.gtmthings.utils.TeamUtil;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;
import java.util.UUID;

public final class WirelessComputationContainer {

    private static final Map<UUID, WirelessComputationContainer> MAP = new Object2ObjectOpenHashMap<>();

    public static WirelessComputationContainer getOrCreateContainer(UUID uuid) {
        return MAP.computeIfAbsent(TeamUtil.getTeamUUID(uuid), WirelessComputationContainer::new);
    }

    private long capacity = 102400;
    private long storage;
    private final UUID uuid;

    private WirelessComputationContainer(UUID uuid) {
        this.uuid = uuid;
    }

    public int free() {
        return MathUtil.saturatedCast(capacity - storage);
    }

    public int getCache() {
        return MathUtil.saturatedCast(storage);
    }

    public void shrink(int change) {
        storage -= change;
    }

    public long getCapacity() {
        return this.capacity;
    }

    public long getStorage() {
        return this.storage;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setCapacity(final long capacity) {
        this.capacity = capacity;
    }

    public void setStorage(final long storage) {
        this.storage = storage;
    }
}
