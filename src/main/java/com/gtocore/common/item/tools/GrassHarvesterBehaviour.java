package com.gtocore.common.item.tools;

import com.gtocore.data.lang.LangHandler;

import com.gtolib.api.annotation.NewDataAttributes;
import com.gtolib.api.annotation.component_builder.ComponentSupplier;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.item.component.IInteractionItem;
import com.gregtechceu.gtceu.common.item.TooltipBehavior;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class GrassHarvesterBehaviour extends TooltipBehavior implements IInteractionItem {

    private static final ComponentSupplier TOOLTIPS = NewDataAttributes.MIRACULOUS_TOOLS.create(LangHandler.CNEN.create("割草镰刀", "Grass Harvester"), b -> b.addCommentLines("""
            §6极大§r地提升小麦种子掉落概率
            右键草以收割小麦种子和稀有作物
            前期大量获取种子去种地的好帮手""",
            """
                    §6Greatly§r increases the drop rate of wheat seeds
                    Right-click grass to harvest wheat seeds and rare crops
                    A good helper for obtaining seeds in large quantities in the early game"""));
    private static final List<Component> TOOLTIPS_DATA_GEN_INITIALIZATION = TOOLTIPS.get();
    public static final GrassHarvesterBehaviour INSTANCE = new GrassHarvesterBehaviour();
    public static final Map<Item, Float> WHEAT_SEEDS = Map.of(
            Items.WHEAT_SEEDS, 0.9F,
            Items.BEETROOT_SEEDS, 0.1F,
            Items.MELON_SEEDS, 0.005F,
            Items.PUMPKIN_SEEDS, 0.005F,
            Items.POTATO, 0.005F,
            Items.CARROT, 0.005F);
    private static final Map<Block, Map<Item, Float>> HARVESTABLE_BLOCKS = Map.of(
            Blocks.GRASS, WHEAT_SEEDS,
            Blocks.TALL_GRASS, WHEAT_SEEDS,
            Blocks.SEAGRASS, WHEAT_SEEDS,
            Blocks.TALL_SEAGRASS, WHEAT_SEEDS,
            Blocks.FERN, WHEAT_SEEDS,
            Blocks.LARGE_FERN, WHEAT_SEEDS);

    private GrassHarvesterBehaviour() {
        super(lines -> lines.addAll(TOOLTIPS.get()));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        ItemStack itemInHand = context.getItemInHand();
        List<ItemStack> bonus = new ArrayList<>();
        if (level instanceof ServerLevel serverLevel && player != null) {
            BlockPos clickedPos = context.getClickedPos();
            Block block = serverLevel.getBlockState(clickedPos).getBlock();
            boolean isMatch = false;
            var items = HARVESTABLE_BLOCKS.get(block);
            if (items != null) {
                isMatch = true;
                items.forEach((item, chance) -> {
                    if (GTValues.RNG.nextFloat() < chance) {
                        bonus.add(new ItemStack(item, (int) Math.max(1, chance * ((GTValues.RNG.nextInt(1, 3))))));
                    }
                });
            }
            if (isMatch) {
                // 掉落物
                bonus.forEach(itemStack -> Block.popResource(level, clickedPos, itemStack));
                // 消耗耐久
                itemInHand.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(context.getHand()));
                // 破坏方块
                level.destroyBlock(clickedPos, false);
                level.playSound(null, clickedPos, SoundEvents.GRASS_BREAK, SoundSource.BLOCKS, 1.0F, 0.8F + (GTValues.RNG.nextFloat() * 0.4F));
            } else {
                return InteractionResult.FAIL;
            }
        }

        return InteractionResult.SUCCESS;
    }
}
