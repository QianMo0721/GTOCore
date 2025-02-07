package com.gto.gtocore.mixin.gtm.capability;

import com.gregtechceu.gtceu.api.capability.GTCapabilityHelper;
import com.gregtechceu.gtceu.api.capability.IElectricItem;
import com.gregtechceu.gtceu.api.item.ComponentItem;
import com.gregtechceu.gtceu.api.item.capability.ElectricItem;
import com.gregtechceu.gtceu.api.item.component.ElectricStats;
import com.gregtechceu.gtceu.api.item.component.IItemComponent;

import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GTCapabilityHelper.class)
public class GTCapabilityHelperMixin {

    @Inject(method = "getElectricItem", at = @At("HEAD"), remap = false, cancellable = true)
    private static void getElectricItem(ItemStack itemStack, CallbackInfoReturnable<IElectricItem> cir) {
        if (itemStack.getItem() instanceof ComponentItem componentItem) {
            for (IItemComponent component : componentItem.getComponents()) {
                if (component instanceof ElectricStats electricStats) {
                    cir.setReturnValue(new ElectricItem(itemStack, electricStats.maxCharge, electricStats.tier, electricStats.chargeable, electricStats.dischargeable));
                    return;
                }
            }
        }
    }
}
