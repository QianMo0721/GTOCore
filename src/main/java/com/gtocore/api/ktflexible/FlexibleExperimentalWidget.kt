package com.gtocore.api.ktflexible

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.chat.Component
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

import com.gtolib.api.gui.ktflexible.LayoutBuilder
import com.gtolib.api.gui.ktflexible.text
import com.lowdragmc.lowdraglib.gui.util.DrawerHelper
import com.lowdragmc.lowdraglib.gui.widget.Widget

import java.util.function.IntSupplier

sealed class ColorStyle {
    abstract val color: Int
    class HEX(override val color: Int) : ColorStyle() {
        constructor(color: Long) : this(color.toInt())
    }
}
fun LayoutBuilder<*>.progressBar(currentSupplier: IntSupplier, totalSupplier: IntSupplier, width: Int = 100, height: Int = 20, border: Int = 2, padding: Int = 1, processColorStyle: ColorStyle = ColorStyle.HEX(0xFFFFFFFF), barColorStyle: ColorStyle = ColorStyle.HEX(0xFF00FF00), borderColorStyle: ColorStyle = ColorStyle.HEX(0xFF000000)) {
    val widget = object : Widget(0, 0, width, height) {
        private var lastCurrent = 1
        private var lastTotal = 1

        override fun writeInitialData(buffer: FriendlyByteBuf?) {
            super.writeInitialData(buffer)
            if (!isClientSideWidget) {
                buffer?.writeBoolean(true)
                lastCurrent = currentSupplier.asInt
                buffer?.writeInt(lastCurrent)
                lastTotal = totalSupplier.asInt
                buffer?.writeInt(lastTotal)
            } else {
                buffer?.writeBoolean(false)
            }
        }

        override fun readInitialData(buffer: FriendlyByteBuf?) {
            super.readInitialData(buffer)
            if (buffer?.readBoolean() == true) {
                lastCurrent = buffer.readInt()
                lastTotal = buffer.readInt()
            }
        }

        override fun detectAndSendChanges() {
            super.detectAndSendChanges()
            if (!isClientSideWidget) {
                val latestCurrent = currentSupplier.asInt
                if (latestCurrent != lastCurrent) {
                    lastCurrent = latestCurrent
                    writeUpdateInfo(-1, { it.writeInt(lastCurrent) })
                }
                val latestTotal = totalSupplier.asInt
                if (latestTotal != lastTotal) {
                    lastTotal = latestTotal
                    writeUpdateInfo(-2, { it.writeInt(lastTotal) })
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        override fun readUpdateInfo(id: Int, buffer: FriendlyByteBuf) {
            super.readUpdateInfo(id, buffer)
            if (id == -1) lastCurrent = buffer.readInt()
            if (id == -2) lastTotal = buffer.readInt()
        }

        @OnlyIn(Dist.CLIENT)
        override fun updateScreen() {
            super.updateScreen()
            if (isClientSideWidget) {
                val latestCurrent = currentSupplier.asInt
                if (latestCurrent != lastCurrent) {
                    lastCurrent = latestCurrent
                }
                val latestTotal = totalSupplier.asInt
                if (latestTotal != lastTotal) {
                    lastTotal = latestTotal
                }
            }
        }

        @OnlyIn(Dist.CLIENT)
        override fun drawInBackground(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
            super.drawInBackground(graphics, mouseX, mouseY, partialTicks)

            // 确保数据安全
            val safeTotal = maxOf(1, lastTotal)
            val safeCurrent = lastCurrent.coerceIn(0, safeTotal)
            // //////////////////////////////
            // ****** 边框和进度条 ******//
            // //////////////////////////////

            // 绘制边框
            DrawerHelper.drawBorder(graphics, positionX + border, positionY + border, width - border * 2, height - border * 2, borderColorStyle.color, border)

            // 计算进度
            val percentage: Float = safeCurrent.toFloat() / safeTotal.toFloat()

            // 计算进度条尺寸
            val startY = positionY + border + padding
            val startX = positionX + border + padding
            val barHeight = height - border * 2 - padding * 2
            val barWidth = ((width - border * 2 - padding * 2) * percentage).toInt()

            // 绘制进度条
            if (barWidth > 0) {
                DrawerHelper.drawSolidRect(graphics, startX, startY, barWidth, barHeight, barColorStyle.color)
            }
            // //////////////////////////////
            // ****** 文本 ******//
            // //////////////////////////////
            // 计算文本位置

            // 绘制文本
            val textWillShow = "$safeCurrent / $safeTotal"
            val font = Minecraft.getInstance().font
            val textWidth = font.width(textWillShow)
            val textHeight = font.lineHeight
            val textX = positionX + width / 2 - textWidth / 2
            val textY = positionY + height / 2 - textHeight / 2
            DrawerHelper.drawText(
                graphics,
                textWillShow,
                textX.toFloat(),
                textY.toFloat(),
                1f,
                processColorStyle.color,
                true,
            )
        }
    }
    widget(widget)
}
