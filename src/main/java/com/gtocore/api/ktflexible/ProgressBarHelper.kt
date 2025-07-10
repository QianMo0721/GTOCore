package com.gtocore.api.ktflexible

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics

import com.lowdragmc.lowdraglib.gui.util.DrawerHelper

import kotlin.math.roundToInt

/**
 * 进度条颜色样式
 */
sealed class ProgressBarColorStyle {
    /**
     * 单一颜色
     */
    data class Solid(val color: Int) : ProgressBarColorStyle()

    /**
     * 基于百分比的分段颜色
     */
    data class Segmented(val segments: List<Pair<Float, Int>>) : ProgressBarColorStyle() {
        init {
            require(segments.isNotEmpty()) { "Segments cannot be empty" }
            require(segments.all { it.first in 0f..1f }) { "All thresholds must be in range [0, 1]" }
            require(segments.sortedBy { it.first } == segments) { "Segments must be sorted by threshold" }
        }
    }

    /**
     * 渐变颜色
     */
    data class Gradient(val startColor: Int, val endColor: Int) : ProgressBarColorStyle()

    /**
     * 多色渐变
     */
    data class MultiGradient(val colors: List<Pair<Float, Int>>) : ProgressBarColorStyle() {
        init {
            require(colors.size >= 2) { "At least 2 colors required for gradient" }
            require(colors.all { it.first in 0f..1f }) { "All positions must be in range [0, 1]" }
            require(colors.sortedBy { it.first } == colors) { "Colors must be sorted by position" }
        }
    }

    /**
     * 彩虹混合色样式 - 整个进度条呈现混合的彩虹色效果
     */
    data class RainbowMix(val baseColors: List<Int> = DEFAULT_RAINBOW_COLORS, val mixIntensity: Float = 1.0f, val scrollSpeed: Float = 0.1f, val waveLength: Float = 1.0f) : ProgressBarColorStyle() {
        init {
            require(baseColors.isNotEmpty()) { "Base colors cannot be empty" }
            require(mixIntensity in 0f..1f) { "Mix intensity must be in range [0, 1]" }
            require(waveLength > 0f) { "Wave length must be positive" }
        }

        companion object {
            val DEFAULT_RAINBOW_COLORS = listOf(
                0xFFFF0000.toInt(),
                0xFFFF8000.toInt(),
                0xFFFFFF00.toInt(), // 黄
                0xFF80FF00.toInt(), // 黄绿
                0xFF00FF00.toInt(), // 绿
                0xFF00FF80.toInt(), // 青绿
                0xFF00FFFF.toInt(), // 青
                0xFF0080FF.toInt(), // 天蓝
                0xFF0000FF.toInt(), // 蓝
                0xFF8000FF.toInt(), // 蓝紫
                0xFFFF00FF.toInt(), // 紫红
                0xFFFF0080.toInt(), // 粉红
            )
        }
    }

    companion object {
        /**
         * 预设的绿-黄-红渐变样式（类似健康值）
         */
        val HEALTH_GRADIENT = MultiGradient(
            listOf(
                0f to 0xFF00AA00.toInt(), // 绿色
                0.5f to 0xFFFFAA00.toInt(), // 黄色
                1f to 0xFFCC0000.toInt(), // 红色
            ),
        )

        /**
         * 预设的蓝-青-白渐变样式（类似魔法值）
         */
        val MANA_GRADIENT = MultiGradient(
            listOf(
                0f to 0xFF0066CC.toInt(), // 深蓝
                0.5f to 0xFF00CCCC.toInt(), // 青色
                1f to 0xFFCCFFFF.toInt(), // 浅蓝白
            ),
        )

        /**
         * 预设的经验值样式
         */
        val EXPERIENCE_GRADIENT = Gradient(0xFF7FFF00.toInt(), 0xFFFFD700.toInt())

        /**
         * 默认绿色样式
         */
        val DEFAULT_GREEN = Solid(0xFF00FF00.toInt())

        /**
         * 默认红色样式
         */
        val DEFAULT_RED = Solid(0xFFFF0000.toInt())

        /**
         * 彩虹混合样式
         */
        val RAINBOW_MIX = RainbowMix()

        /**
         * 柔和彩虹混合样式
         */
        val SOFT_RAINBOW_MIX = RainbowMix(
            mixIntensity = 0.6f,
            waveLength = 1.5f,
        )

        /**
         * 动态彩虹混合样式
         */
        val DYNAMIC_RAINBOW_MIX = RainbowMix(
            scrollSpeed = 0.3f,
            waveLength = 0.8f,
        )
    }
}

