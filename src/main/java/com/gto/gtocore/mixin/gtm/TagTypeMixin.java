package com.gto.gtocore.mixin.gtm;

import com.gto.gtocore.api.data.tag.ITagType;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.tag.TagType;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.BiFunction;

@Mixin(TagType.class)
public class TagTypeMixin implements ITagType {

    @Shadow(remap = false)
    private BiFunction<TagPrefix, Material, TagKey<Item>> formatter;

    @Shadow(remap = false)
    private boolean isParentTag;

    @Shadow(remap = false)
    @Final
    private String tagPath;

    @Override
    public String gtocore$getTagPath() {
        return tagPath;
    }

    @Override
    public ITagType gtocore$setFormatter(BiFunction<TagPrefix, Material, TagKey<Item>> formatter) {
        this.formatter = formatter;
        return this;
    }

    @Override
    public void gtocore$setParentTag() {
        isParentTag = true;
    }
}
