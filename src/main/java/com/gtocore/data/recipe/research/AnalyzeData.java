package com.gtocore.data.recipe.research;

import com.gregtechceu.gtceu.GTCEu;

import static com.gtocore.data.lang.LangHandler.addCNEN;
import static com.gtocore.data.recipe.builder.research.ExResearchManager.writeAnalyzeResearchToMap;

public final class AnalyzeData {

    public static void init() {
        addCNEN("data.empty", "空", "empty");

        addData("error1", "§k1§r错误§k1§r", "§k1§rError§k1§r", 0, 1);
        addTooltip("error1", "只是一个意外罢了", "It was just an accident");
        addData("error2", "§k22§r错误§k22§r", "§k22§rError§k22§r", 0, 2);
        addData("error3", "§k333§r错误§k333§r", "§k333§rError§k333§r", 0, 3);
        addData("error4", "§k4444§r错误§k4444§r", "§k4444§rError§k4444§r", 0, 4);
        addData("error5", "§k55555§r错误§k55555§r", "§k55555§rError§k55555§r", 0, 5);
    }

    private static final boolean rundata = GTCEu.isDataGen();

    public static void addData(String key, String cn, String en, int dataTier, int dataCrystal) {
        if (rundata) addCNEN("data." + key, cn, en);
        else writeAnalyzeResearchToMap(key, dataTier, dataCrystal);
    }

    public static void addData(String cn, String en, int dataTier, int dataCrystal) {
        String key = "data." + en.replace(' ', '_').toLowerCase();
        if (rundata) addCNEN(key, cn, en);
        else writeAnalyzeResearchToMap(key, dataTier, dataCrystal);
    }

    public static void addTooltip(String key, String cn, String en) {
        if (rundata) addCNEN("data." + key + ".tooltip", cn, en);
    }
}
