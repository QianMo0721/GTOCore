package com.gto.gtocore.mixin.gtm.chemical;

import com.gto.gtocore.api.data.tag.ITagPrefix;
import com.gto.gtocore.api.data.tag.ITagType;
import com.gto.gtocore.utils.TagUtils;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.data.tag.TagType;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.PREFIXES;

@Mixin(TagPrefix.class)
public class TagPrefixMixin implements ITagPrefix {

    @Unique
    private static Set<String> gTOCore$ORE;

    @Unique
    private boolean gtocore$isTagInput;

    @Unique
    private static Set<String> gtocore$filter;

    @Override
    public boolean gtocore$isTagInput() {
        return gtocore$isTagInput;
    }

    @Shadow(remap = false)
    @Final
    public String name;

    @Shadow(remap = false)
    @Final
    protected List<TagType> tags;

    @Inject(method = "defaultTagPath(Ljava/lang/String;)Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;", at = @At("HEAD"), remap = false, cancellable = true)
    private void defaultTagPath(String path, CallbackInfoReturnable<TagPrefix> cir) {
        if (gtocore$filter.contains(name)) {
            gtocore$isTagInput = true;
            return;
        }
        TagType type = TagType.withCustomFormatter(path, (prefix, mat) -> null);
        ((ITagType) type).gtocore$setFormatter((prefix, mat) -> TagUtils.createTGTag(((ITagType) type).gtocore$getTagPath().formatted(mat.getName())));
        this.tags.add(type);
        cir.setReturnValue((TagPrefix) (Object) this);
    }

    @Inject(method = "unformattedTagPath(Ljava/lang/String;)Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;", at = @At("HEAD"), remap = false, cancellable = true)
    private void unformattedTagPath(String path, CallbackInfoReturnable<TagPrefix> cir) {
        if ("ores".equals(path)) return;
        TagType type = TagType.withCustomFormatter(path, (prefix, mat) -> null);
        ((ITagType) type).gtocore$setFormatter((prefix, mat) -> TagUtils.createTGTag(((ITagType) type).gtocore$getTagPath())).gtocore$setParentTag();
        this.tags.add(type);
        cir.setReturnValue((TagPrefix) (Object) this);
    }

    @Inject(method = "unformattedTagPath(Ljava/lang/String;Z)Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;", at = @At("HEAD"), remap = false)
    private void unformattedTagPath(String path, boolean isVanilla, CallbackInfoReturnable<TagPrefix> cir) {
        if (isVanilla) {
            gtocore$isTagInput = true;
        }
    }

    @Inject(method = "oreTagPrefix", at = @At("TAIL"), remap = false)
    private static void oreTagPrefix(String name, TagKey<Block> miningToolTag, CallbackInfoReturnable<TagPrefix> cir) {
        if (gTOCore$ORE != null && gTOCore$ORE.contains(name)) {
            PREFIXES.remove(name);
        }
    }

    @Inject(method = "registerOre(Ljava/util/function/Supplier;Ljava/util/function/Supplier;Ljava/util/function/Supplier;Lnet/minecraft/resources/ResourceLocation;ZZZ)Lcom/gregtechceu/gtceu/api/data/tag/TagPrefix;", at = @At("HEAD"), remap = false, cancellable = true)
    private void registerOre(Supplier<BlockState> stoneType, Supplier<Material> material, Supplier<BlockBehaviour.Properties> properties, ResourceLocation baseModelLocation, boolean doubleDrops, boolean isSand, boolean shouldDropAsItem, CallbackInfoReturnable<TagPrefix> cir) {
        if (gTOCore$ORE != null && gTOCore$ORE.contains(name)) {
            cir.setReturnValue((TagPrefix) (Object) this);
        }
    }

    @Inject(method = "<clinit>", at = @At("HEAD"), remap = false)
    private static void init(CallbackInfo ci) {
        gtocore$filter = Set.of("dye", "lens");
        gTOCore$ORE = Set.of("granite", "diorite", "andesite", "red_granite", "marble", "tuff", "sand", "redSand", "gravel", "basalt", "blackstone");
    }

    @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)
    private static void initT(CallbackInfo ci) {
        gTOCore$ORE = null;
    }
}
