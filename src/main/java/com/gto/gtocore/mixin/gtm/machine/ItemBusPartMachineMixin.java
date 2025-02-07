package com.gto.gtocore.mixin.gtm.machine;

import com.gto.gtocore.api.machine.trait.NotifiableCircuitItemStackHandler;
import com.gto.gtocore.common.data.GTOItems;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.multiblock.part.TieredIOPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableItemStackHandler;
import com.gregtechceu.gtceu.common.machine.multiblock.part.ItemBusPartMachine;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBusPartMachine.class)
public class ItemBusPartMachineMixin extends TieredIOPartMachine {

    public ItemBusPartMachineMixin(IMachineBlockEntity holder, int tier, IO io) {
        super(holder, tier, io);
    }

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    protected int getInventorySize() {
        int sizeRoot = 1 + getTier();
        return sizeRoot * sizeRoot;
    }

    @Inject(method = "createInventory", at = @At("RETURN"), remap = false, cancellable = true)
    private void createInventory(Object[] args, CallbackInfoReturnable<NotifiableItemStackHandler> cir) {
        if (io == IO.IN) cir.setReturnValue(cir.getReturnValue().setFilter(itemStack -> !itemStack.is(GTOItems.VIRTUAL_ITEM_PROVIDER.get())));
    }

    @Inject(method = "createCircuitItemHandler", at = @At("HEAD"), remap = false, cancellable = true)
    private void createCircuitItemHandler(Object[] args, CallbackInfoReturnable<NotifiableItemStackHandler> cir) {
        if (args.length > 0 && args[0] instanceof IO i && i == IO.IN) {
            cir.setReturnValue(new NotifiableCircuitItemStackHandler(this));
        }
    }
}
