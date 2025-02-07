package com.gto.gtocore.mixin.gtm.item;

import com.gto.gtocore.api.data.chemical.material.GTOMaterial;

import com.gregtechceu.gtceu.api.block.MaterialPipeBlock;
import com.gregtechceu.gtceu.api.item.MaterialPipeBlockItem;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MaterialPipeBlockItem.class)
public class MaterialPipeBlockItemMixin {

    @ModifyVariable(method = "<init>", at = @At("HEAD"), index = 2, argsOnly = true, remap = false)
    private static Item.Properties init(Item.Properties value, @Local(argsOnly = true) MaterialPipeBlock block) {
        if (block.material instanceof GTOMaterial material) {
            Rarity rarity = material.gtocore$rarity();
            if (rarity != null) value.rarity(rarity);
        }
        return value;
    }
}
