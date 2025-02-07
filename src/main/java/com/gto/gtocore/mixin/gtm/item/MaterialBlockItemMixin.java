package com.gto.gtocore.mixin.gtm.item;

import com.gto.gtocore.api.data.chemical.material.GTOMaterial;
import com.gto.gtocore.api.data.chemical.material.info.GTOMaterialIconSet;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.block.MaterialBlock;
import com.gregtechceu.gtceu.api.item.MaterialBlockItem;
import com.gregtechceu.gtceu.api.item.component.ICustomRenderer;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import com.llamalad7.mixinextras.sugar.Local;
import com.lowdragmc.lowdraglib.client.renderer.IRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaterialBlockItem.class)
public class MaterialBlockItemMixin {

    @Unique
    private ICustomRenderer gtocore$customRenderer;

    @ModifyVariable(method = "<init>", at = @At("HEAD"), index = 2, argsOnly = true, remap = false)
    private static Item.Properties init(Item.Properties value, @Local(argsOnly = true) MaterialBlock block) {
        if (block.material instanceof GTOMaterial material) {
            Rarity rarity = material.gtocore$rarity();
            if (rarity != null) value.rarity(rarity);
        }
        return value;
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void MaterialBlockItem(MaterialBlock block, Item.Properties properties, CallbackInfo ci) {
        if (GTCEu.isClientSide()) {
            if (block.material.getMaterialIconSet() instanceof GTOMaterialIconSet iconSet) {
                gtocore$customRenderer = iconSet.getCustomRenderer();
            }
        }
    }

    @Inject(method = "getRenderer", at = @At("HEAD"), remap = false, cancellable = true)
    private void getRenderer(ItemStack stack, CallbackInfoReturnable<IRenderer> cir) {
        if (gtocore$customRenderer != null) {
            cir.setReturnValue(gtocore$customRenderer.getRenderer());
        }
    }
}
