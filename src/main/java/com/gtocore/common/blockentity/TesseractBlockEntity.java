package com.gtocore.common.blockentity;

import com.gtocore.common.machine.electric.TesseractMachine;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.Set;

public final class TesseractBlockEntity extends MetaMachineBlockEntity {

    private static final Set<Capability<?>> CAPABILITIES = Set.of(ForgeCapabilities.ITEM_HANDLER, ForgeCapabilities.FLUID_HANDLER);

    private WeakReference<BlockEntity> blockEntityReference;

    public TesseractBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    @NotNull
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        TesseractMachine machine = (TesseractMachine) metaMachine;
        if (machine.pos != null && CAPABILITIES.contains(cap)) {
            if (blockEntityReference == null) {
                var be = level().getBlockEntity(machine.pos);
                if (be != null) {
                    blockEntityReference = new WeakReference<>(be);
                    return be.getCapability(cap, side);
                } else {
                    machine.pos = null;
                }
            } else {
                var blockEntity = blockEntityReference.get();
                if (blockEntity == null || blockEntity.isRemoved()) {
                    blockEntity = level().getBlockEntity(machine.pos);
                    if (blockEntity != null) {
                        blockEntityReference = new WeakReference<>(blockEntity);
                        return blockEntity.getCapability(cap, side);
                    } else {
                        machine.pos = null;
                    }
                } else {
                    return blockEntity.getCapability(cap, side);
                }
            }
        }
        return LazyOptional.empty();
    }
}
