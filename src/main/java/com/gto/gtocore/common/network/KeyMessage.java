package com.gto.gtocore.common.network;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.api.entity.IEnhancedPlayer;
import com.gto.gtocore.client.ClientUtil;

import com.gregtechceu.gtceu.api.item.IGTTool;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public final class KeyMessage {

    public static boolean disableDrift;

    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(GTOCore.MOD_ID, GTOCore.MOD_ID), () -> "123", "123"::equals, "123"::equals);
    public static int messageID;

    private final int type;
    private final int pressedMillis;

    public KeyMessage(int type, int pressedMillis) {
        this.type = type;
        this.pressedMillis = pressedMillis;
    }

    public KeyMessage(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readInt());
    }

    public static void buffer(KeyMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedMillis);
    }

    public static void handler(KeyMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player sender = context.getSender();
            if (sender != null) {
                pressAction(sender, message.type);
            }
        });
        context.setPacketHandled(true);
    }

    private static void pressAction(Player player, int type) {
        Level level = player.level();
        if (!level.hasChunkAt(player.blockPosition())) {
            return;
        }
        switch (type) {
            case 0:
                handleFlightSpeed(player);
                break;
            case 1:
                toggleNightVision(player);
                break;
            case 2:
                upgradeToolSpeed(player);
                break;
        }
    }

    private static void handleFlightSpeed(Player player) {
        float speed;
        String armorSlots = player.getArmorSlots().toString();
        if (armorSlots.contains("warden_")) {
            speed = 0.2F;
        } else if (armorSlots.contains("infinity_")) {
            speed = 0.3F;
        } else {
            return;
        }
        CompoundTag data = player.getPersistentData();
        int speedFactor = data.getInt("fly_speed");
        if (player.isShiftKeyDown()) {
            player.getAbilities().setFlyingSpeed(0.05F);
            player.onUpdateAbilities();
            player.displayClientMessage(Component.translatable("gtocore.fly_speed_reset"), true);
            data.putInt("fly_speed", 1);
        } else {
            float currentSpeed = player.getAbilities().getFlyingSpeed();
            if (currentSpeed < speed) {
                player.getAbilities().setFlyingSpeed(0.05F * speedFactor);
                player.onUpdateAbilities();
                data.putInt("fly_speed", speedFactor + 1);
                player.displayClientMessage(Component.translatable("gtocore.fly_speed", (speedFactor + 1)), true);
            } else {
                player.displayClientMessage(Component.translatable("gtocore.reach_limit"), true);
            }
        }
    }

    private static void toggleNightVision(Player player) {
        if (((IEnhancedPlayer) player).gTOCore$canFly()) {
            CompoundTag data = player.getPersistentData();
            boolean nightVisionEnabled = data.getBoolean("night_vision");
            data.putBoolean("night_vision", !nightVisionEnabled);

            if (nightVisionEnabled) {
                player.removeEffect(MobEffects.NIGHT_VISION);
                player.displayClientMessage(Component.translatable("metaarmor.message.nightvision.disabled"), true);
            } else {
                player.displayClientMessage(Component.translatable("metaarmor.message.nightvision.enabled"), true);
            }
        }
    }

    private static void upgradeToolSpeed(Player player) {
        ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.getItem() instanceof IGTTool gtTool && gtTool.getToolType().name.contains("_vajra")) {
            int value = player.isShiftKeyDown() ? 10 : 1;
            float speed = itemStack.getOrCreateTag().getCompound("GT.Tool").getFloat("ToolSpeed");
            float newSpeed = adjustToolSpeed(speed, value, (int) gtTool.getMaterialToolSpeed(itemStack));
            itemStack.getOrCreateTag().getCompound("GT.Tool").putFloat("ToolSpeed", newSpeed);
            player.displayClientMessage(Component.translatable("jade.horseStat.speed", newSpeed), true);
        }
    }

    private static float adjustToolSpeed(float speed, int value, int max) {
        if (speed < max) {
            if (speed < 100) {
                return speed + value;
            } else {
                return speed + value * 10;
            }
        } else {
            return 10;
        }
    }

    public static void drift() {
        disableDrift = !disableDrift;
        if (disableDrift) {
            ClientUtil.getPlayer().displayClientMessage(Component.translatable("key.gtocore.drift").append(": ").append(Component.translatable("gtocore.machine.off")), true);
        } else {
            ClientUtil.getPlayer().displayClientMessage(Component.translatable("key.gtocore.drift").append(": ").append(Component.translatable("gtocore.machine.on")), true);
        }
    }
}
