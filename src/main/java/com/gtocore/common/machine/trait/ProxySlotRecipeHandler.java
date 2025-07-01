package com.gtocore.common.machine.trait;

import com.gtocore.common.machine.multiblock.part.ae.MEPatternBufferPartMachine;
import com.gtocore.common.machine.multiblock.part.ae.MEPatternBufferProxyPartMachine;

import com.gtolib.api.machine.trait.ProxyFluidExtendRecipeHandler;
import com.gtolib.api.machine.trait.ProxyFluidRecipeHandler;
import com.gtolib.api.machine.trait.ProxyItemExtendRecipeHandler;
import com.gtolib.api.machine.trait.ProxyItemRecipeHandler;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;

import java.util.ArrayList;
import java.util.List;

public final class ProxySlotRecipeHandler {

    public static final ProxySlotRecipeHandler DEFAULT = new ProxySlotRecipeHandler(null, null);
    private final List<RecipeHandlerList> proxySlotHandlers;

    public ProxySlotRecipeHandler(MEPatternBufferProxyPartMachine machine, MEPatternBufferPartMachine patternBuffer) {
        int slots = patternBuffer == null ? 0 : patternBuffer.maxPatternCount;
        proxySlotHandlers = new ArrayList<>(slots);
        for (int i = 0; i < slots; ++i) {
            proxySlotHandlers.add(new ProxyRHL(machine, patternBuffer.getInternalInventory()[i]));
        }
    }

    public void updateProxy(MEPatternBufferPartMachine patternBuffer) {
        var slotHandlers = patternBuffer.getInternalRecipeHandler().getSlotHandlers();
        for (int i = 0; i < proxySlotHandlers.size(); ++i) {
            ProxyRHL proxyRHL = (ProxyRHL) proxySlotHandlers.get(i);
            proxyRHL.setBuffer(patternBuffer, (InternalSlotRecipeHandler.SlotRHL) slotHandlers.get(i));
        }
    }

    public void clearProxy() {
        for (var slotHandler : proxySlotHandlers) {
            ((ProxyRHL) slotHandler).clearBuffer();
        }
    }

    private static final class ProxyRHL extends InternalSlotRecipeHandler.AbstractRHL {

        private final ProxyItemRecipeHandler circuit;
        private final ProxyItemRecipeHandler sharedItem;
        private final ProxyItemRecipeHandler slotItem;
        private final ProxyFluidRecipeHandler sharedFluid;
        private final ProxyFluidRecipeHandler slotFluid;

        private ProxyRHL(MEPatternBufferProxyPartMachine machine, MEPatternBufferPartMachine.InternalSlot slot) {
            super(IO.IN, slot, machine);
            circuit = new ProxyItemExtendRecipeHandler(machine);
            sharedItem = new ProxyItemExtendRecipeHandler(machine);
            slotItem = new ProxyItemExtendRecipeHandler(machine);
            sharedFluid = new ProxyFluidExtendRecipeHandler(machine);
            slotFluid = new ProxyFluidExtendRecipeHandler(machine);
            addHandlers(circuit, sharedItem, slotItem, sharedFluid, slotFluid);
        }

        private void setBuffer(MEPatternBufferPartMachine buffer, InternalSlotRecipeHandler.SlotRHL slotRHL) {
            circuit.setProxy(buffer.getCircuitInventory());
            sharedItem.setProxy(buffer.getShareInventory());
            sharedFluid.setProxy(buffer.getShareTank());
            slotItem.setProxy(slotRHL.itemRecipeHandler);
            slotFluid.setProxy(slotRHL.fluidRecipeHandler);
        }

        private void clearBuffer() {
            circuit.setProxy(null);
            sharedItem.setProxy(null);
            sharedFluid.setProxy(null);
            slotItem.setProxy(null);
            slotFluid.setProxy(null);
        }
    }

    public List<RecipeHandlerList> getProxySlotHandlers() {
        return this.proxySlotHandlers;
    }
}
