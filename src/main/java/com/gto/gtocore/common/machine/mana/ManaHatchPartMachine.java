package com.gto.gtocore.common.machine.mana;

import com.gto.gtocore.api.machine.mana.IManaMachine;
import com.gto.gtocore.api.machine.mana.NotifiableManaContainer;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.TickableSubscription;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import vazkii.botania.api.mana.ManaCollector;
import vazkii.botania.api.mana.ManaPool;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.xplat.XplatAbstractions;

@Getter
public final class ManaHatchPartMachine extends TieredIOPartMachine implements IManaMachine {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(
            ManaHatchPartMachine.class, TieredIOPartMachine.MANAGED_FIELD_HOLDER);

    private TickableSubscription tickSubs;

    @Persisted
    public final NotifiableManaContainer manaContainer;

    public ManaHatchPartMachine(IMachineBlockEntity holder, int tier, IO io, int rate) {
        super(holder, tier, io);
        manaContainer = createManaContainer(rate);
    }

    @Override
    public @NotNull ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    private NotifiableManaContainer createManaContainer(int rate) {
        int tierMana = tier * tier * 100 * rate;
        if (io == IO.OUT) {
            return new NotifiableManaContainer(this, IO.OUT, 64 * tierMana, tierMana);
        } else return new NotifiableManaContainer(this, IO.IN, 64 * tierMana, tierMana);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (!isRemote() && io == IO.OUT) {
            tickSubs = subscribeServerTick(tickSubs, this::tickUpdate);
        }
    }

    @Override
    public void onUnload() {
        super.onUnload();
        if (tickSubs != null) {
            tickSubs.unsubscribe();
            tickSubs = null;
        }
    }

    private void tickUpdate() {
        if (getOffsetTimer() % 20 != 0) return;
        ManaReceiver receiver = XplatAbstractions.INSTANCE.findManaReceiver(getLevel(), getPos().relative(getFrontFacing()), null);
        if (receiver != null && !receiver.isFull()) {
            int mana = manaContainer.getCurrentMana();
            if (receiver instanceof ManaCollector collector) {
                mana = Math.min(mana, collector.getMaxMana() - collector.getCurrentMana());
            } else if (receiver instanceof ManaPool pool) {
                mana = Math.min(mana, pool.getMaxMana() - pool.getCurrentMana());
            }
            if (manaContainer.removeMana(mana)) {
                receiver.receiveMana(mana);
            }
        }
    }

    @Override
    public boolean shouldOpenUI(Player player, InteractionHand hand, BlockHitResult hit) {
        return false;
    }

    @Override
    public int tintColor(int index) {
        if (index == 2) {
            return GTValues.VC[getTier()];
        }
        return super.tintColor(index);
    }

    @Override
    public boolean canReceiveManaFromBursts() {
        return io == IO.IN;
    }
}
