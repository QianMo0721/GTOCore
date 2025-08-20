package com.gtocore.common.data;

import com.gtolib.utils.RLUtils;

import com.gregtechceu.gtceu.core.MixinHelpers;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagLoader;

import java.util.List;
import java.util.Map;

public final class GTOTags {

    private static final ResourceLocation RED_ALLOY_INGOT = RLUtils.parse("morered:red_alloy_ingot");

    public static boolean cache;

    public static <T> void generateDynamicTags(Map<ResourceLocation, List<TagLoader.EntryWithSource>> tagMap, Registry<T> registry) {
        MixinHelpers.generateGTDynamicTags(tagMap, registry);
    }

    public static TagEntry convert(ResourceLocation id, TagEntry tagEntry) {
        if (cache) return tagEntry;
        if (tagEntry.getId().equals(RED_ALLOY_INGOT)) {
            cache = true;
            return null;
        }
        return tagEntry;
    }
}
