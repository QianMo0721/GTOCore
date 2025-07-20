package com.gtocore.common.machine.monitor;

import com.gregtechceu.gtceu.api.block.MetaMachineBlock;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;

import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MonitorBlock extends MetaMachineBlock {

    public MonitorBlock(Properties properties, MachineDefinition definition) {
        super(properties, definition);
    }

    @Override
    public @Nullable IRenderer getRenderer(@NotNull BlockState state) {
        return super.getRenderer(state);
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        if (!level.isClientSide()) {
            Manager.addBlock(state, pos, level);
        }
    }

    @Override
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        Manager.removeBlock(pState, pPos, pLevel);
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        var newState = super.rotate(state, level, pos, direction);
        if (!level.isClientSide()) {
            // 手动加入更新队列确保先移除再添加
            Manager.requireQueue(() -> Manager.removeBlock(state, pos, (Level) level));
            Manager.addBlock(newState, pos, (Level) level);
        }
        return newState;
    }
}
