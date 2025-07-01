package com.gtocore.integration.jei;

import com.gtolib.GTOCore;

import net.minecraft.resources.ResourceLocation;

import appeng.integration.modules.jei.JEIPlugin;
import com.enderio.base.common.integrations.jei.EnderIOJEI;
import com.enderio.machines.common.integrations.jei.MachinesJEI;
import com.hollingsworth.arsnouveau.client.jei.JEIArsNouveauPlugin;
import dev.emi.emi.jemi.JemiPlugin;
import dev.shadowsoffire.apotheosis.ench.compat.EnchJEIPlugin;
import dev.shadowsoffire.apotheosis.potion.compat.PotionJEIPlugin;
import dev.shadowsoffire.apotheosis.village.compat.VillageJEIPlugin;
import jeresources.jei.JEIConfig;
import mezz.jei.api.IModPlugin;
import mezz.jei.library.plugins.jei.JeiInternalPlugin;
import mythicbotany.jei.MythicJei;
import org.jetbrains.annotations.NotNull;
import umpaz.farmersrespite.integration.jei.JEIFRPlugin;

import java.util.List;

public final class GTJEIPlugin implements IModPlugin {

    public static void addJEIPlugin(List<IModPlugin> list) {
        list.add(new GTJEIPlugin());
        list.add(new mezz.jei.library.plugins.vanilla.VanillaPlugin());
        list.add(new JeiInternalPlugin());
        list.add(new JEIPlugin());
        list.add(new EnderIOJEI());
        list.add(new MachinesJEI());
        list.add(new JemiPlugin());
        list.add(new EnchJEIPlugin());
        list.add(new PotionJEIPlugin());
        list.add(new VillageJEIPlugin());
        list.add(new JEIConfig());
        list.add(new MythicJei());
        list.add(new JEIFRPlugin());
        list.add(new JEIArsNouveauPlugin());
        list.add(new de.mari_023.ae2wtlib.reijei.JEIPlugin());
        list.add(new vectorwing.farmersdelight.integration.jei.JEIPlugin());
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return GTOCore.id("jei_plugin");
    }
}
