package com.gtocore.client.forge

import net.minecraft.client.Camera
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.GameRenderer
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraftforge.client.event.RenderLevelStageEvent

import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.DefaultVertexFormat
import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.Tesselator
import com.mojang.blaze3d.vertex.VertexFormat
import it.unimi.dsi.fastutil.objects.ObjectArrayList

object GTORenderManager {
    val tasks: ObjectArrayList<GTORenderType<*>> = ObjectArrayList<GTORenderType<*>>()
}

sealed class GTORenderType<T : GTORenderData>(val renderData: T) {
    open fun render(event: RenderLevelStageEvent) {
        self.renderData.startTick ?: run { self.renderData.startTick = event.renderTick }
    }
    var self: GTORenderType<T> = this
    class BLOCK_LINE(data: GTORenderData.BLOCK_LINE_DATA) : GTORenderType<GTORenderData.BLOCK_LINE_DATA>(data) {
        override fun render(event: RenderLevelStageEvent) {
            super.render(event)
            if (event.renderTick - self.renderData.startTick!! > self.renderData.durationTick) {
                self.renderData.willBeCalled = false
                self.renderData.willBeDelete = true
                return
            }
            if ((event.renderTick % self.renderData.flickerCycle) < self.renderData.flickerCycle / 2) return
            if (event.stage != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return
            with(PlayerRenderContext.create(event) ?: return) {
                if (self.renderData.level != player.level().dimension()) return

                val blockPos = self.renderData.pos
                val blockSideLength = 1f
                val halfBlockSideLength = blockSideLength / 2
                val colorRGBA = 0xFFFF0000.toInt() // 红色

                poseStack.pushPose()
                poseStack.translate(
                    blockPos.x.toDouble() - camera.position.x + halfBlockSideLength,
                    blockPos.y.toDouble() - camera.position.y + halfBlockSideLength,
                    blockPos.z.toDouble() - camera.position.z + halfBlockSideLength,
                )

                RenderSystem.disableDepthTest()
                RenderSystem.depthMask(false)
                RenderSystem.lineWidth(1f)
                RenderSystem.enableBlend()
                RenderSystem.defaultBlendFunc()
                RenderSystem.setShader(GameRenderer::getPositionColorShader)
                RenderSystem.disableCull()

                val tesselator = Tesselator.getInstance()
                val buffer = tesselator.builder
                val matrix = poseStack.last().pose()
                buffer.begin(VertexFormat.Mode.DEBUG_LINES, DefaultVertexFormat.POSITION_COLOR)

                // 1.构造顶点列表
                val vertices = listOf(
                    floatArrayOf(-halfBlockSideLength, halfBlockSideLength, halfBlockSideLength),
                    floatArrayOf(halfBlockSideLength, halfBlockSideLength, halfBlockSideLength),
                    floatArrayOf(halfBlockSideLength, halfBlockSideLength, -halfBlockSideLength),
                    floatArrayOf(-halfBlockSideLength, halfBlockSideLength, -halfBlockSideLength),
                    floatArrayOf(-halfBlockSideLength, -halfBlockSideLength, halfBlockSideLength),
                    floatArrayOf(halfBlockSideLength, -halfBlockSideLength, halfBlockSideLength),
                    floatArrayOf(halfBlockSideLength, -halfBlockSideLength, -halfBlockSideLength),
                    floatArrayOf(-halfBlockSideLength, -halfBlockSideLength, -halfBlockSideLength),
                )
                // 2.上面四条边相连
                for (i in 0..3) {
                    val start = vertices[i]
                    val end = vertices[(i + 1) % 4]
                    buffer.vertex(matrix, start[0], start[1], start[2])
                        .color(colorRGBA)
                        .endVertex()
                    buffer.vertex(matrix, end[0], end[1], end[2])
                        .color(colorRGBA)
                        .endVertex()
                }
                // 3.下面四条边相连
                for (i in 4..7) {
                    val start = vertices[i]
                    val end = vertices[(i + 1) % 4 + 4]
                    buffer.vertex(matrix, start[0], start[1], start[2])
                        .color(colorRGBA)
                        .endVertex()
                    buffer.vertex(matrix, end[0], end[1], end[2])
                        .color(colorRGBA)
                        .endVertex()
                }
                // 4.上下四条边相连
                for (i in 0..3) {
                    val start = vertices[i]
                    val end = vertices[i + 4]
                    buffer.vertex(matrix, start[0], start[1], start[2])
                        .color(colorRGBA)
                        .endVertex()
                    buffer.vertex(matrix, end[0], end[1], end[2])
                        .color(colorRGBA)
                        .endVertex()
                }

                tesselator.end()
                RenderSystem.enableDepthTest()
                RenderSystem.depthMask(true)
                RenderSystem.disableBlend()
                RenderSystem.enableCull()

                poseStack.popPose()
            }
        }
    }
}
sealed class GTORenderData(val description: String) {
    var willBeDelete = false
    var willBeCalled = true
    var startTick: Int? = null
    var endTick: Int? = null

    /*
     * pos: 渲染位置
     * level: 渲染所在的维度
     * durationTick: 渲染Tick数，超过这个时间后将会被删除
     * flickerCycle: 闪烁周期，每这个周期(亮-暗)一次
     */
    data class BLOCK_LINE_DATA(val pos: BlockPos, val level: ResourceKey<Level>, val durationTick: Int, val flickerCycle: Int = 2000000000) : GTORenderData("渲染方块线框用于提示")
}

class PlayerRenderContext(val player: Player, val poseStack: PoseStack, val camera: Camera, val instance: Minecraft = Minecraft.getInstance()) {
    companion object {
        fun create(event: RenderLevelStageEvent): PlayerRenderContext? {
            val instance = Minecraft.getInstance()
            val player = instance.player ?: return null
            return PlayerRenderContext(player, event.poseStack, event.camera)
        }
    }
}
