package com.gtocore.common.machine.multiblock.part.ae

import com.gtocore.common.machine.multiblock.part.ae.MESortMachine.Companion.PAGE_WIDTH
import com.gtocore.common.machine.multiblock.part.ae.MESortMachine.SortType.DEFAULT
import com.gtocore.common.machine.multiblock.part.ae.MESortMachine.SortType.TAG

import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.network.chat.Component
import net.minecraft.tags.TagKey
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.items.IItemHandlerModifiable
import net.minecraftforge.items.ItemStackHandler

import appeng.api.networking.IManagedGridNode
import com.gregtechceu.gtceu.api.gui.fancy.FancyMachineUIWidget
import com.gregtechceu.gtceu.api.gui.fancy.IFancyUIProvider
import com.gregtechceu.gtceu.api.gui.fancy.TabsWidget
import com.gregtechceu.gtceu.api.gui.widget.PhantomSlotWidget
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine
import com.gregtechceu.gtceu.integration.ae2.machine.feature.IGridConnectedMachine
import com.gregtechceu.gtceu.integration.ae2.machine.trait.GridNodeHolder
import com.gtolib.api.gui.ktflexible.*
import com.lowdragmc.lowdraglib.gui.modular.ModularUI
import com.lowdragmc.lowdraglib.gui.texture.IGuiTexture
import com.lowdragmc.lowdraglib.gui.texture.ItemStackTexture
import com.lowdragmc.lowdraglib.gui.util.DrawerHelper
import com.lowdragmc.lowdraglib.gui.widget.WidgetGroup
import com.lowdragmc.lowdraglib.syncdata.IContentChangeAware
import com.lowdragmc.lowdraglib.syncdata.ITagSerializable
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder
import kotlinx.coroutines.Runnable

import java.util.function.Predicate
import java.util.function.Supplier

private const val i1 = PAGE_WIDTH

