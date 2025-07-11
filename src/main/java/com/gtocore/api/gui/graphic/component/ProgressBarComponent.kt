package com.gtocore.api.gui.graphic.component

import com.gtocore.api.gui.graphic.TooltipComponent
import com.gtocore.api.gui.graphic.helper.ProgressBarColorStyle
import com.gtocore.api.gui.graphic.helper.ProgressBarHelper

import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics

/**
 * 进度条组件
 * @param progressPercentage 进度百分比 (0.0~1.0)
 * @param width 进度条宽度
 * @param font 字体
 * @param height 进度条高度
 * @param showTextInBar 进度条内显示文本
 * @param flexibleForceWidth 是否根据文本自动扩展宽度
 * @param colorStyle 进度条颜色样式
 * @param textColor 文本颜色
 */
class ProgressBarComponent(private val progressPercentage: Float, private var width: Int, private val font: Font, private val height: Int = 10, private val showTextInBar: String = "", private val flexibleForceWidth: Boolean = false, private val colorStyle: ProgressBarColorStyle = ProgressBarColorStyle.HEALTH_GRADIENT, private val textColor: Int = 0xFFFFFFFF.toInt()) : TooltipComponent {
    init {
        if (flexibleForceWidth) {
            val textWidth = font.width(showTextInBar)
            if (width < textWidth + 20) {
                width = textWidth + 20
            }
        }
    }

    override fun render(graphics: GuiGraphics) {
        val safeProgress = progressPercentage.coerceIn(0f, 1f)
        val percentInt = (safeProgress * 100f).toInt()
        ProgressBarHelper.drawProgressBarWithText(
            graphics = graphics,
            progress = percentInt,
            totalWidth = width,
            totalHeight = height,
            text = showTextInBar,
            borderWidth = 1,
            progressColorStyle = colorStyle,
            backgroundColor = 0xFF404040.toInt(),
            borderColor = 0xFF000000.toInt(),
            textColor = textColor,
        )
    }

    override fun getHeight(): Int = height
    override fun getWidth(): Int = width
}
