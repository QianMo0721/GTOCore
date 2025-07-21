package com.gtocore.common.machine.monitor;

import com.gregtechceu.gtceu.api.gui.widget.LongInputWidget;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public abstract class AbstractInfoProviderMonitor extends BasicMonitor implements IInformationProvider {

    @Persisted
    @DescSynced
    private long priority = 0;
    @Persisted
    @DescSynced
    private ResourceLocation[] displayOrderCache = new ResourceLocation[0];
    private static final ManagedFieldHolder BASIC_MONITOR_HOLDER = new ManagedFieldHolder(BasicMonitor.class, MetaMachine.MANAGED_FIELD_HOLDER);

    protected static ManagedFieldHolder getManagedFieldHolder(Class<? extends BasicMonitor> clazz) {
        return new ManagedFieldHolder(clazz, BASIC_MONITOR_HOLDER);
    }

    public AbstractInfoProviderMonitor(IMachineBlockEntity holder) {
        super(holder);
        Class<? extends BasicMonitor> clazz = this.getClass();
        MANAGED_FIELD_HOLDER_MAP.putIfAbsent(clazz, getManagedFieldHolder(clazz));
        this.subscribeServerTick(() -> {
            if (this.getOffsetTimer() % 10 == 0) {
                this.syncInfoFromServer();
                this.getSyncStorage().markAllDirty();
                this.getHolder().syncNow(false);
            }
        });
    }

    private static final Map<Class<? extends BasicMonitor>, ManagedFieldHolder> MANAGED_FIELD_HOLDER_MAP = new ConcurrentHashMap<>();

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        var holder = MANAGED_FIELD_HOLDER_MAP.get(getClass());
        if (holder == null) {
            throw new IllegalStateException("ManagedFieldHolder not found for " + getClass().getName());
        }
        return holder;
    }

    @Override
    public long getPriority() {
        return priority;
    }

    @Override
    public void setPriority(long priority) {
        this.priority = priority;
        if (getLevel() != null && !getLevel().isClientSide()) {
            this.getHolder().syncNow(false);
        }
    }

    @Override
    public List<ResourceLocation> getSortedRLs() {
        if (displayOrderCache == null || displayOrderCache.length == 0) {
            return getAvailableRLs();
        }
        return Stream.of(displayOrderCache).toList();
    }

    @Override
    public Widget createUIWidget() {
        // TODO可以调整显示的顺序 即调整displayOrderCache
        LongInputWidget var1 = new LongInputWidget(this::getPriority, this::setPriority);
        var1.setMax((long) Integer.MAX_VALUE);
        var1.setMin((long) Integer.MIN_VALUE);
        var1.setHoverTooltips(Component.translatable("gtocore.machine.monitor.priority"));
        return (new WidgetGroup(0, 0, 100, 20)).addWidget(var1);
    }
}
