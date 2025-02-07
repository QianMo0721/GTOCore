package com.gto.gtocore.api.data.tag;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;

public interface ITagType {

    String gtocore$getTagPath();

    ITagType gtocore$setFormatter(BiFunction<TagPrefix, Material, TagKey<Item>> formatter);

    void gtocore$setParentTag();
}
