package com.gtocore.common.forge.render

import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.RenderTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

import appeng.items.storage.BasicStorageCell
import appeng.items.tools.powered.PortableCellItem
import appeng.me.cells.BasicCellHandler

import kotlin.math.nextUp

@OnlyIn(Dist.CLIENT)
object ToolTipsComponentAttacherForge {
    @SubscribeEvent
    @JvmStatic
    fun onItemTooltips(event: RenderTooltipEvent.Pre) {
        val components = event.components
        var tooltipWidth = 0
        var tooltipHeight = 0
        for (component in components) {
            val componentWidth = component.getWidth(event.font)
            if (componentWidth > tooltipWidth) {
                tooltipWidth = componentWidth
            }
            tooltipHeight += component.getHeight()
        }
        tooltipWidth += 8
        tooltipHeight += 8
        val componentManager = TooltipComponentManager()

        if (event.itemStack.item is BasicStorageCell || event.itemStack.item is PortableCellItem) {
            val handler = BasicCellHandler.INSTANCE.getCellInventory(event.itemStack, null)
            if (handler != null) {
                val usedBytes = handler.usedBytes
                val totalBytes = handler.totalBytes
                val progress = if (totalBytes > 0) usedBytes.toFloat() / totalBytes.toFloat() else 0f
                componentManager.components.add(ProgressBarComponent(progress, tooltipWidth - 4, event.font))
            }
        }

        componentManager.render(
            event.graphics,
            event.x + 8,
            event.y - 16,
            tooltipWidth,
            tooltipHeight,
            event.font,
        )
    }
}

// 工具提示组件接口
interface TooltipComponent {
    fun render(graphics: GuiGraphics)
    fun getHeight(): Int
    fun getWidth(): Int
}

// 进度条组件
class ProgressBarComponent(private val progress: Float, private val width: Int, private val font: Font, private val height: Int = 6) : TooltipComponent {

    override fun render(graphics: GuiGraphics) {
        val progressWidth = (width * progress).toInt()

        // 背景
        graphics.fill(0, 0, width, height, 0xFF404040.toInt())

        // 边框
        graphics.fill(-1, -1, width + 1, 0, 0xFF000000.toInt()) // 上边框
        graphics.fill(-1, height, width + 1, height + 1, 0xFF000000.toInt()) // 下边框
        graphics.fill(-1, -1, 0, height + 1, 0xFF000000.toInt()) // 左边框
        graphics.fill(width, -1, width + 1, height + 1, 0xFF000000.toInt()) // 右边框

        // 进度
        if (progressWidth > 0) {
            val progressColor = when {
                progress <= 0.5f -> 0xFF00AA00.toInt() // 绿色
                progress <= 0.75f -> {
                    // 0.5-0.75: 绿到黄的渐变
                    val t = (progress - 0.5f) / 0.25f // 归一化到 0-1
                    val red = (0x00 + (0xFF - 0x00) * t).toInt()
                    val green = 0xAA
                    val blue = 0x00
                    (0xFF000000.toInt() or (red shl 16) or (green shl 8) or blue)
                }
                else -> {
                    // 0.75-1: 黄到红的渐变
                    val t = (progress - 0.75f) / 0.25f // 归一化到 0-1
                    val red = 0xCC
                    val green = (0xAA * (1 - t)).toInt() // 从0xAA逐渐减少到0x00
                    val blue = 0x00
                    (0xFF000000.toInt() or (red shl 16) or (green shl 8) or blue)
                }
            }
            graphics.fill(0, 0, progressWidth, height, progressColor)
        }

        // 进度文本
        val progressText = "${(progress * 100).nextUp().toInt()}%"
        val textWidth = font.width(progressText)
        val textX = (width - textWidth) / 2
        val textY = (height - 8) / 2

        // 绘制文本阴影
        graphics.drawString(font, progressText, textX + 1, textY + 1, 0xFF000000.toInt())
        // 绘制文本
        graphics.drawString(font, progressText, textX, textY, 0xFFFFFFFF.toInt())
    }
    override fun getHeight(): Int = height + 2 // 加上边框高度
    override fun getWidth(): Int = width
}

// 组件管理器
class TooltipComponentManager(val components: MutableList<TooltipComponent> = mutableListOf(), private val spacing: Int = 2) {
    fun render(graphics: GuiGraphics, baseX: Int, baseY: Int, tooltipWidth: Int, tooltipHeight: Int, font: Font) {
        graphics.pose().pushPose()
        graphics.pose().translate(0f, 0f, 400f)

        var currentY = baseY + tooltipHeight + spacing

        components.forEach { component ->
            graphics.pose().pushPose()
            graphics.pose().translate((baseX + 2).toFloat(), currentY.toFloat(), 0f)
            component.render(graphics)
            graphics.pose().popPose()
            currentY += component.getHeight() + spacing
        }

        graphics.pose().popPose()
    }
}