/**
 * 进度条绘制Helper
 */
object ProgressBarHelper {

    /**
     * 绘制带文本的进度条 - 主要方法
     *
     * @param graphics 图形上下文（已经过变换，Helper内从0,0开始绘制）
     * @param progress 进度百分比 (0-100)
     * @param totalWidth 总宽度
     * @param totalHeight 总高度
     * @param text 显示的文本
     * @param borderWidth 边框宽度
     * @param progressColorStyle 进度条颜色样式
     * @param backgroundColor 背景颜色
     * @param borderColor 边框颜色
     * @param textColor 文本颜色
     */
    fun drawProgressBarWithText(graphics: GuiGraphics, progress: Int, totalWidth: Int, totalHeight: Int, text: String, borderWidth: Int = 1, progressColorStyle: ProgressBarColorStyle = ProgressBarColorStyle.DEFAULT_GREEN, backgroundColor: Int = 0xFF404040.toInt(), borderColor: Int = 0xFF000000.toInt(), textColor: Int = 0xFFFFFFFF.toInt()) {
        val safeProgress = progress.coerceIn(0, 100)
        val progressFloat = safeProgress / 100f

        // 计算内部区域
        val innerX = borderWidth
        val innerY = borderWidth
        val innerWidth = totalWidth - borderWidth * 2
        val innerHeight = totalHeight - borderWidth * 2

        // 绘制边框
        if (borderWidth > 0) {
            DrawerHelper.drawBorder(graphics, innerX, innerY, innerWidth, innerHeight, borderColor, borderWidth)
        }

        // 绘制背景
        if (innerWidth > 0 && innerHeight > 0) {
            DrawerHelper.drawSolidRect(graphics, innerX, innerY, innerWidth, innerHeight, backgroundColor)
        }

        // 计算进度条宽度
        val progressWidth = (innerWidth * progressFloat).roundToInt()

        // 绘制进度条 - 对于彩虹混合使用特殊处理
        if (progressWidth > 0 && innerHeight > 0) {
            when (progressColorStyle) {
                is ProgressBarColorStyle.RainbowMix -> {
                    drawRainbowMixProgressBar(graphics, innerX, innerY, progressWidth, innerHeight, progressFloat, progressColorStyle)
                }
                else -> {
                    val color = when (progressColorStyle) {
                        is ProgressBarColorStyle.Solid -> progressColorStyle.color
                        is ProgressBarColorStyle.Segmented -> getSegmentedColor(progressFloat, progressColorStyle.segments)
                        is ProgressBarColorStyle.Gradient -> interpolateColor(progressColorStyle.startColor, progressColorStyle.endColor, progressFloat)
                        is ProgressBarColorStyle.MultiGradient -> getMultiGradientColor(progressFloat, progressColorStyle.colors)
                        else -> 0xFF00FF00.toInt()
                    }
                    DrawerHelper.drawSolidRect(graphics, innerX, innerY, progressWidth, innerHeight, color)
                }
            }
        }

        // 绘制文本
        if (text.isNotEmpty()) {
            val font = Minecraft.getInstance().font
            val textWidth = font.width(text)
            val textHeight = font.lineHeight

            // 手动计算居中位置
            val textX = (totalWidth - textWidth) / 2f
            val textY = (totalHeight - textHeight) / 2f + 1f

            DrawerHelper.drawText(
                graphics,
                text,
                textX,
                textY,
                1f,
                textColor,
                false, // 不使用DrawerHelper的居中功能，我们手动计算
            )
        }
    }

    /**
     * 绘制带百分比文本的进度条 - 便捷重载方法
     */
    fun drawProgressBarWithPercentage(graphics: GuiGraphics, progress: Int, totalWidth: Int, totalHeight: Int, borderWidth: Int = 1, progressColorStyle: ProgressBarColorStyle = ProgressBarColorStyle.DEFAULT_GREEN, backgroundColor: Int = 0xFF404040.toInt(), borderColor: Int = 0xFF000000.toInt(), textColor: Int = 0xFFFFFFFF.toInt()) {
        val safeProgress = progress.coerceIn(0, 100)
        val percentageText = "$safeProgress%"

        drawProgressBarWithText(
            graphics = graphics,
            progress = progress,
            totalWidth = totalWidth,
            totalHeight = totalHeight,
            text = percentageText,
            borderWidth = borderWidth,
            progressColorStyle = progressColorStyle,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            textColor = textColor,
        )
    }

