package com.gtocore.common.machine.multiblock.part.ae;

import com.gtolib.api.machine.feature.IMEPartMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IDistinctPart;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.transfer.fluid.IFluidHandlerModifiable;
import com.gregtechceu.gtceu.integration.ae2.machine.trait.GridNodeHolder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraftforge.items.IItemHandlerModifiable;

import appeng.api.networking.IGridNodeListener;
import appeng.api.networking.IManagedGridNode;
import appeng.api.networking.security.IActionSource;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;

import java.util.EnumSet;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class MEBusPartMachine extends TieredIOPartMachine implements IMEPartMachine, IDistinctPart, IMachineLife {

    static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(MEBusPartMachine.class, TieredIOPartMachine.MANAGED_FIELD_HOLDER);
    @Persisted
    private final GridNodeHolder nodeHolder;
    @DescSynced
    boolean isOnline;
    final IActionSource actionSource;

    private TickableSubscription autoIOSubs;
    @Persisted
    protected boolean isDistinct = false;

    MEBusPartMachine(IMachineBlockEntity holder, IO io) {
        super(holder, GTValues.LuV, io);
        this.nodeHolder = createNodeHolder();
        this.actionSource = IActionSource.ofMachine(nodeHolder.getMainNode()::getNode);
    }

    private GridNodeHolder createNodeHolder() {
        return new GridNodeHolder(this);
    }

    void autoIO() {}

    @Override

    public IItemHandlerModifiable getItemHandlerCap(Direction side, boolean useCoverCapability) {
        return null;
    }

    @Override

    public IFluidHandlerModifiable getFluidHandlerCap(Direction side, boolean useCoverCapability) {
        return null;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (holder.self().getPersistentData().getBoolean("isAllFacing")) {
            getMainNode().setExposedOnSides(EnumSet.allOf(Direction.class));
        }
    }

    @Override
    public IManagedGridNode getMainNode() {
        return nodeHolder.getMainNode();
    }

    @Override
    public void onMainNodeStateChanged(IGridNodeListener.State reason) {
        IMEPartMachine.super.onMainNodeStateChanged(reason);
        this.updateInventorySubscription();
    }

    void updateInventorySubscription() {
        if (shouldSubscribe()) {
            autoIOSubs = subscribeServerTick(autoIOSubs, this::autoIO);
        } else if (autoIOSubs != null) {
            autoIOSubs.unsubscribe();
            autoIOSubs = null;
        }
    }

    boolean shouldSubscribe() {
        return isWorkingEnabled() && isOnline;
    }

    @Override
    public void onRotated(Direction oldFacing, Direction newFacing) {
        super.onRotated(oldFacing, newFacing);
        getMainNode().setExposedOnSides(EnumSet.of(newFacing));
    }

    @Override
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    public boolean isDistinct() {
        return isDistinct;
    }

    @Override
    public void setDistinct(boolean isDistinct) {
        this.isDistinct = isDistinct;
        getHandlerList().setDistinctAndNotify(isDistinct);
    }

    public TickableSubscription getAutoIOSubs() {
        return this.autoIOSubs;
    }

    public GridNodeHolder getNodeHolder() {
        return this.nodeHolder;
    }

    public void setOnline(final boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean isOnline() {
        return this.isOnline;
    }

    public IActionSource getActionSource() {
        return this.actionSource;
    }
}
