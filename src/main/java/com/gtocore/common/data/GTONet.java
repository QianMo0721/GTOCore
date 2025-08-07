package com.gtocore.common.data;

import com.gtocore.common.network.FromClientMessage;
import com.gtocore.common.network.FromServerMessage;

import com.gtolib.GTOCore;

import dev.architectury.networking.simple.MessageType;

public final class GTONet {

    public static final MessageType SEND_DATA_FROM_CLIENT = GTOCore.NETWORK_MANAGER.registerC2S("from_client", FromClientMessage::new);
    public static final MessageType SEND_DATA_FROM_SERVER = GTOCore.NETWORK_MANAGER.registerS2C("from_server", FromServerMessage::new);

    public static void init() {}
}