class MESortMachine :
    MetaMachine,
    IGridConnectedMachine,
    IFancyUIMachine {
    constructor(holder: IMachineBlockEntity) : super(holder)

    companion object {
        const val PAGE_WIDTH = 276
        const val PAGE_HEIGHT = 166
        val MANAGED_FIELD_HOLDER: ManagedFieldHolder =
            ManagedFieldHolder(MESortMachine::class.java, MetaMachine.MANAGED_FIELD_HOLDER)
    }

    override fun getFieldHolder() = MANAGED_FIELD_HOLDER

    // AE
    @Persisted
    var gridNodeHolder: GridNodeHolder = GridNodeHolder(this)

    @DescSynced
    var isinOnline: Boolean = false
    override fun isOnline(): Boolean = isinOnline

    override fun setOnline(online: Boolean): Unit = run { isinOnline = online }
    override fun getMainNode(): IManagedGridNode? = gridNodeHolder.mainNode

    // 内部存储
    enum class SortType {
        TAG,
        DEFAULT,
    }

    class MyPhantomSlotWidget : PhantomSlotWidget {
        constructor(
            itemHandler: IItemHandlerModifiable?,
            slotIndex: Int,
            xPosition: Int,
            yPosition: Int,
            validator: Predicate<ItemStack?>?,
        ) : super(itemHandler, slotIndex, xPosition, yPosition, validator)
    }

    abstract class Line :
        ITagSerializable<CompoundTag>,
        IContentChangeAware {
        var widgets: MutableList<MyPhantomSlotWidget> = mutableListOf()
    }

    @Persisted
    @DescSynced
    var tagLineList = LineContainer<TagLine> { TagLine(0) }

    class LineContainer<T : Line>(val factory: () -> T) :
        ITagSerializable<CompoundTag>,
        IContentChangeAware {
        var lists: MutableList<T> = mutableListOf()
        override fun serializeNBT(): CompoundTag? {
            val tag = CompoundTag()
            val listTag = ListTag()
            for (line in lists) {
                listTag.add(line.serializeNBT())
            }
            tag.put("lists", listTag)
            return tag
        }

        override fun deserializeNBT(nbt: CompoundTag?) {
            if (nbt != null) {
                val listTag = nbt.getList("lists", 10)
                for (i in 0 until listTag.size) {
                    lists.add(factory().apply { deserializeNBT(listTag.getCompound(i)) })
                }
            }
        }

        override fun setOnContentsChanged(onContentChanged: Runnable) {}

        override fun getOnContentsChanged() = Runnable {}
    }

    class TagLine :
        Line,
        ITagSerializable<CompoundTag>,
        IContentChangeAware {
        constructor(slotsNum: Int) {
            slots = slotsNum
        }

        fun getSortStacks(): List<ItemStack> = getItemStacks().filterNot { it.isEmpty }.sortedBy { it.count }
        fun getItemStacks(): List<ItemStack> = widgets.mapNotNull { it.item }
        fun getItemStackTagList(stack: ItemStack): Set<TagKey<Item>> = stack.tags.toList().toSet()
        fun refreshAllowTags() {
            if (getItemStacks().none { !it.isEmpty }) {
                allowTags = emptySet()
                return
            }

            allowTags = getItemStacks().asSequence()
                .filterNot { it.isEmpty }
                .map { getItemStackTagList(it) }
                .reduceOrNull { acc, tags -> acc intersect tags } ?: emptySet()
        }

        var slots: Int = 0
            set(value) {
                widgets.clear()
                for (i in 0 until value) {
                    widgets.add(
                        MyPhantomSlotWidget(ItemStackHandler(1), 0, 0, 0) { stack ->
                            val r = stack?.run {
                                if (stack.tags.toList()?.isEmpty() ?: true) return@run false
                                if (allowTags.isEmpty()) return@run true
                                return@run stack.tags.toList().any { tag -> allowTags.contains(tag) }
                            } ?: false
                            refreshAllowTags()
                            r
                        },
                    )
                }
                field = value
            }
        var allowTags: Set<TagKey<Item>> = setOf()
        override fun serializeNBT(): CompoundTag? {
            val root = CompoundTag()
            root.putInt("slots", slots)
            val stackList = ListTag()
            for (stack in getItemStacks()) {
                stackList.add(stack.serializeNBT())
            }
            root.put("stacks", stackList)
            println("MESortMachine: serializeNBT: slots=$slots, stacks=${stackList.size}")
            return root
        }

        override fun deserializeNBT(nbt: CompoundTag?) {
            if (nbt != null) {
                slots = nbt.getInt("slots")
                val stackList = nbt.getList("stacks", 10)
                for (i in 0 until stackList.size) {
                    widgets[i].item = ItemStack.of(stackList.getCompound(i))
                }
                refreshAllowTags()
            }
            println("MESortMachine: deserializeNBT: slots=$slots, stacks=${widgets.size}")
        }

        override fun setOnContentsChanged(onContentChanged: Runnable?) {}

        override fun getOnContentsChanged() = Runnable { }
    }

    // UI
    val fancyMachineUIWidget = MyFancyMachineUIWidget(this, PAGE_WIDTH, PAGE_HEIGHT)

    override fun attachSideTabs(sideTabs: TabsWidget?) {
        sideTabs?.apply {
            mainTab = this@MESortMachine
            attachSubTab(SubPage(DEFAULT))
            attachSubTab(SubPage(TAG))
        }
    }

    class MyFancyMachineUIWidget(mainPage: IFancyUIProvider, width: Int, height: Int) :
        FancyMachineUIWidget(
            mainPage,
            width,
            height,
        ) {
        fun openSetupUI(fancyUI: IFancyUIProvider?) {
            setupFancyUI(fancyUI)
        }
    }

    override fun createUIWidget(): WidgetGroup = vBox(width = PAGE_WIDTH) {}
    override fun createUI(entityPlayer: Player?): ModularUI? = ModularUI(PAGE_WIDTH, PAGE_HEIGHT, this, entityPlayer).widget(fancyMachineUIWidget)

    override fun isRemote(): Boolean = super<IFancyUIMachine>.isRemote
    inner class SubPage(val sortType: SortType) : IFancyUIProvider {
        override fun getTabIcon(): IGuiTexture? = ItemStackTexture(Items.IRON_INGOT)
        override fun getTitle(): Component? = Component.literal(sortType.name)
        override fun createMainPage(widget: FancyMachineUIWidget?) = root(PAGE_WIDTH, PAGE_HEIGHT) {
            vScroll(PAGE_WIDTH, PAGE_HEIGHT, spacing = 4) {
                when (sortType) {
                    TAG -> {
                        button(width = availableWidth, text = { "添加" }, onClick = {
                            tagLineList.lists.add(TagLine(13))
                            fancyMachineUIWidget.openSetupUI(this@SubPage)
                        })
                        tagLineList.lists.forEach { line ->
                            vBoxWithThreeColumn(
                                availableWidth,
                                spacing = 2,
                                between = 7,
                                drawInBackgroundInit =
                                { g, _, _, _, vBox -> DrawerHelper.drawSolidRect(g, vBox.positionX + 2, vBox.positionY, 2, vBox.sizeHeight, 0xFF66CCFF.toInt()) },
                            ) {
                                hBox(16, spacing = 2) {
                                    button(width = (this@vBoxWithThreeColumn.availableWidth - 4) / 3, text = { "上移" }, onClick = {
                                        moveItem(tagLineList.lists, line, -1)
                                        fancyMachineUIWidget.openSetupUI(this@SubPage)
                                    })
                                    button(width = (this@vBoxWithThreeColumn.availableWidth - 4) / 3, text = { "删除" }, onClick = {
                                        tagLineList.lists.remove(line)
                                        fancyMachineUIWidget.openSetupUI(this@SubPage)
                                    })
                                    button(width = (this@vBoxWithThreeColumn.availableWidth - 4) / 3, text = { "下移" }, onClick = {
                                        moveItem(tagLineList.lists, line, 1)
                                        fancyMachineUIWidget.openSetupUI(this@SubPage)
                                    })
                                }
                                vBox(availableWidth, spacing = 2) {
                                    line.allowTags.forEach { tag ->
                                        text(width = availableWidth, text = Supplier { Component.literal(tag.location.path) }, init = {})
                                    }
                                }
                                hBox(18) {
                                    line.widgets.forEach { widget ->
                                        widget(widget)
                                    }
                                }
                            }
                        }
                    }
                    DEFAULT -> {}
                }
            }
        }
    }
    private fun <T> moveItem(list: MutableList<T>, item: T, direction: Int) {
        val index = list.indexOf(item)
        val targetIndex = index + direction
        if (targetIndex in 0 until list.size) {
            val temp = list.removeAt(index)
            list.add(targetIndex, temp)
        }
    }
}

