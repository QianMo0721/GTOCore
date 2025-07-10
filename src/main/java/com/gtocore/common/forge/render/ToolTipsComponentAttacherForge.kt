package com.gtocore.common.forge.render

import com.gtocore.api.ktflexible.ProgressBarColorStyle
import com.gtocore.api.ktflexible.ProgressBarHelper

import net.minecraft.client.gui.Font
import net.minecraft.client.gui.GuiGraphics
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.event.RenderTooltipEvent

import appeng.items.storage.BasicStorageCell
import appeng.items.tools.powered.PortableCellItem
import appeng.me.cells.BasicCellHandler
import com.gtolib.mixin.ae2.BasicCellInventoryMixin

@OnlyIn(Dist.CLIENT)
object ToolTipsComponentAttacherForge {
//    @SubscribeEvent
//    @JvmStatic
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
        val itemStack = event.itemStack
        // //////////////////////////////
        // ****** AE硬盘 ******//
        // //////////////////////////////
        run {
            if (itemStack.item is BasicStorageCell || itemStack.item is PortableCellItem) {
                val handler = BasicCellHandler.INSTANCE.getCellInventory(itemStack, null) as BasicCellInventoryMixin?
                if (handler != null) {
                    val usedBytes = handler.usedBytes
                    val totalBytes = handler.totalBytes
                    if (totalBytes <= 0) return@run
                    val progress = (usedBytes.toFloat() / totalBytes.toFloat())
                    componentManager.components.add(
                        ProgressBarComponent(
                            progress,
                            tooltipWidth - 4,
                            event.font,
                            colorStyle = ProgressBarColorStyle.DEFAULT_GREEN,
                        ),
                    )
                }
            }
        }
        // //////////////////////////////
        // ****** 多步合成的物品 ******//
        // //////////////////////////////
        run {
            if (itemStack.hasTag()) {
                val step = itemStack.tag?.getInt("current_craft_step") ?: return@run
                val maxStep = itemStack.tag?.getInt("craft_step") ?: return@run
                if (maxStep == 0) return@run
                componentManager.components.add(
                    ProgressBarComponent(
                        step.toFloat() / maxStep.toFloat(),
                        tooltipWidth - 4,
                        event.font,
                        colorStyle = ProgressBarColorStyle.Gradient(0xFFFFFF00.toInt(), 0xFF00CC00.toInt()),
                    ),
                )
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
class ProgressBarComponent(private val progress: Float, private val width: Int, private val font: Font, private val height: Int = 6, private val colorStyle: ProgressBarColorStyle = ProgressBarColorStyle.HEALTH_GRADIENT) : TooltipComponent {

    override fun render(graphics: GuiGraphics) {
        val safeProgress = progress.coerceIn(0f, 1f)
        val progressPercentage = (safeProgress * 100f).toInt()
        val percentageText = "$progressPercentage%"

        ProgressBarHelper.drawProgressBarWithText(
            graphics = graphics,
            progress = progressPercentage,
            totalWidth = width,
            totalHeight = height,
            text = percentageText,
            borderWidth = 1,
            progressColorStyle = colorStyle,
            backgroundColor = 0xFF404040.toInt(),
            borderColor = 0xFF000000.toInt(),
            textColor = 0xFFFFFFFF.toInt(),
        )
    }

    override fun getHeight(): Int = height
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
