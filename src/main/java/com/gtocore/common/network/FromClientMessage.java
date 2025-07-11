package com.gtocore.common.network;

import com.gtocore.common.data.GTONet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class FromClientMessage extends BaseC2SMessage {

    private final String channel;
    private Consumer<FriendlyByteBuf> consumer;
    private FriendlyByteBuf buf;

    FromClientMessage(String c, @NotNull Consumer<FriendlyByteBuf> consumer) {
        channel = c;
        this.consumer = consumer;
    }

    public FromClientMessage(FriendlyByteBuf buf) {
        channel = buf.readUtf();
        this.buf = new FriendlyByteBuf(buf.copy());
    }

    @Override
    public MessageType getType() {
        return GTONet.SEND_DATA_FROM_CLIENT;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(channel);
        consumer.accept(buf);
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        if (buf != null && !channel.isEmpty() && context.getPlayer() instanceof ServerPlayer serverPlayer) {
            ClientMessage.handle(channel, serverPlayer, buf);
        }
    }
}
