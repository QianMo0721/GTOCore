package com.gtocore.common.item.misc

import com.gregtechceu.gtceu.api.item.ComponentItem
import com.gtolib.GTOCore
import com.gtolib.utils.TagUtils
import com.gtolib.utils.register.ItemRegisterUtils.item
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullConsumer
enum class OrganType(val key: String, val cn: String, val slotCount: Int = 1) {
    Eye("eye", "眼睛", slotCount = 2),
    Spine("spine", "脊椎"),
    Lung("lung", "肺"),
    Liver("liver", "肝脏"),
    Heart("heart", "心脏"),
    Wing("wing", "翅膀"),
    LeftArm("left_arm", "左臂"),
    RightArm("right_arm", "右臂"),
    LeftLeg("left_leg", "左腿"),
    RightLeg("right_leg", "右腿"),
    Other("other", "其他"),
}

sealed class OrganItemBase(properties: Properties, val organType: OrganType) : ComponentItem(properties) {
    companion object {
        fun <T : OrganItemBase> registerOrganItem(id: String, organType: OrganType, resourceName: String, en: String, cn: String, itemFactory: (Properties, OrganType) -> T, onRegister: NonNullConsumer<T> = NonNullConsumer { }): ItemEntry<T> {
            val resourcePath = "item/organ/part/${organType.key}/$resourceName"
            val tag = TagUtils.createTag(GTOCore.id("organ_${organType.key}"))
            val itemBuilder = item(id, "器官 $cn", { p -> itemFactory(p.stacksTo(1).setNoRepair(), organType) })
                .lang("organ $en")
                .tag(tag)
                .model { ctx, prov -> prov.generated(ctx, GTOCore.id(resourcePath)) }
            itemBuilder.onRegister(onRegister)
            return itemBuilder.register()
        }
    }
    class OrganItem(properties: Properties, organType: OrganType) : OrganItemBase(properties, organType)
    class TierOrganItem(val tier: Int, properties: Properties, organType: OrganType) : OrganItemBase(properties, organType)
}
