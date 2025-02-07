package com.gto.gtocore.mixin.gtmt;

import com.gto.gtocore.common.machine.multiblock.part.NeutronAcceleratorPartMachine;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.ICoverable;
import com.gregtechceu.gtceu.api.cover.CoverBehavior;
import com.gregtechceu.gtceu.api.cover.CoverDefinition;
import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.common.machine.electric.BatteryBufferMachine;
import com.gregtechceu.gtceu.common.machine.electric.HullMachine;

import net.minecraft.core.Direction;

import com.hepdd.gtmthings.api.misc.WirelessEnergyManager;
import com.hepdd.gtmthings.common.cover.WirelessEnergyReceiveCover;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import static com.gregtechceu.gtceu.api.capability.GTCapabilityHelper.getEnergyContainer;

@Mixin(WirelessEnergyReceiveCover.class)
public abstract class WirelessEnergyReceiveCoverMixin extends CoverBehavior {

    @Shadow(remap = false)
    @Final
    private int tier;

    @Shadow(remap = false)
    private UUID uuid;

    @Shadow(remap = false)
    @Final
    private int amperage;

    @Shadow(remap = false)
    @Final
    private long energyPerTick;

    @Shadow(remap = false)
    private long machineMaxEnergy;

    @Shadow(remap = false)
    protected abstract void updateCoverSub();

    protected WirelessEnergyReceiveCoverMixin(CoverDefinition definition, ICoverable coverHolder, Direction attachedSide) {
        super(definition, coverHolder, attachedSide);
    }

    @Inject(method = "canAttach", at = @At("HEAD"), remap = false, cancellable = true)
    private void canAttach(CallbackInfoReturnable<Boolean> cir) {
        if (MetaMachine.getMachine(coverHolder.getLevel(), coverHolder.getPos()) instanceof NeutronAcceleratorPartMachine neutronAcceleratorPartMachine) {
            cir.setReturnValue(neutronAcceleratorPartMachine.getTier() >= tier);
        }
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    private void updateEnergy() {
        if (uuid == null) return;
        var energyContainer = getEnergyContainer(coverHolder.getLevel(), coverHolder.getPos(), attachedSide);
        if (energyContainer != null) {
            var machine = MetaMachine.getMachine(coverHolder.getLevel(), coverHolder.getPos());
            if (machine instanceof BatteryBufferMachine || machine instanceof HullMachine || (machine instanceof NeutronAcceleratorPartMachine neutronAcceleratorPartMachine && neutronAcceleratorPartMachine.isWorkingEnabled())) {
                var changeStored = Math.min(energyContainer.getEnergyCapacity() - energyContainer.getEnergyStored(), energyPerTick);
                if (changeStored <= 0) return;
                long changeenergy = -WirelessEnergyManager.addEUToGlobalEnergyMap(uuid, -changeStored, machine);
                if (changeenergy > 0) energyContainer.acceptEnergyFromNetwork(null, (long) ((double) GTValues.V[tier] * changeenergy / changeStored), amperage);
            } else {
                var changeStored = Math.min(machineMaxEnergy - energyContainer.getEnergyStored(), energyPerTick);
                if (changeStored <= 0) return;
                changeStored = WirelessEnergyManager.addEUToGlobalEnergyMap(uuid, -changeStored, machine);
                if (changeStored < 0) energyContainer.addEnergy(-changeStored);
            }
        }
        updateCoverSub();
    }
}
