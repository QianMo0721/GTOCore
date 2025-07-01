package com.gtocore.common.wireless;

import com.gtocore.common.saved.WirelessManaSavaedData;

import com.hepdd.gtmthings.utils.TeamUtil;

import java.math.BigInteger;
import java.util.UUID;

public final class WirelessManaContainer {

    public static WirelessManaContainer getOrCreateContainer(UUID uuid) {
        return WirelessManaSavaedData.INSTANCE.containerMap.computeIfAbsent(TeamUtil.getTeamUUID(uuid), WirelessManaContainer::new);
    }

    private BigInteger storage;
    private final UUID uuid;

    private WirelessManaContainer(UUID uuid) {
        this.uuid = uuid;
        this.storage = BigInteger.ZERO;
    }

    public WirelessManaContainer(UUID uuid, BigInteger storage) {
        this(uuid);
        this.storage = storage;
    }

    public BigInteger getStorage() {
        return this.storage;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setStorage(final BigInteger storage) {
        this.storage = storage;
    }
}
