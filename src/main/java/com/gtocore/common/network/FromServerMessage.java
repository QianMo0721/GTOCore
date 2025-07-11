package com.gtocore.common.network;

import com.gtocore.common.data.GTONet;

import net.minecraft.network.FriendlyByteBuf;

import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class FromServerMessage extends BaseS2CMessage {

    private final String channel;
    private Consumer<FriendlyByteBuf> consumer;
    private FriendlyByteBuf buf;

    FromServerMessage(String c, @NotNull Consumer<FriendlyByteBuf> consumer) {
        channel = c;
        this.consumer = consumer;
    }

    public FromServerMessage(FriendlyByteBuf buf) {
        channel = buf.readUtf();
        this.buf = new FriendlyByteBuf(buf.copy());
    }

    @Override
    public MessageType getType() {
        return GTONet.SEND_DATA_FROM_SERVER;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(channel);
        consumer.accept(buf);
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        if (buf != null && !channel.isEmpty()) {
            ServerMessage.handle(channel, context.getPlayer(), buf);
        }
    }
}