    /**
     * 绘制彩虹混合进度条 - 每个像素都有不同的彩虹色
     */
    private fun drawRainbowMixProgressBar(graphics: GuiGraphics, x: Int, y: Int, width: Int, height: Int, progress: Float, rainbowMix: ProgressBarColorStyle.RainbowMix) {
        val baseColors = rainbowMix.baseColors
        val mixIntensity = rainbowMix.mixIntensity
        val scrollSpeed = rainbowMix.scrollSpeed
        val waveLength = rainbowMix.waveLength

        // 获取当前时间用于动态效果
        val currentTime = System.currentTimeMillis()

        // 逐像素绘制彩虹效果
        for (pixelX in 0 until width) {
            val pixelProgress = pixelX.toFloat() / width.toFloat()

            // 计算当前像素的彩虹位置
            val rainbowPosition = (pixelProgress * waveLength + currentTime * scrollSpeed * 0.001f) % 1f
            val colorIndex = rainbowPosition * baseColors.size

            // 获取相邻的两个颜色
            val colorIndexFloor = colorIndex.toInt() % baseColors.size
            val colorIndexCeil = (colorIndexFloor + 1) % baseColors.size
            val colorFraction = colorIndex - colorIndex.toInt()

            val startColor = baseColors[colorIndexFloor]
            val endColor = baseColors[colorIndexCeil]

            // 混合颜色
            val mixedColor = interpolateColor(startColor, endColor, colorFraction * mixIntensity)

            // 绘制单个像素列
            graphics.fill(x + pixelX, y, x + pixelX + 1, y + height, mixedColor)
        }
    }

    /**
     * 获取分段颜色
     */
    private fun getSegmentedColor(progress: Float, segments: List<Pair<Float, Int>>): Int {
        for (i in segments.indices.reversed()) {
            if (progress >= segments[i].first) {
                return segments[i].second
            }
        }
        return segments.first().second
    }

    /**
     * 获取多色渐变颜色
     */
    private fun getMultiGradientColor(progress: Float, colors: List<Pair<Float, Int>>): Int {
        if (progress <= colors.first().first) return colors.first().second
        if (progress >= colors.last().first) return colors.last().second

        for (i in 0 until colors.size - 1) {
            val current = colors[i]
            val next = colors[i + 1]
            if (progress >= current.first && progress <= next.first) {
                val t = (progress - current.first) / (next.first - current.first)
                return interpolateColor(current.second, next.second, t)
            }
        }

        return colors.last().second
    }

    /**
     * 彩虹混合色计算
     */
    private fun getRainbowMixColor(progress: Float, rainbowMix: ProgressBarColorStyle.RainbowMix): Int {
        val baseColors = rainbowMix.baseColors
        val mixIntensity = rainbowMix.mixIntensity
        val scrollSpeed = rainbowMix.scrollSpeed
        val waveLength = rainbowMix.waveLength

        // 计算滚动偏移
        val scrollOffset = (progress * waveLength + System.currentTimeMillis() * scrollSpeed).toInt() % (baseColors.size * waveLength.toInt())

        // 计算颜色混合
        val colorIndex = scrollOffset / waveLength.toInt()
        val colorFraction = (scrollOffset % waveLength.toInt()) / waveLength

        val startColor = baseColors[colorIndex % baseColors.size]
        val endColor = baseColors[(colorIndex + 1) % baseColors.size]

        return interpolateColor(startColor, endColor, colorFraction * mixIntensity)
    }

    /**
     * 颜色插值
     */
    private fun interpolateColor(startColor: Int, endColor: Int, factor: Float): Int {
        val safeFactor = factor.coerceIn(0f, 1f)

        val startA = (startColor shr 24) and 0xFF
        val startR = (startColor shr 16) and 0xFF
        val startG = (startColor shr 8) and 0xFF
        val startB = startColor and 0xFF

        val endA = (endColor shr 24) and 0xFF
        val endR = (endColor shr 16) and 0xFF
        val endG = (endColor shr 8) and 0xFF
        val endB = endColor and 0xFF

        val a = (startA + (endA - startA) * safeFactor).roundToInt()
        val r = (startR + (endR - startR) * safeFactor).roundToInt()
        val g = (startG + (endG - startG) * safeFactor).roundToInt()
        val b = (startB + (endB - startB) * safeFactor).roundToInt()

        return (a shl 24) or (r shl 16) or (g shl 8) or b
    }
}
