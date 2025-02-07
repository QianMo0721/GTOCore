package com.gto.gtocore.integration.kjs;

import net.minecraft.server.packs.resources.ResourceManager;

import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import org.jetbrains.annotations.Nullable;

public final class EmptyScriptManager extends ScriptManager {

    public EmptyScriptManager(ScriptType t) {
        super(t);
    }

    @Override
    public void reload(@Nullable ResourceManager resourceManager) {}
}
