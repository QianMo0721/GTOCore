package com.gto.gtocore.api.item.tool;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagUtil;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.item.tool.behavior.DisableShieldBehavior;
import com.gregtechceu.gtceu.common.item.tool.behavior.ToolModeSwitchBehavior;

public final class GTOToolType {

    public static final GTToolType VAJRA_HV = vajra(GTToolType.builder("hv_vajra").electric(GTValues.HV));
    public static final GTToolType VAJRA_EV = vajra(GTToolType.builder("ev_vajra").electric(GTValues.EV));
    public static final GTToolType VAJRA_IV = vajra(GTToolType.builder("iv_vajra").electric(GTValues.IV));

    private static GTToolType vajra(GTToolType.Builder builder) {
        return builder.idFormat("%s_vajra")
                .toolTag(TagUtil.createItemTag("tools/wrenches", false))
                .toolTag(TagUtil.createItemTag("tools/wrench", false))
                .toolTag(TagUtil.createItemTag("tools/wire_cutters", false))
                .toolTag(TagUtil.createItemTag("pickaxes", true))
                .toolTag(TagUtil.createItemTag("shovels", true))
                .toolTag(TagUtil.createItemTag("axes", true))
                .harvestTag(TagUtil.createBlockTag("mineable/pickaxe", true))
                .harvestTag(TagUtil.createBlockTag("mineable/shovel", true))
                .harvestTag(TagUtil.createBlockTag("mineable/axe", true))
                .harvestTag(TagUtil.createBlockTag("mineable/wrench", false))
                .toolStats(b -> b.crafting().blockBreaking().sneakBypassUse().attacking().attackDamage(10.0F).attackSpeed(2.0F).behaviors(DisableShieldBehavior.INSTANCE, ToolModeSwitchBehavior.INSTANCE))
                .toolClasses(GTToolType.WRENCH, GTToolType.WIRE_CUTTER, GTToolType.PICKAXE, GTToolType.SHEARS, GTToolType.AXE)
                .build();
    }
}
