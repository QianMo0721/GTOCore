package com.gtocore.api.ktflexible

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.network.chat.Component
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

import com.gtolib.api.gui.ktflexible.LayoutBuilder
import com.lowdragmc.lowdraglib.gui.util.DrawerHelper

import java.util.function.IntSupplier
import java.util.function.Supplier

fun LayoutBuilder<*>.progressBar(currentSupplier: IntSupplier, totalSupplier: IntSupplier, width: Int = 100, height: Int = 20, border: Int = 2, padding: Int = 1, progressColorStyle: ProgressBarColorStyle = ProgressBarColorStyle.DEFAULT_GREEN, backgroundColor: Int = 0xFF404040.toInt(), borderColor: Int = 0xFF000000.toInt(), textColor: Int = 0xFFFFFFFF.toInt(), showPercentage: Boolean = false) {
    val widget = object : SyncWidget(0, 0, width, height) {
        private val currentField = syncInt({ currentSupplier.asInt }, -1, 1)
        private val totalField = syncInt({ totalSupplier.asInt }, -2, 1)

        @OnlyIn(Dist.CLIENT)
        override fun drawInBackground(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
            super.drawInBackground(graphics, mouseX, mouseY, partialTicks)

            val safeTotal = maxOf(1, totalField.lastValue)
            val safeCurrent = currentField.lastValue.coerceIn(0, safeTotal)
            val percentage = ((safeCurrent.toFloat() / safeTotal.toFloat()) * 100).toInt()

            // 使用ProgressBarHelper绘制进度条
            graphics.pose().pushPose()
            graphics.pose().translate(positionX.toFloat(), positionY.toFloat(), 0f)

            ProgressBarHelper.drawProgressBarWithText(
                graphics = graphics,
                progress = percentage,
                totalWidth = width,
                totalHeight = height,
                text = if (showPercentage) "$percentage%" else "$safeCurrent / $safeTotal",
                borderWidth = border,
                progressColorStyle = progressColorStyle,
                backgroundColor = backgroundColor,
                borderColor = borderColor,
                textColor = textColor,
            )
            graphics.pose().popPose()
        }
    }
    widget(widget)
}

fun LayoutBuilder<*>.textBlock(textSupplier: Supplier<Component>, tab: Int = 0, maxWidth: Int = 40) {
    val widget = object : SyncWidget(0, 0, 100, 12) {
        private val textField = syncComponent({ textSupplier.get() }, -1, textSupplier.get())
        private val yPadding: Int = 1
        private val lineSpacing: Int = 1
        private var lastText: Component? = null

        override fun initWidget() {
            super.initWidget()
            updateSize()
        }

        // //////////////////////////////
        // ****** 切割换行，先以单词切割，如果还超过这一行，那就换成字母切割 ******//
        // //////////////////////////////
        private fun wrapText(text: String, font: net.minecraft.client.gui.Font, maxLineWidth: Int): List<String> {
            if (maxLineWidth <= 0) return listOf(text)

            return text.split(" ")
                .fold(mutableListOf<String>() to "") { (lines, currentLine), word ->
                    val testLine = when {
                        currentLine.isEmpty() -> word
                        else -> "$currentLine $word"
                    }
                    when {
                        font.width(testLine) <= maxLineWidth -> lines to testLine
                        currentLine.isNotEmpty() -> {
                            lines.add(currentLine)
                            lines to word
                        }
                        else -> {
                            // 单词太长，需要强制截断
                            val forceSplitLines = word.splitByWidth(font, maxLineWidth)
                            lines.addAll(forceSplitLines)
                            lines to ""
                        }
                    }
                }.let { (lines, lastLine) ->
                    when {
                        lastLine.isNotEmpty() -> lines.apply { add(lastLine) }
                        lines.isEmpty() -> listOf("")
                        else -> lines
                    }
                }
        }

        private fun String.splitByWidth(font: net.minecraft.client.gui.Font, maxWidth: Int): List<String> = generateSequence(this) { remaining ->
            when {
                remaining.isEmpty() -> null
                else -> {
                    val cutIndex = (1..remaining.length)
                        .takeWhile { font.width(remaining.substring(0, it)) <= maxWidth }
                        .lastOrNull() ?: 1
                    remaining.drop(cutIndex).takeIf { it.isNotEmpty() }
                }
            }
        }.map { remaining ->
            val cutIndex = (1..remaining.length)
                .takeWhile { font.width(remaining.substring(0, it)) <= maxWidth }
                .lastOrNull() ?: 1
            remaining.take(cutIndex)
        }.toList()

        // //////////////////////////////
        // ****** 变宽变高，先宽后高 ******//
        // //////////////////////////////
        private fun updateSize() {
            val font = Minecraft.getInstance().font
            val text = textField.lastValue.string
            val availableWidth = maxWidth - tab
            val wrappedLines = wrapText(text, font, availableWidth)

            val textHeight = font.lineHeight
            val totalHeight = when {
                wrappedLines.isEmpty() -> textHeight + 2 * yPadding
                else -> wrappedLines.size * textHeight + (wrappedLines.size - 1) * lineSpacing + 2 * yPadding
            }
            val actualWidth = wrappedLines.maxOfOrNull { font.width(it) } ?: 0

            setSize(actualWidth + tab, totalHeight)
        }

        @OnlyIn(Dist.CLIENT)
        override fun drawInBackground(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
            super.drawInBackground(graphics, mouseX, mouseY, partialTicks)

            val currentText = textField.lastValue
            if (currentText != lastText) {
                lastText = currentText
                updateSize()
            }

            val font = Minecraft.getInstance().font
            val text = currentText.string
            val availableWidth = maxWidth - tab
            val wrappedLines = wrapText(text, font, availableWidth)

            val textHeight = font.lineHeight

            wrappedLines.forEachIndexed { index, line ->
                val textY = positionY + yPadding + (index * (textHeight + lineSpacing))
                DrawerHelper.drawText(
                    graphics,
                    line,
                    (positionX + tab).toFloat(),
                    textY.toFloat(),
                    1f,
                    0xFFFFFFFF.toInt(),
                    true,
                )
            }
        }
    }
    widget(widget)
}
