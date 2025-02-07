package com.gto.gtocore.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;

import dev.latvian.mods.kubejs.core.MinecraftServerKJS;

public final class ServerUtils {

    public static CompoundTag getPersistentData(MinecraftServer server) {
        return ((MinecraftServerKJS) server).kjs$getPersistentData();
    }

    public static void runCommandSilent(MinecraftServer server, String command) {
        ((MinecraftServerKJS) server).kjs$runCommandSilent(command);
    }
}
