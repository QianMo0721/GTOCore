package com.gto.gtocore.api.data.chemical.material;

import net.minecraft.world.item.Rarity;

public interface GTOMaterial {

    Rarity gtocore$rarity();

    void gtocore$setRarity(Rarity rarity);
}
