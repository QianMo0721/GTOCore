package com.gtocore.data.recipe.mod;

import com.gregtechceu.gtceu.GTCEu;

import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Predicate;

public final class ExtraMods {

    public static void initCustomJsonFilter(List<Predicate<ResourceLocation>> filters) {
        if (GTCEu.isModLoaded("sophisticatedstorage")) {
            filters.add(id -> id.getNamespace().equals("sophisticatedstorage"));
        }
        if (GTCEu.isModLoaded("pipez")) {
            filters.add(id -> id.getNamespace().equals("pipez"));
        }
    }

    // TODO 添加配方
}
