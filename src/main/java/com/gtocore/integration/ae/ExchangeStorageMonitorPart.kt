package com.gtocore.integration.ae

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Font
import net.minecraft.client.renderer.LightTexture
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.player.Player
import net.minecraft.world.phys.Vec3
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

import appeng.api.networking.IGridNode
import appeng.api.networking.IGridNodeListener
import appeng.api.networking.ticking.IGridTickable
import appeng.api.networking.ticking.TickRateModulation
import appeng.api.networking.ticking.TickingRequest
import appeng.api.orientation.BlockOrientation
import appeng.api.parts.IPartItem
import appeng.api.stacks.AmountFormat
import appeng.api.util.AEColor
import appeng.client.render.BlockEntityRenderHelper
import appeng.hooks.ticking.TickHandler
import appeng.items.parts.PartModels
import appeng.parts.reporting.StorageMonitorPart
import com.gtolib.utils.RLUtils
import com.mojang.blaze3d.vertex.PoseStack

import kotlin.math.abs

class ExchangeStorageMonitorPart(partItem: IPartItem<*>) :
    StorageMonitorPart(partItem),
    IGridTickable {
    init {
        mainNode.addService(IGridTickable::class.java, this)
    }
    var lastValue: Long = 0L
    var laseEnum: WorkRoutine = WorkRoutine.MINUTE
    var lastTick: Long = 0L
    var lastReportedValue: Long = 0L
    var humanRate: String = "-"
    var rateColorState: RateColorState = RateColorState.NEUTRAL

    private val historyData = mutableMapOf<Long, Long>()
    private var lastSecondValue: Long = 0L
    private var lastMinuteValue: Long = 0L
    private var lastHourValue: Long = 0L
    private var lastSecondTick: Long = 0L
    private var lastMinuteTick: Long = 0L
    private var lastHourTick: Long = 0L

    // ////////////////////////////////
    // ****** RENDER  ******//
    // //////////////////////////////
    @OnlyIn(Dist.CLIENT)
    override fun renderDynamic(partialTicks: Float, poseStack: PoseStack, buffers: MultiBufferSource?, combinedLightIn: Int, combinedOverlayIn: Int) {
        if (isActive) {
            displayed?.let { displayed ->
                val textColor = color.contrastTextColor

                poseStack.pushPose()
                val orientation = BlockOrientation.get(side, spin.toInt())

                poseStack.translate(0.5, 0.5, 0.5)
                BlockEntityRenderHelper.rotateToFace(poseStack, orientation)
                poseStack.translate(0.0, 0.08, 0.5)

                BlockEntityRenderHelper.renderItem2d(
                    poseStack,
                    buffers,
                    displayed,
                    0.35f,
                    LightTexture.FULL_BRIGHT,
                    level,
                )

                val renderedStackSize =
                    if (amount == 0L && canCraft()) "Craft" else displayed.formatAmount(amount, AmountFormat.SLOT)

                val fr = Minecraft.getInstance().font
                val width = fr.width(renderedStackSize)
                val height = fr.lineHeight.toFloat()
                poseStack.pushPose()
                poseStack.translate(0.0f, -0.22f, 0.02f)
                poseStack.scale(1.0f / 62.0f, -1.0f / 62.0f, 1.0f / 62.0f)
                poseStack.scale(0.5f, 0.5f, 0f)
                poseStack.translate(0f, -5f, 0f)
                poseStack.translate(-0.5f * width, 0.0f, 0.5f)
                fr.drawInBatch(
                    renderedStackSize, 0f, 0f, textColor, false, poseStack.last().pose(), buffers!!,
                    Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT,
                )
                poseStack.translate(0.5f * width, 0.0f, 0.5f)

                val width1 = fr.width(humanRate)
                poseStack.translate(0.0f, height, 0.02f)
                poseStack.translate(-0.5f * width1, 0.0f, 0f)

                val rateTextColor: Int = when (rateColorState) {
                    RateColorState.POSITIVE -> AEColor.GREEN.mediumVariant
                    RateColorState.NEGATIVE -> AEColor.RED.mediumVariant
                    RateColorState.NEUTRAL -> textColor
                }

                fr.drawInBatch(
                    humanRate, 0f, 0f, rateTextColor, false, poseStack.last().pose(), buffers,
                    Font.DisplayMode.NORMAL, 0, LightTexture.FULL_BRIGHT,
                )
                poseStack.translate(0.5f * width1, 0.0f, 0.5f)

                poseStack.popPose()
                poseStack.popPose()
            }
        }
    }

    override fun requireDynamicRender(): Boolean = true

    // ////////////////////////////////
    // ****** 初始化 操作 ******//
    // //////////////////////////////
    fun resetState() {
        lastValue = 0L
        laseEnum = WorkRoutine.MINUTE
        lastTick = 0L
        historyData.clear()
        lastSecondValue = 0L
        lastMinuteValue = 0L
        lastHourValue = 0L
        lastSecondTick = 0L
        lastMinuteTick = 0L
        lastHourTick = 0L
    }
    override fun onPartActivate(player: Player?, hand: InteractionHand?, pos: Vec3?): Boolean {
        if (!isClientSide) {
            laseEnum = laseEnum.next()
            mainNode.apply { grid?.tickManager?.alertDevice(node) }
            host.markForSave()
        }
        return super.onPartActivate(player, hand, pos)
    }

    override fun configureWatchers() {
        if (displayed != null) {
            updateState(TickHandler.instance().currentTick)
            mainNode.apply { grid?.tickManager?.wakeDevice(node) }
        } else {
            mainNode.apply { grid?.tickManager?.sleepDevice(node) }
        }
        super.configureWatchers()
    }

    // ////////////////////////////////
    // ****** 数据同步 ******//
    // //////////////////////////////
    override fun readFromNBT(data: CompoundTag?) {
        super.readFromNBT(data)
        lastValue = data?.getLong("lastValue") ?: 0L
        laseEnum = WorkRoutine.fromId(data?.getInt("laseEnum") ?: 0) ?: WorkRoutine.MINUTE
        lastTick = data?.getLong("lastTick") ?: 0L
        humanRate = data?.getString("humanRate") ?: "-"
    }

    override fun writeToNBT(data: CompoundTag?) {
        super.writeToNBT(data)
        data?.putLong("lastValue", lastValue)
        data?.putInt("laseEnum", laseEnum.id)
        data?.putLong("lastTick", lastTick)
        data?.putString("humanRate", humanRate)
    }

    override fun writeToStream(data: FriendlyByteBuf?) {
        super.writeToStream(data)
        data?.writeLong(lastValue)
        data?.writeInt(laseEnum.id)
        data?.writeLong(lastTick)
        data?.writeUtf(humanRate)
        data?.writeUtf(rateColorState.name)
    }

    override fun readFromStream(data: FriendlyByteBuf?): Boolean {
        val stream = super.readFromStream(data)
        lastValue = data?.readLong() ?: 0L
        laseEnum = WorkRoutine.fromId(data?.readInt() ?: 0) ?: WorkRoutine.MINUTE
        lastTick = data?.readLong() ?: 0L
        humanRate = data?.readUtf() ?: "-"
        rateColorState = data?.readUtf().let { RateColorState.fromString(it ?: "") }
        return stream
    }
    // ////////////////////////////////
    // ****** DEFINITION ******//
    // //////////////////////////////

    enum class WorkRoutine(val id: Int) {
        SECOND(0),
        MINUTE(1),
        HOUR(2),
        ;

        fun next(): WorkRoutine = when (this) {
            SECOND -> MINUTE
            MINUTE -> HOUR
            HOUR -> SECOND
        }
        companion object {
            fun fromId(id: Int): WorkRoutine? = entries.find { it.id == id }
        }
    }
    override fun onMainNodeStateChanged(reason: IGridNodeListener.State?) {
        mainNode?.let { it.grid?.tickManager?.wakeDevice(it.node) }
        super.onMainNodeStateChanged(reason)
    }

    // ////////////////////////////////
    // ****** tick ******//
    // //////////////////////////////

    fun updateState(tick: Long) {
        lastTick = tick
        lastValue = amount
        lastReportedValue = amount
    }

    override fun getTickingRequest(node: IGridNode): TickingRequest = TickingRequest(5, 100, !isActive || displayed == null, true)

    override fun tickingRequest(node: IGridNode?, ticksSinceLastCall: Int): TickRateModulation {
        if (mainNode.isActive.not() || displayed == null) {
            return TickRateModulation.SLEEP
        }

        val currentTick = TickHandler.instance().currentTick

        if (lastTick == -1L || lastTick == 0L) {
            initializeHistoryData(currentTick)
            updateState(currentTick)
            return TickRateModulation.URGENT
        }

        val hasChanged = amount != lastValue

        historyData[currentTick] = amount

        updateReferenceData(currentTick)
        calculateChangeRate(currentTick)

        if (currentTick % 100 == 0L) {
            cleanOldHistoryData(currentTick)
        }

        lastTick = currentTick
        lastValue = amount
        host.markForUpdate()

        return when {
            hasChanged -> TickRateModulation.URGENT
            laseEnum == WorkRoutine.SECOND -> {
                TickRateModulation.FASTER
            }
            laseEnum == WorkRoutine.MINUTE -> {
                TickRateModulation.SAME
            }
            laseEnum == WorkRoutine.HOUR -> {
                TickRateModulation.SLOWER
            }
            else -> TickRateModulation.SAME
        }
    }

    private fun initializeHistoryData(currentTick: Long) {
        historyData.clear()
        historyData[currentTick] = amount
        lastSecondValue = amount
        lastMinuteValue = amount
        lastHourValue = amount
        lastSecondTick = currentTick
        lastMinuteTick = currentTick
        lastHourTick = currentTick
    }
    // ////////////////////////////////
    // ****** 计算 ******//
    // //////////////////////////////

    private fun cleanOldHistoryData(currentTick: Long) {
        val oneHourAgo = currentTick - (20 * 60 * 60) - 200
        historyData.keys.removeIf { it < oneHourAgo }
    }

    private fun updateReferenceData(currentTick: Long) {
        val oneSecondAgo = currentTick - 20
        val oneMinuteAgo = currentTick - (20 * 60)
        val oneHourAgo = currentTick - (20 * 60 * 60)

        if (currentTick - lastSecondTick >= 20) {
            findClosestHistoryData(oneSecondAgo)?.let { (tick, value) ->
                lastSecondValue = value
                lastSecondTick = tick
            }
        }

        if (currentTick - lastMinuteTick >= 20 * 60) {
            findClosestHistoryData(oneMinuteAgo)?.let { (tick, value) ->
                lastMinuteValue = value
                lastMinuteTick = tick
            }
        }

        if (currentTick - lastHourTick >= 20 * 60 * 60) {
            findClosestHistoryData(oneHourAgo)?.let { (tick, value) ->
                lastHourValue = value
                lastHourTick = tick
            }
        }
    }

    private fun findClosestHistoryData(targetTick: Long): Pair<Long, Long>? = historyData.entries
        .minByOrNull { abs(it.key - targetTick) }
        ?.let { it.key to it.value }

    private fun calculateChangeRate(currentTick: Long) {
        val currentAmount = amount

        when (laseEnum) {
            WorkRoutine.SECOND -> {
                val timeDiff = currentTick - lastSecondTick
                if (timeDiff > 0) {
                    val valueDiff = currentAmount - lastSecondValue
                    if (valueDiff == 0L) {
                        humanRate = "－O－"
                        rateColorState = RateColorState.NEUTRAL
                    } else {
                        val ratePerSecond = valueDiff.toDouble() / (timeDiff / 20.0)
                        rateColorState = if (ratePerSecond > 0) RateColorState.POSITIVE else RateColorState.NEGATIVE
                        humanRate = "${displayed?.formatAmount(abs(ratePerSecond).toLong(), AmountFormat.SLOT)} /s"
                    }
                } else {
                    humanRate = "－O－"
                    rateColorState = RateColorState.NEUTRAL
                }
            }
            WorkRoutine.MINUTE -> {
                val timeDiff = currentTick - lastMinuteTick
                if (timeDiff > 0) {
                    val valueDiff = currentAmount - lastMinuteValue
                    if (valueDiff == 0L) {
                        humanRate = "－O－"
                        rateColorState = RateColorState.NEUTRAL
                    } else {
                        val ratePerMinute = valueDiff.toDouble() / (timeDiff / (20.0 * 60))
                        rateColorState = if (ratePerMinute > 0) RateColorState.POSITIVE else RateColorState.NEGATIVE
                        humanRate = "${displayed?.formatAmount(abs(ratePerMinute).toLong(), AmountFormat.SLOT)} /m"
                    }
                } else {
                    humanRate = "－O－"
                    rateColorState = RateColorState.NEUTRAL
                }
            }
            WorkRoutine.HOUR -> {
                val timeDiff = currentTick - lastHourTick
                if (timeDiff > 0) {
                    val valueDiff = currentAmount - lastHourValue
                    if (valueDiff == 0L) {
                        humanRate = "－O－"
                        rateColorState = RateColorState.NEUTRAL
                    } else {
                        val ratePerHour = valueDiff.toDouble() / (timeDiff / (20.0 * 60 * 60))
                        rateColorState = if (ratePerHour > 0) RateColorState.POSITIVE else RateColorState.NEGATIVE
                        humanRate = "${displayed?.formatAmount(abs(ratePerHour).toLong(), AmountFormat.SLOT)} /h"
                    }
                } else {
                    humanRate = "－O－"
                    rateColorState = RateColorState.NEUTRAL
                }
            }
        }
    }
    companion object {
        val positiveColor = AEColor.GREEN.mediumVariant
        val negativeColor = AEColor.RED.mediumVariant

        @PartModels
        @JvmStatic
        val MODEL_OFF: ResourceLocation = RLUtils.ae("part/storage_monitor_off")

        @PartModels
        @JvmStatic
        val MODEL_ON: ResourceLocation = RLUtils.ae("part/storage_monitor_on")

        @PartModels
        @JvmStatic
        val MODEL_LOCKED_OFF: ResourceLocation = RLUtils.ae("part/storage_monitor_locked_off")

        @PartModels
        @JvmStatic
        val MODEL_LOCKED_ON: ResourceLocation = RLUtils.ae("part/storage_monitor_locked_on")
    }

    enum class RateColorState {
        POSITIVE,
        NEGATIVE,
        NEUTRAL,
        ;

        companion object {
            fun fromString(name: String): RateColorState = entries.find { it.name.equals(name, ignoreCase = true) } ?: NEUTRAL
        }
    }
}
