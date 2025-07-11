package com.gtocore.api.gui.graphic.component

import com.gtocore.api.gui.graphic.TooltipComponent
import com.gtocore.api.gui.graphic.helper.TextBlockHelper

import net.minecraft.client.gui.GuiGraphics

/**
 * 纯文本块组件 - 不包含边框，专注于文本渲染
 *
 * @param text 要显示的文本内容
 * @param maxWidth 最大宽度（像素）
 * @param lineGap 行间距
 * @param scale 文本缩放比例
 * @param textColor 文本颜色
 */
class TextBlockComponent(private val text: String, private val maxWidth: Int = 200, private val lineGap: Int = 1, private val scale: Float = 1f, private val textColor: Int = 0xFFE8F4FD.toInt()) : TooltipComponent {
    // region 缓存尺寸
    private val cachedSize: Pair<Int, Int> by lazy {
        TextBlockHelper.calculateTextBlockSize(
            text = text,
            lineGap = lineGap,
            maxWidth = maxWidth,
        )
    }
    // endregion

    // region 主渲染流程
    override fun render(graphics: GuiGraphics) {
        if (text.isEmpty()) return
        graphics.pose().pushPose()
        graphics.pose().translate(0.0, 1.0, 0.0)
        TextBlockHelper.drawTextBlock(
            graphics = graphics,
            text = text,
            lineGap = lineGap,
            scale = scale,
            color = textColor,
            maxWidth = maxWidth,
        )
        graphics.pose().popPose()
    }
    // endregion

    // region 尺寸
    override fun getHeight(): Int = cachedSize.second
    override fun getWidth(): Int = cachedSize.first
    // endregion

    companion object {
        // region 静态工厂
        /**
         * 创��标准文本块
         */
        fun createStandard(text: String, maxWidth: Int = 200): TextBlockComponent = TextBlockComponent(text, maxWidth)

        /**
         * 创建高对比度文本块
         */
        fun createHighContrast(text: String, maxWidth: Int = 200): TextBlockComponent = TextBlockComponent(text, maxWidth, textColor = 0xFFFFFFFF.toInt())

        /**
         * 创建紧凑型文本块
         */
        fun createCompact(text: String, maxWidth: Int = 200): TextBlockComponent = TextBlockComponent(text, maxWidth, lineGap = 0, scale = 0.9f)

        /**
         * 创建带边框的标准科幻风格文本块
         */
        fun createWithBorder(text: String, maxWidth: Int = 200): BorderContainerComponent = BorderContainerComponent.createStandard(TextBlockComponent(text, maxWidth))

        /**
         * 创建带边框的高对比度科幻风格文本块
         */
        fun createWithHighContrastBorder(text: String, maxWidth: Int = 200): BorderContainerComponent = BorderContainerComponent.createHighContrast(TextBlockComponent(text, maxWidth, textColor = 0xFFFFFFFF.toInt()))

        /**
         * 创建带边框的紧凑型科幻风格文本块
         */
        fun createWithCompactBorder(text: String, maxWidth: Int = 200): BorderContainerComponent = BorderContainerComponent.createCompact(TextBlockComponent(text, maxWidth, lineGap = 0, scale = 0.9f))
        // endregion
    }
}
