package com.gtocore.common.machine.monitor;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.entity.BlockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import org.jetbrains.annotations.Contract;

/**
 * 服务端对应的方块：
 * <p>
 * </p>
 * **存储**一个{@link ResourceLocation}的**当前**id列表
 * <p>
 * 与**定义**一个{@link ResourceLocation}的**可用**id集合
 * </p>
 * 向客户端传递这个id列表，
 * 客户端根据这个id列表来添加显示组件。
 * 需求：
 * <li>需要一个id来唯一标识每个显示组件。
 * <li>需要判断显示类型（文本，图形渲染）
 * <li>如果是文本类型，需要提供格式化的显示值。
 * <li>如果是图形渲染类型，需要提供自定义渲染逻辑。
 * <li>服务端仅使用id存储和传输数据，客户端使用id和格式化的显示值进行渲染。
 */
public interface IDisplayComponent {

    ResourceLocation getId();

    @Contract("_, -> this")
    IDisplayComponent setInformation(Object... information);

    /**
     * 给客户端使用的显示值。
     * 
     * @return 格式化的字符序列，是文本。
     */
    default FormattedCharSequence getDisplayValue() {
        return FormattedCharSequence.EMPTY;
    }

    /**
     * 自定义的渲染逻辑。
     */
    default void renderDisplay(Manager.GridNetwork network, BlockEntity blockEntity, float partialTicks, PoseStack stack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {}

    DisplayType getDisplayType();

    int getVisualWidth();

    int getVisualHeight();
}
