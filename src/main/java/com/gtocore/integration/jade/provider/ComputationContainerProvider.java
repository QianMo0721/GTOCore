package com.gtocore.integration.jade.provider;

import com.gtocore.common.machine.multiblock.part.WirelessNetworkComputationHatchMachine;

import com.gtolib.GTOCore;
import com.gtolib.api.wireless.WirelessComputationContainer;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.integration.jade.provider.CapabilityBlockProvider;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import org.jetbrains.annotations.Nullable;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;

public final class ComputationContainerProvider extends CapabilityBlockProvider<WirelessComputationContainer> {

    public ComputationContainerProvider() {
        super(GTOCore.id("computation_container_provider"));
    }

    @Nullable
    @Override
    protected WirelessComputationContainer getCapability(Level level, BlockPos pos, BlockEntity blockEntity, @Nullable Direction side) {
        var machine = MetaMachine.getMachine(blockEntity);
        if (machine instanceof WirelessNetworkComputationHatchMachine hatchMachine) {
            return hatchMachine.getTrait().getWirelessComputationContainer();
        }
        return null;
    }

    @Override
    protected void write(CompoundTag data, WirelessComputationContainer capability) {
        if (capability != null) {
            data.putLong("capacity", capability.getCapacity());
            data.putLong("storage", capability.getStorage());
        }
    }

    @Override
    protected void addTooltip(CompoundTag capData, ITooltip tooltip, Player player, BlockAccessor block, BlockEntity blockEntity, IPluginConfig config) {
        long capacity = capData.getLong("capacity");
        if (capacity == 0) return;
        long storage = capData.getLong("storage");
        IElementHelper helper = tooltip.getElementHelper();
        tooltip.add(helper.progress(getProgress(storage, capacity), Component.literal(storage + " / " + capacity + " CWU"), tooltip.getElementHelper().progressStyle().color(0xFF006D6A).textColor(-1), Util.make(BoxStyle.DEFAULT, style -> style.borderColor = 0xFF555555), true));
    }
}