// tagLineList.lists.forEach { line ->
//    custom(object : VBox(PAGE_WIDTH, spacing = 2) {
//        @OnlyIn(Dist.CLIENT)
//        override fun drawInBackground(graphics: GuiGraphics, mouseX: Int, mouseY: Int, partialTicks: Float) {
//            super.drawInBackground(graphics, mouseX, mouseY, partialTicks)
//            DrawerHelper.drawSolidRect(graphics, positionX + 1, positionY, 2, sizeHeight, 0xFF66CCFF.toInt())
//        }
//    }) {
//        hBox(PAGE_WIDTH, spacing = 2) {
//            widget(Widget(0, 0, 4, 1))
//            val innerWidth = PAGE_WIDTH - 2 - 4 - 2 - 4
//            vBox(innerWidth, spacing = 2) {
//                hBox(16, spacing = 2) {
//                    button(width = (innerWidth - 4) / 3, text = { "上移" }, onClick = {
//                        val index = tagLineList.lists.indexOf(line)
//                        if (index > 0) {
//                            val temp = tagLineList.lists.removeAt(index)
//                            tagLineList.lists.add(index - 1, temp)
//                        }
//                        fancyMachineUIWidget.openSetupUI(this@SubPage)
//                    })
//                    button(width = (innerWidth - 4) / 3, text = { "删除" }, onClick = {
//                        tagLineList.lists.remove(line)
//                        fancyMachineUIWidget.openSetupUI(this@SubPage)
//                    })
//                    button(width = (innerWidth - 4) / 3, text = { "下移" }, onClick = {
//                        val index = tagLineList.lists.indexOf(line)
//                        if (index < tagLineList.lists.size - 1) {
//                            val temp = tagLineList.lists.removeAt(index)
//                            tagLineList.lists.add(index + 1, temp)
//                        }
//                        fancyMachineUIWidget.openSetupUI(this@SubPage)
//                    })
//                }
//                vBox(innerWidth, spacing = 2) {
//                    line.allowTags.forEach { tag ->
//                        text(width = innerWidth, text = Supplier { Component.literal(tag.location.path) }, init = {})
//                    }
//                }
//                hBox(innerWidth) {
//                    line.widgets.forEach { widget ->
//                        widget(widget)
//                    }
//                }
//            }
//            widget(Widget(0, 0, 4, 1))
//        }
//    }
// }
