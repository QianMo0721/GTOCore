package com.gtocore.common.machine.monitor;

import com.gtolib.GTOCore;

import net.minecraft.resources.ResourceLocation;

import static com.gtocore.data.lang.LangHandler.addCNEN;

public enum DisplayRegistry {

    OWNER("owner", "所有者", "Owner"),

    MANA_CURRENT("mana_current", "当前魔力", "Current Mana"),
    MANA_CURRENT_AS_POOL("mana_current_as_pool", "当前魔力（以池计算）", "Current Mana (as Pool)"),
    MANA_CHANGE("mana_change", "魔力变化", "Mana Change"),
    MANA_CHANGE_AS_POOL("mana_change_as_pool", "魔力变化（以池计算）", "Mana Change (as Pool)"),
    MANA_REMAINING_TIME("mana_remaining_time", "剩余耗尽时间", "Remaining Time Until Depletion"),
    MANA_PROGRESS_BAR("mana_progress_bar", "魔力进度条", "Mana Progress Bar"),

    TOTAL_ENERGY("total_energy", "总能量", "Total Energy"),
    ENERGY_TRANSFER_LIMIT("energy_transfer_limit", "能量传输上限", "Energy Transfer Limit"),
    ENERGY_STAT_TITLE("energy_stat_title", "能量统计标题", "Energy Statistics Title"),
    ENERGY_STAT_MINUTE("energy_stat_minute", "能量统计（上一分钟）", "Energy Statistics (Last Minute)"),
    ENERGY_STAT_HOUR("energy_stat_hour", "能量统计（上一小时）", "Energy Statistics (Last Hour)"),
    ENERGY_STAT_DAY("energy_stat_day", "能量统计（上一天）", "Energy Statistics (Last Day)"),
    ENERGY_STAT_NOW("energy_stat_now", "能量统计（当前）", "Energy Statistics (Now)"),
    ENERGY_STAT_BOUND_INFO("energy_stat_bound_info", "能源塔绑定信息", "Energy Tower Binding Information"),

    COMPUTATION_WORK("computation_work", "算力使用情况", "Computation Work Usage"),
    COMPUTATION_WORK_USED("computation_work_used", "算力使用量", "Computation Work Used"),
    COMPUTATION_WORK_UNAVAILABLE("computation_work_unavailable", "算力容器不可用信息", "Computation Work Container Unavailable Information")

    ;

    private final ResourceLocation id;
    private final String langCN;
    private final String langEN;

    DisplayRegistry(String id, String langCN, String langEN) {
        this.id = GTOCore.id(id);
        this.langCN = langCN;
        this.langEN = langEN;
    }

    public ResourceLocation id() {
        return id;
    }

    private String langKey() {
        return "gtocore.machine.monitor.display_key." + id.getNamespace() + "." + id.getPath();
    }

    // activates on runData task
    public static void registerLanguage() {
        for (DisplayRegistry displayRegistry : DisplayRegistry.values()) {
            addCNEN(displayRegistry.langKey(), displayRegistry.langCN, displayRegistry.langEN);
        }
    }
}
