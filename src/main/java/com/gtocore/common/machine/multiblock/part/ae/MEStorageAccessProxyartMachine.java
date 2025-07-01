package com.gtocore.common.machine.multiblock.part.ae;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.MetaMachine;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import appeng.api.storage.MEStorage;
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class MEStorageAccessProxyartMachine extends MEStorageAccessPartMachine {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(MEStorageAccessProxyartMachine.class, MEStorageAccessPartMachine.MANAGED_FIELD_HOLDER);
    @Persisted
    @DescSynced
    @Nullable
    private BlockPos accessPos;
    @Nullable
    private MEStorageAccessPartMachine access = null;
    private boolean accessResolved = false;

    public MEStorageAccessProxyartMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    @NotNull
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    @Override
    boolean contains(List<MEStorage> list) {
        return access == null || access.contains(list);
    }

    private void setAccess(@Nullable BlockPos pos) {
        accessResolved = true;
        var level = getLevel();
        if (level == null || pos == null) {
            access = null;
        } else if (MetaMachine.getMachine(level, pos) instanceof MEStorageAccessPartMachine machine && machine != this) {
            accessPos = pos;
            access = machine;
            machine.addProxy(this);
            setCapacity(machine.getCapacity());
            setUUID(machine.uuid);
            setInfinite(machine.isInfinite());
            setCheck(true);
        } else {
            access = null;
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (getLevel() instanceof ServerLevel level) {
            level.getServer().tell(new TickTask(0, () -> this.setAccess(accessPos)));
        }
    }

    @Override
    public void onMachineRemoved() {
        super.onMachineRemoved();
        if (!accessResolved) setAccess(accessPos);
        if (access != null) {
            access.removeProxy(this);
        }
    }

    @Override
    public InteractionResult onDataStickUse(Player player, ItemStack dataStick) {
        if (dataStick.hasTag()) {
            assert dataStick.getTag() != null;
            if (dataStick.getTag().contains("pos", Tag.TAG_INT_ARRAY)) {
                var posArray = dataStick.getOrCreateTag().getIntArray("pos");
                var pos = new BlockPos(posArray[0], posArray[1], posArray[2]);
                setAccess(pos);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
