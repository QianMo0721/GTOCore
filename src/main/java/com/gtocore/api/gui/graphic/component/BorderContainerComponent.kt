package com.gtocore.api.gui.graphic.component

import com.gtocore.api.gui.graphic.TooltipComponent

import net.minecraft.client.gui.GuiGraphics

import com.lowdragmc.lowdraglib.gui.util.DrawerHelper

/**
 * 边框容器组件 - 可嵌套一个内部组件并为其提供科幻风格边框
 *
 * @param innerComponent 内部嵌套的组件
 * @param backgroundColor 背景颜色
 * @param borderColor 边框颜色
 * @param borderWidth 边框宽度
 * @param padding 内边距
 * @param enableSciFiAccents 是否启用科幻装饰线条
 */
class BorderContainerComponent(
    private val innerComponent: TooltipComponent,
    private val backgroundColor: Int = 0xF0101824.toInt(), // 更深更不透明的背景
    private val borderColor: Int = 0xFF00D4FF.toInt(), // 更亮的科幻蓝色
    private val borderWidth: Int = 1,
    private val padding: Int = 2,
    private val enableSciFiAccents: Boolean = true,
) : TooltipComponent {
    // region 属性与缓存
    // 缓存计算结果避免重复计算
    private val cachedSize: Pair<Int, Int> by lazy {
        val innerWidth = innerComponent.getWidth()
        val innerHeight = innerComponent.getHeight()

        // 返回包含边框和内边距的总尺寸
        val totalWidth = innerWidth + (borderWidth + padding) * 2
        val totalHeight = innerHeight + (borderWidth + padding) * 2

        totalWidth to totalHeight
    }
    // endregion

    // region 主渲染流程
    override fun render(graphics: GuiGraphics) {
        val totalWidth = getWidth()
        val totalHeight = getHeight()

        // 绘制科幻风格的背景和边框
        renderSciFiBackground(graphics, totalWidth, totalHeight)

        // 绘制内部组件（添加偏移量）
        graphics.pose().pushPose()
        graphics.pose().translate(
            (borderWidth + padding).toFloat(),
            (borderWidth + padding).toFloat(),
            0f,
        )

        innerComponent.render(graphics)

        graphics.pose().popPose()
    }
    // endregion

    // region 尺寸
    override fun getHeight(): Int = cachedSize.second

    override fun getWidth(): Int = cachedSize.first
    // endregion

    // region 私有绘制方法
    /**
     * 绘制科幻风格的背景和边框
     */
    private fun renderSciFiBackground(graphics: GuiGraphics, width: Int, height: Int) {
        // 绘制主背景
        DrawerHelper.drawSolidRect(graphics, 0, 0, width, height, backgroundColor)

        // 绘制内部边框 - 向内收缩1像素避免向外扩散
        DrawerHelper.drawBorder(graphics, 1, 1, width - 2, height - 2, borderColor, borderWidth)

        // 科幻装饰线条
        if (enableSciFiAccents) {
            drawSciFiAccents(graphics, width, height)
        }
    }

    /**
     * 绘制科幻装饰线条
     */
    private fun drawSciFiAccents(graphics: GuiGraphics, width: Int, height: Int) {
        val accentColor = 0xFF00FFD4.toInt() // 青绿色装饰

        // 角部切角装饰
        val cornerSize = 4
        val cornerThick = 1

        // 左上角
        DrawerHelper.drawSolidRect(graphics, 1, 1, cornerSize, cornerThick, accentColor)
        DrawerHelper.drawSolidRect(graphics, 1, 1, cornerThick, cornerSize, accentColor)

        // 右上角
        DrawerHelper.drawSolidRect(graphics, width - cornerSize - 1, 1, cornerSize, cornerThick, accentColor)
        DrawerHelper.drawSolidRect(graphics, width - cornerThick - 1, 1, cornerThick, cornerSize, accentColor)

        // 左下角
        DrawerHelper.drawSolidRect(graphics, 1, height - cornerThick - 1, cornerSize, cornerThick, accentColor)
        DrawerHelper.drawSolidRect(graphics, 1, height - cornerSize - 1, cornerThick, cornerSize, accentColor)

        // 右下角
        DrawerHelper.drawSolidRect(graphics, width - cornerSize - 1, height - cornerThick - 1, cornerSize, cornerThick, accentColor)
        DrawerHelper.drawSolidRect(graphics, width - cornerThick - 1, height - cornerSize - 1, cornerThick, cornerSize, accentColor)
    }
    // endregion

    // region 工具方法
    /**
     * 获取内部组件
     */
    fun getInnerComponent(): TooltipComponent = innerComponent
    // endregion

    companion object {
        // region 静态工厂
        /**
         * 创建标准科幻风格边框容器
         */
        fun createStandard(innerComponent: TooltipComponent): BorderContainerComponent = BorderContainerComponent(innerComponent)

        /**
         * 创建高对比度科幻风格边框容器
         */
        fun createHighContrast(innerComponent: TooltipComponent): BorderContainerComponent = BorderContainerComponent(
            innerComponent = innerComponent,
            backgroundColor = 0xFF000816.toInt(),
            borderColor = 0xFF00FFFF.toInt(),
            padding = 3,
        )

        /**
         * 创建紧凑型边框容器
         */
        fun createCompact(innerComponent: TooltipComponent): BorderContainerComponent = BorderContainerComponent(
            innerComponent = innerComponent,
            padding = 1,
            enableSciFiAccents = false,
        )

        /**
         * 创建无装饰边框容器
         */
        fun createSimple(innerComponent: TooltipComponent, backgroundColor: Int = 0x80000000.toInt(), borderColor: Int = 0xFFFFFFFF.toInt()): BorderContainerComponent = BorderContainerComponent(
            innerComponent = innerComponent,
            backgroundColor = backgroundColor,
            borderColor = borderColor,
            enableSciFiAccents = false,
        )
        // endregion
    }
}
