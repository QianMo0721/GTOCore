package com.gtocore.integration.ae

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine
import com.gtolib.GTOCore
import com.gtolib.api.annotation.ClientSynced
import com.gtolib.api.capability.ISyncFromClient
import com.gtolib.api.gui.ktflexible.button
import com.gtolib.api.gui.ktflexible.root
import com.gtolib.syncdata.C2SManagedFieldHolder
import com.lowdragmc.lowdraglib.gui.widget.Widget
import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder

/**
 * 默认情况下，机器只能从服务器同步到客户端。使用方法：
 * - 重写 `getFieldHolder()` 管理器
 * - 给字段添加 `@DescSynced` 注解
 * - 默认会每隔一段时间自动同步
 *
 * 手动同步方法：
 * - `requestSync()`：将机器标记为需要同步，异步线程会在下次执行时进行同步，性能友好
 * - `syncNow()`：立即向客户端发送同步数据包，会阻塞服务器线程
 *
 * 若要实现从客户端同步到服务端，需实现 `ISyncFromClient` 接口。使用方法：
 * - 重写 `getSyncHolder()` 管理器
 * - 给字段添加 `@ClientSynced` 注解
 * - 无自动同步功能，需手动同步
 *
 * 手动同步方法：
 * - `sendSync()`：立即向服务器发送同步数据包
 *
 * 需要额外的注册方法
 * - `registerSync()`：注册到同步管理器，在机器 `onLoad()` 时调用或在方块实体 `clearRemoved()` 时调用
 * - `unregisterSync()`：从同步管理器中移除，在机器 `onUnload()` 时调用或在方块实体 `setRemoved()` 时调用
 */
class SyncTesterMachine(holder: IMachineBlockEntity) :
    MetaMachine(holder),
    IFancyUIMachine,
    ISyncFromClient {
    companion object {
        val MANAGED_FIELD_HOLDER = ManagedFieldHolder(SyncTesterMachine::class.java, MetaMachine.MANAGED_FIELD_HOLDER)
        var CLIENT_MANAGED_FIELD_HOLDER = C2SManagedFieldHolder(SyncTesterMachine::class.java)
    }

    override fun getFieldHolder() = MANAGED_FIELD_HOLDER

    override fun getSyncHolder() = CLIENT_MANAGED_FIELD_HOLDER

    override fun isRemote() = super<MetaMachine>.isRemote

    @ClientSynced
    @DescSynced
    var testInt = 0

    @ClientSynced
    @DescSynced
    var testBoolean = false

    override fun onLoad() {
        super.onLoad()
        registerSync()
    }

    override fun onUnload() {
        unregisterSync()
        super.onUnload()
    }

    override fun createUIWidget(): Widget? = root(176, 166) {
        vBox(width = availableWidth) {
            hBox(height = 20, { spacing = 4 }) {
                button(text = { "客户端Int+=1" }, onClick = { ck ->
                    if (isRemote) {
                        testInt++
                        sendSync()
                    }
                })
                button(text = { "服务端Int-=1" }, onClick = { ck ->
                    if (!isRemote) {
                        testInt--
                        syncNow()
                    }
                })
            }
            hBox(height = 20, { spacing = 4 }) {
                button(text = { "客户端Boolean取反" }, onClick = { ck ->
                    if (isRemote) {
                        testBoolean = !testBoolean
                        sendSync()
                    }
                })
                button(text = { "服务端Boolean取反" }, onClick = { ck ->
                    if (!isRemote) {
                        testBoolean = !testBoolean
                        syncNow()
                    }
                })
            }
            button(text = { "获取数据" }) {
                if (isRemote) {
                    GTOCore.LOGGER.info("客户端Int: $testInt")
                    GTOCore.LOGGER.info("客户端Boolean: $testBoolean")
                } else {
                    GTOCore.LOGGER.info("服务端Int: $testInt")
                    GTOCore.LOGGER.info("服务端Boolean: $testBoolean")
                }
            }
        }
    }
}
