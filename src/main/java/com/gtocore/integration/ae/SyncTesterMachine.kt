package com.gtocore.integration.ae

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity
import com.gregtechceu.gtceu.api.machine.MetaMachine
import com.gregtechceu.gtceu.api.machine.feature.IFancyUIMachine
import com.gtolib.GTOCore
import com.gtolib.api.annotation.Synced
import com.gtolib.api.capability.ISync
import com.gtolib.api.gui.ktflexible.button
import com.gtolib.api.gui.ktflexible.root
import com.gtolib.syncdata.SyncManagedFieldHolder
import com.lowdragmc.lowdraglib.gui.widget.Widget

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
 * 若要实现从客户端同步到服务端，需实现 `ISync` 接口。使用方法：
 * - 重写 `getSyncHolder()` 管理器
 * - 给字段添加 `@Synced` 注解，可添加监听器
 * - 无自动同步功能，需手动同步
 *
 * 手动同步方法：
 * - `syncToServer()`：立即向服务器发送同步数据包
 * - `syncToClient()`：立即向客户端发送同步数据包
 *
 * 需要额外的注册方法
 * - `registerSync()`：注册到同步管理器，在机器 `onLoad()` 时调用或在方块实体 `clearRemoved()` 时调用
 * - `unregisterSync()`：从同步管理器中移除，在机器 `onUnload()` 时调用或在方块实体 `setRemoved()` 时调用
 */
class SyncTesterMachine(holder: IMachineBlockEntity) :
    MetaMachine(holder),
    IFancyUIMachine,
    ISync {
    companion object {
        var SYNC_MANAGED_FIELD_HOLDER = SyncManagedFieldHolder(SyncTesterMachine::class.java)
    }

    override fun getSyncHolder() = SYNC_MANAGED_FIELD_HOLDER

    override fun isRemote() = super<MetaMachine>.isRemote

    @Synced(listener = "intListener")
    var testInt = 0

    @Synced(listener = "booleanListener")
    var testBoolean = false

    fun intListener(o: Int, n: Int) {
        GTOCore.LOGGER.info("intListener: $o -> $n")
    }

    fun booleanListener(o: Boolean, n: Boolean) {
        GTOCore.LOGGER.info("intListener: $o -> $n")
    }

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
                        syncToServer("testInt")
                    }
                })
                button(text = { "服务端Int-=1" }, onClick = { ck ->
                    if (!isRemote) {
                        testInt--
                        syncToClient("testInt")
                    }
                })
            }
            hBox(height = 20, { spacing = 4 }) {
                button(text = { "客户端Boolean取反" }, onClick = { ck ->
                    if (isRemote) {
                        testBoolean = !testBoolean
                        syncToServer("testBoolean")
                    }
                })
                button(text = { "服务端Boolean取反" }, onClick = { ck ->
                    if (!isRemote) {
                        testBoolean = !testBoolean
                        syncToClient("testBoolean")
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
