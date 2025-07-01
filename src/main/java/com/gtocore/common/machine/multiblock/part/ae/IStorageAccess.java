package com.gtocore.common.machine.multiblock.part.ae;

import java.util.UUID;

public interface IStorageAccess {

    void setCapacity(double capacity);

    void setInfinite(boolean isInfinite);

    void setUUID(UUID uuid);

    void setCheck(boolean check);

    void setObserve(boolean observe);

    boolean isInfinite();

    double getCapacity();

    int getTypes();

    double getBytes();
}
