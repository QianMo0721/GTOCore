package com.gto.gtocore.mixin.registrate;

import net.minecraft.world.level.block.Block;

import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.nullness.NonNullBiConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(BlockBuilder.class)
public class BlockBuilderMixin<T extends Block, P> {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public BlockBuilder<T, P> loot(NonNullBiConsumer<RegistrateBlockLootTables, T> cons) {
        return (BlockBuilder<T, P>) (Object) this;
    }
}
