package com.gto.gtocore.api.gui;

import com.gto.gtocore.GTOCore;

import com.lowdragmc.lowdraglib.gui.texture.ResourceTexture;

public final class GTOGuiTextures {

    public static final ResourceTexture PLANET_TELEPORT = createTexture("planet_teleport");

    public static final ResourceTexture HIGH_SPEED_MODE = createTexture("high_speed_mode");

    private static ResourceTexture createTexture(String location) {
        return new ResourceTexture(GTOCore.id("textures/gui/%s.png".formatted(location)));
    }
}
