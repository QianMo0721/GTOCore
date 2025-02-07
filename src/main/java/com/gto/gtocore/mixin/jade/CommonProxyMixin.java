package com.gto.gtocore.mixin.jade;

import com.gto.gtocore.integration.jade.GTOJadePlugin;

import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import appeng.integration.modules.jade.JadeModule;
import dev.shadowsoffire.apotheosis.adventure.compat.AdventureHwylaPlugin;
import dev.shadowsoffire.apotheosis.ench.compat.EnchHwylaPlugin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import snownee.jade.Jade;
import snownee.jade.addon.core.CorePlugin;
import snownee.jade.addon.universal.UniversalPlugin;
import snownee.jade.addon.vanilla.VanillaPlugin;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.impl.WailaClientRegistration;
import snownee.jade.impl.WailaCommonRegistration;
import snownee.jade.util.CommonProxy;

import java.util.List;

@Mixin(CommonProxy.class)
public class CommonProxyMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    private void loadComplete(FMLLoadCompleteEvent event) {
        List<IWailaPlugin> plugins = List.of(new VanillaPlugin(), new UniversalPlugin(), new CorePlugin(), new JadeModule(), new GTOJadePlugin(), new AdventureHwylaPlugin(), new EnchHwylaPlugin());
        for (IWailaPlugin plugin : plugins) {
            plugin.register(WailaCommonRegistration.INSTANCE);
            if (CommonProxy.isPhysicallyClient()) {
                plugin.registerClient(WailaClientRegistration.INSTANCE);
            }
        }
        Jade.loadComplete();
    }
}
