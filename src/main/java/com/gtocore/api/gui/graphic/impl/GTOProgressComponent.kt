package com.gtocore.api.gui.g.impl

import com.gtocore.api.gui.graphic.helper.GTOClientTooltipComponent
import com.gtocore.api.gui.graphic.helper.GTOComponent
import com.gtocore.api.gui.helper.ProgressBarHelper

import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.world.inventory.tooltip.TooltipComponent

class GTOProgressComponent(val percentage: Float, val text: String = "") :
    GTOComponent(height = 10, width = 150),
    TooltipComponent

class GTOProgressClientComponent(data: GTOProgressComponent) : GTOClientTooltipComponent<GTOProgressComponent>(data) {
    override fun getHeight() = data.height
    override fun getWidth(p0: Font) = data.width
    override fun renderImage(font: Font, x: Int, y: Int, guiGraphics: GuiGraphics) {
        guiGraphics.pose().pushPose()
        guiGraphics.pose().translate(x.toDouble() - 1, y.toDouble(), 400.0)
        ProgressBarHelper.drawProgressBarWithText(
            graphics = guiGraphics,
            progress = (data.percentage * 100).toInt(),
            totalWidth = data.width,
            totalHeight = data.height,
            text = data.text,
            borderWidth = 1,
            backgroundColor = 0xFF404040.toInt(),
            borderColor = 0xFF000000.toInt(),
        )
    }
}
infix fun Int.toPercentageWith(other: Int): Float = (this.toDouble() / other.toDouble()).toFloat()
infix fun Long.toPercentageWith(other: Long): Float = (this.toDouble() / other.toDouble()).toFloat()
