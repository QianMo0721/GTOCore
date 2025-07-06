package com.gtocore.data;

import com.gtolib.api.annotation.Scanned;

@Scanned
public final class IdleReason extends com.gtolib.api.recipe.IdleReason {

    public static final IdleReason ORDERED = new IdleReason("gtocore.idle_reason.ordered", "未满足有序要求", "Ordered Not Satisfies");
    public static final IdleReason SET_CIRCUIT = new IdleReason("gtocore.idle_reason.set_circuit", "需要设置电路", "Need to set circuit");

    public static final IdleReason GRIND_BALL = new IdleReason("gtocore.idle_reason.grindball", "需要研磨球", "Need to grind ball");

    public static final IdleReason CHARGE = new IdleReason("gtocore.idle_reason.charge", "需要充能", "Need to charge");

    public static final IdleReason FELLING_TOOL = new IdleReason("gtocore.idle_reason.felling_tool", "需要伐木工具", "Need to felling tool");

    public IdleReason(String key, String cn, String en) {
        super(key, en, cn);
    }
}
