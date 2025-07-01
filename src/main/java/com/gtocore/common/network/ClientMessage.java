package com.gtocore.common.network;

import com.gtolib.api.misc.PlanetManagement;
import com.gtolib.api.player.IEnhancedPlayer;
import com.gtolib.api.player.organ.capability.OrganCapability;
import com.gtolib.utils.SortUtils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import com.lowdragmc.lowdraglib.gui.modular.ModularUIContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ClientMessage {

    public static void sendData(String channel, @Nullable CompoundTag data) {
        new FromClientMessage(channel, data).sendToServer();
    }

    public static void checkPlanetIsUnlocked(ResourceLocation planet) {
        if (PlanetManagement.isClientUnlocked(planet)) return;
        CompoundTag data = new CompoundTag();
        data.putString("planet", planet.toString());
        sendData("planetIsUnlocked", data);
    }

    public static void organCapAsync(CompoundTag tag) { // ServerToClient 全量同步
        sendData("organCapAsync", tag);
    }

    static void handle(String channel, @NotNull ServerPlayer serverPlayer, CompoundTag data) {
        switch (channel) {
            case "planetIsUnlocked": {
                ResourceLocation planet = new ResourceLocation(data.getString("planet"));
                PlanetManagement.checkIsUnlocked(serverPlayer, planet);
                break;
            }
            case "sortInventory": {
                if (serverPlayer.containerMenu instanceof ModularUIContainer container) {
                    SortUtils.sort(container);
                }
                break;
            }
            case "shiftKeypress": {
                IEnhancedPlayer.of(serverPlayer).getPlayerData().shiftState = data.getBoolean("shift");
                break;
            }
            case "organCapAsync": {
                OrganCapability.of(serverPlayer).deserializeNBT(data);
                break;
            }
            default: {
                KeyMessage.pressAction(serverPlayer, channel);
            }
        }
    }
}
