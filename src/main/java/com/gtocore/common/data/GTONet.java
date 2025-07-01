package com.gtocore.common.data;

import com.gtocore.common.network.FromClientMessage;
import com.gtocore.common.network.FromServerMessage;

import com.gtolib.GTOCore;

import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;

public final class GTONet {

    private static final SimpleNetworkManager NET = SimpleNetworkManager.create(GTOCore.MOD_ID);

    public static final MessageType SEND_DATA_FROM_CLIENT = NET.registerC2S("from_client", FromClientMessage::new);
    public static final MessageType SEND_DATA_FROM_SERVER = NET.registerS2C("from_server", FromServerMessage::new);

    public static void init() {}
}
