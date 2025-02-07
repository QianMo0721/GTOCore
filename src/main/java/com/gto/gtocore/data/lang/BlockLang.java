package com.gto.gtocore.data.lang;

import com.gto.gtocore.api.GTOValues;

import com.gregtechceu.gtceu.api.GTValues;

import static com.gto.gtocore.data.lang.LangHandler.addENCN;

final class BlockLang {

    static void init() {
        for (int tier = GTValues.EV; tier <= GTValues.MAX; tier++) {
            addENCN("block.gtceu." + GTValues.VN[tier].toLowerCase() + "_buffer", "%s Buffer %s".formatted(GTValues.VLVH[tier], GTValues.VLVT[tier]), "%s缓存器 %s".formatted(GTOValues.VLVHCN[tier], GTValues.VLVT[tier]));
        }

        for (int tier = GTValues.LV; tier <= GTValues.IV; tier++) {
            addENCN("block.gtceu." + GTValues.VN[tier].toLowerCase() + "_dual_input_hatch", "%s Dual Input Hatch".formatted(GTValues.VNF[tier]), "%s输入总成".formatted(GTOValues.VNFR[tier]));
            addENCN("block.gtceu." + GTValues.VN[tier].toLowerCase() + "_dual_output_hatch", "%s Dual Output Hatch".formatted(GTValues.VNF[tier]), "%s输出总成".formatted(GTOValues.VNFR[tier]));
        }

        for (int tier = GTValues.UHV; tier <= GTValues.MAX; tier++) {
            addENCN("block.gtceu." + GTValues.VN[tier].toLowerCase() + "_parallel_hatch", GTValues.VNF[tier] + " Parallel Control Hatch", GTOValues.VNFR[tier] + "并行控制仓");
        }

        addENCN("block.gtceu.max_256a_laser_source_hatch", "256A §c§lMAX§r Laser Source Hatch", "256§e安§r§c§lMAX§r激光源仓");
        addENCN("block.gtceu.max_256a_laser_target_hatch", "256A §c§lMAX§r Laser Target Hatch", "256§e安§r§c§lMAX§r激光靶仓");
        addENCN("block.gtceu.max_1024a_laser_source_hatch", "1024A §c§lMAX§r Laser Source Hatch", "1024§e安§r§c§lMAX§r激光源仓");
        addENCN("block.gtceu.max_1024a_laser_target_hatch", "1024A §c§lMAX§r Laser Target Hatch", "1024§e安§r§c§lMAX§r激光靶仓");
        addENCN("block.gtceu.max_4096a_laser_source_hatch", "4096A §c§lMAX§r Laser Source Hatch", "4096§e安§r§c§lMAX§r激光源仓");
        addENCN("block.gtceu.max_4096a_laser_target_hatch", "4096A §c§lMAX§r Laser Target Hatch", "4096§e安§r§c§lMAX§r激光靶仓");
    }
}
