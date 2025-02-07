package com.gto.gtocore.mixin.gtm.registry;

import com.gregtechceu.gtceu.api.registry.GTRegistry;

import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;

@Mixin(GTRegistry.RL.class)
public abstract class GTRegistryRlMixin<V> extends GTRegistry<ResourceLocation, V> {

    protected GTRegistryRlMixin(ResourceLocation registryName) {
        super(registryName);
    }

    @Override
    public <T extends V> T registerOrOverride(ResourceLocation key, T value) {
        this.registry.put(key, value);
        return value;
    }
}
