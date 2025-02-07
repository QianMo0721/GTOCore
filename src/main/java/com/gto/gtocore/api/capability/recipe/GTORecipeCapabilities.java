package com.gto.gtocore.api.capability.recipe;

import com.gregtechceu.gtceu.api.addon.events.KJSRecipeKeyEvent;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.integration.kjs.recipe.components.ContentJS;

import com.mojang.datafixers.util.Pair;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;

public final class GTORecipeCapabilities {

    private static final ContentJS<Long> MANA_IN = new ContentJS<>(NumberComponent.ANY_LONG, ManaRecipeCapability.CAP, false);
    private static final ContentJS<Long> MANA_OUT = new ContentJS<>(NumberComponent.ANY_LONG, ManaRecipeCapability.CAP, true);

    public static void init() {
        GTRegistries.RECIPE_CAPABILITIES.register(ManaRecipeCapability.CAP.name, ManaRecipeCapability.CAP);
    }

    public static void registerRecipeKeys(KJSRecipeKeyEvent event) {
        event.registerKey(ManaRecipeCapability.CAP, Pair.of(MANA_IN, MANA_OUT));
    }
}
