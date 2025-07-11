package com.gtocore.api.gui.graphic

import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics

// 组件管理器
class TooltipComponentManager(val components: MutableList<TooltipComponent> = mutableListOf(), private val spacing: Int = 2) {
    fun render(graphics: GuiGraphics, baseX: Int, baseY: Int, tooltipWidth: Int, tooltipHeight: Int, font: Font) {
        graphics.pose().pushPose()
        graphics.pose().translate(0f, 0f, 400f)

        var currentY = baseY + tooltipHeight + spacing

        components.forEach { component ->
            graphics.pose().pushPose()
            graphics.pose().translate((baseX).toFloat(), currentY.toFloat(), 0f)
            component.render(graphics)
            graphics.pose().popPose()
            currentY += component.getHeight() + spacing
        }

        graphics.pose().popPose()
    }
}

// 工具提示组件接口
interface TooltipComponent {
    fun render(graphics: GuiGraphics)
    fun getHeight(): Int
    fun getWidth(): Int
}
