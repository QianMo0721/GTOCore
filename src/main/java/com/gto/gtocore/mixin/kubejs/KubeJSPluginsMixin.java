package com.gto.gtocore.mixin.kubejs;

import com.gto.gtocore.integration.kjs.GTKubeJSPlugin;

import dev.architectury.platform.Mod;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ClassFilter;
import dev.latvian.mods.kubejs.util.KubeJSPlugins;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(KubeJSPlugins.class)
public final class KubeJSPluginsMixin {

    @Shadow(remap = false)
    @Final
    private static List<KubeJSPlugin> LIST;

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static void load(List<Mod> mods, boolean loadClientPlugins) {
        LIST.add(new GTKubeJSPlugin());
    }

    /**
     * @author .
     * @reason .
     */

    @Overwrite(remap = false)
    public static ClassFilter createClassFilter(ScriptType type) {
        return new ClassFilter();
    }
}
