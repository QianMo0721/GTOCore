package com.gtocore.mixin.gtmt;

import com.hepdd.gtmthings.common.item.IWirelessMonitor;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.math.BigInteger;

@Mixin(IWirelessMonitor.class)
public interface IWirelessMonitorAccessor {
    @Invoker(value = "getTimeToFillDrainText", remap = false)
    static Component getTimeToFillDrainText(BigInteger timeToFillSeconds) {
        throw new AssertionError("This method should not be called directly. It is intended for mixin use only.");
    }
}
