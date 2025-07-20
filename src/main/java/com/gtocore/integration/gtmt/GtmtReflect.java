package com.gtocore.integration.gtmt;

import com.gtolib.GTOCore;

import net.minecraft.network.chat.Component;

import java.lang.reflect.Method;
import java.math.BigInteger;

public class GtmtReflect {

    public static Component getTimeToFillDrainText(BigInteger value) {
        try {
            Class<?> clazz = Class.forName("com.hepdd.gtmthings.common.item.IWirelessMonitor");
            Method method = clazz.getDeclaredMethod("getTimeToFillDrainText", BigInteger.class);
            method.setAccessible(true);
            return (Component) method.invoke(null, value);
        } catch (Exception e) {
            GTOCore.LOGGER.error("Failed to invoke getTimeToFillDrainText method via reflection", e);
            return Component.empty();
        }
    }
}
