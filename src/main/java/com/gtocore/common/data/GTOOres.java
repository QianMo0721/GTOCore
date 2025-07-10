package com.gtocore.common.data;

import com.gtolib.GTOCore;
import com.gtolib.api.data.Dimension;
import com.gtolib.utils.StringIndex;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.worldgen.GTOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.bedrockore.BedrockOreDefinition;
import com.gregtechceu.gtceu.api.data.worldgen.bedrockore.WeightedMaterial;
import com.gregtechceu.gtceu.api.data.worldgen.generator.VeinGenerator;
import com.gregtechceu.gtceu.api.data.worldgen.generator.indicators.SurfaceIndicatorGenerator;
import com.gregtechceu.gtceu.api.data.worldgen.generator.veins.DikeVeinGenerator;
import com.gregtechceu.gtceu.api.data.worldgen.generator.veins.VeinedVeinGenerator;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTOres;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.Level;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gtocore.common.data.GTOMaterials.*;
import static com.gtolib.api.data.GTODimensions.*;
import static com.gtolib.api.data.GTOWorldGenLayers.ALL_LAYER;

@SuppressWarnings("unused")
public final class GTOOres {

    private static final Map<ResourceLocation, MaterialSelector> RANDOM_ORES = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, Object2IntOpenHashMap<Material>> ALL_ORES = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, BedrockOre> BEDROCK_ORES = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, BedrockOreDefinition> BEDROCK_ORES_DEFINITION = new Object2ObjectOpenHashMap<>();

    public static void init() {
        if (false) {
            Map<ResourceLocation, Set<String>> ORE_MAP = new Object2ObjectOpenHashMap<>();
            ORE_MAP.put(THE_NETHER, Set.of("TagPrefix.oreNetherrack"));
            ORE_MAP.put(THE_END, Set.of("TagPrefix.oreEndstone"));
            ORE_MAP.put(MOON, Set.of("GTOTagPrefix.MOON_STONE"));
            ORE_MAP.put(MARS, Set.of("GTOTagPrefix.MARS_STONE"));
            ORE_MAP.put(VENUS, Set.of("GTOTagPrefix.VENUS_STONE"));
            ORE_MAP.put(MERCURY, Set.of("GTOTagPrefix.MERCURY_STONE"));
            ORE_MAP.put(GLACIO, Set.of("GTOTagPrefix.GLACIO_STONE"));
            ORE_MAP.put(TITAN, Set.of("GTOTagPrefix.TITAN_STONE"));
            ORE_MAP.put(PLUTO, Set.of("GTOTagPrefix.PLUTO_STONE"));
            ORE_MAP.put(IO, Set.of("GTOTagPrefix.IO_STONE"));
            ORE_MAP.put(GANYMEDE, Set.of("GTOTagPrefix.GANYMEDE_STONE"));
            ORE_MAP.put(ENCELADUS, Set.of("GTOTagPrefix.ENCELADUS_STONE"));
            ORE_MAP.put(CERES, Set.of("GTOTagPrefix.CERES_STONE"));
            StringBuilder stringBuilder = new StringBuilder();
            Map<String, Set<String>> a = new Object2ObjectOpenHashMap<>();
            GTOOres.ALL_ORES.forEach((k, v) -> v.keySet().forEach(m -> {
                Set<String> b = a.computeIfAbsent(StringIndex.MATERIAL_MAP.get(m), m1 -> new HashSet<>());
                if (ORE_MAP.containsKey(k)) b.addAll(ORE_MAP.get(k));
                a.put(StringIndex.MATERIAL_MAP.get(m), b);
            }));
            a.forEach((m, s) -> {
                stringBuilder.append("\nOREBuilder.put(").append(m).append(", ");
                StringBuilder sb = new StringBuilder();
                sb.append("Set.of(");
                s.forEach(s1 -> sb.append(s1).append(","));
                sb.deleteCharAt(sb.length() - 1).append(")");
                stringBuilder.append(sb).append(");");
            });
            GTOCore.LOGGER.error(stringBuilder.toString());
        }

        if (false) {
            Map<Material, String> materialFieldMap = getMaterialStringMap();

            Dimension[] ALL_DIM = { Dimension.OVERWORLD, Dimension.ANCIENT_WORLD, Dimension.THE_NETHER, Dimension.MOON, Dimension.MARS, Dimension.VENUS, Dimension.MERCURY, Dimension.GLACIO, Dimension.TITAN, Dimension.PLUTO, Dimension.IO, Dimension.GANYMEDE, Dimension.ENCELADUS, Dimension.CERES, Dimension.BARNARDA_C, Dimension.OTHERSIDE };
            Map<ResourceLocation, DimensionOreInfo> dimensionOreInfoMap = new HashMap<>();
            Map<ResourceLocation, Map<Material, Double>> normalizedOreWeights = new HashMap<>();

            // 计算每个矿脉的均衡权重
            for (Map.Entry<ResourceLocation, BedrockOre> entry : BEDROCK_ORES.entrySet()) {
                ResourceLocation veinId = entry.getKey();
                BedrockOre bedrockOre = entry.getValue();

                int totalOreWeightInVein = bedrockOre.materials().stream()
                        .mapToInt(WeightedMaterial::weight)
                        .sum();

                Map<Material, Double> weights = new HashMap<>();
                for (WeightedMaterial wm : bedrockOre.materials()) {
                    double normalizedWeight = (double) wm.weight() / totalOreWeightInVein * 100.0;
                    weights.put(wm.material(), normalizedWeight);
                }
                normalizedOreWeights.put(veinId, weights);
            }

            // 处理所有矿脉定义
            for (Map.Entry<ResourceLocation, BedrockOre> entry : BEDROCK_ORES.entrySet()) {
                ResourceLocation veinId = entry.getKey();
                BedrockOre bedrockOre = entry.getValue();
                int veinWeight = bedrockOre.weight();

                Map<Material, Double> normalizedWeights = normalizedOreWeights.get(veinId);

                float density;
                GTOreDefinition oreDefinition = GTRegistries.ORE_VEINS.get(veinId);
                if (oreDefinition != null) {
                    density = oreDefinition.density();
                } else {
                    density = 1.0f;
                }

                for (ResourceKey<Level> dimensionKey : bedrockOre.dimensions()) {
                    ResourceLocation dimensionId = dimensionKey.location();

                    Dimension dim = Arrays.stream(ALL_DIM).filter(d -> d.getLocation().equals(dimensionId)).findFirst().orElse(null);
                    DimensionOreInfo oreInfo = dimensionOreInfoMap.computeIfAbsent(dimensionId, k -> new DimensionOreInfo(dim));

                    oreInfo.totalVeinWeight += veinWeight;

                    for (WeightedMaterial wm : bedrockOre.materials()) {
                        Material material = wm.material();
                        double oreWeight = normalizedWeights.getOrDefault(material, 0.0);
                        int combinedWeight = (int) (oreWeight * veinWeight * density);
                        oreInfo.ores.compute(material, (k, v) -> {
                            if (v == null) {
                                return new OreData(oreWeight, veinWeight, combinedWeight, density);
                            }
                            // 更新现有矿石数据
                            v.totalOreWeight += oreWeight;
                            v.totalVeinWeight += veinWeight;
                            v.combinedWeight += combinedWeight;
                            return v;
                        });
                    }
                }
            }

            // 输出矿石信息
            StringBuilder report = new StringBuilder("\n# ***维度矿石统计报告***");
            StringBuilder report_arrays = new StringBuilder("***维度矿石统计输出(数组形式)***");
            StringBuilder report_table = new StringBuilder("***维度矿石统计输出(表格形式)***");
            for (Map.Entry<ResourceLocation, DimensionOreInfo> entry : dimensionOreInfoMap.entrySet()) {
                ResourceLocation dimId = entry.getKey();
                DimensionOreInfo info = entry.getValue();
                Dimension dim = info.dimension;

                String dimName = dim != null ? String.format("%s (%s)", dim.getEn(), dim.getCn()) : dimId.toString();

                report.append("\n\n## **维度**: ").append(dimName).append("  ");
                report.append("\n**ID**: ").append(dimId).append("  ");

                if (dim != null) {
                    report.append("\n**层级**: ").append(dim.getTier()).append("  ");
                    report.append("\n**星系**: ").append(dim.getGalaxy()).append("  ");
                    report.append("\n**轨道**: ").append(dim.getOrbit()).append("  ");
                }
                report.append("\n**总矿脉权重**: ").append(info.totalVeinWeight).append("  ");

                report.append("\n\n### **矿石列表**:  ");
                report.append("\n|序号|矿石名称|  |矿石权重|矿脉权重|矿脉密度|联合权重|");
                report.append("\n|-----|----------|--------------------|------|------|------|------|");
                AtomicInteger index = new AtomicInteger(1);
                info.ores.entrySet().stream()
                        .sorted((e1, e2) -> Integer.compare(e2.getValue().combinedWeight, e1.getValue().combinedWeight))
                        .forEach(e -> {
                            Material material = e.getKey();
                            OreData data = e.getValue();
                            int currentIndex = index.getAndIncrement();
                            String fieldName = materialFieldMap.getOrDefault(material, material.getName());
                            report.append(String.format(
                                    "\n| %d | %s | %s | %s | %d | %s | %d |",
                                    currentIndex,
                                    I18n.get(material.getUnlocalizedName()),
                                    fieldName,
                                    String.format("%.1f", data.totalOreWeight),
                                    data.totalVeinWeight,
                                    String.format("%.0f%%", data.density * 100),
                                    data.combinedWeight));
                        });

                // 为当前维度创建矿石组合权重映射
                Object2IntMap<Material> oreMap = new Object2IntOpenHashMap<>();
                info.ores.forEach((material, oreData) -> oreMap.put(material, oreData.combinedWeight));

                // 数组形式输出
                report_arrays.append("\n\n维度: ").append(dimName);
                report_arrays.append("\nID: ").append(dimId);
                List<String> materialNames = new ArrayList<>();
                List<Integer> weights = new ArrayList<>();
                List<String> localizationKeys = new ArrayList<>();
                for (var entry2 : oreMap.object2IntEntrySet()) {
                    Material material = entry2.getKey();
                    materialNames.add(materialFieldMap.getOrDefault(material, material.getName()));
                    weights.add(entry2.getIntValue());
                    localizationKeys.add(I18n.get(material.getUnlocalizedName()));
                }
                String materialNamesStr = materialNames.stream().collect(Collectors.joining(", ", "new Material[] { ", " }"));
                String weightsStr = weights.stream().map(Object::toString).collect(Collectors.joining(", ", "new int[] { ", " }"));
                String localizationKeysStr = localizationKeys.stream().collect(Collectors.joining(", ", "{ ", " }"));
                report_arrays.append("\n材料名称数组: \n").append(materialNamesStr);
                report_arrays.append("\n权重数组: \n").append(weightsStr);
                report_arrays.append("\n本地化键数组: \n").append(localizationKeysStr);

                // 表格形式输出
                report_table.append("\n\n维度: ").append(dimName);
                report_table.append("\nID: ").append(dimId);
                report_table.append("\n材料名称,权重,本地化键");
                for (var entry3 : oreMap.object2IntEntrySet()) {
                    Material material = entry3.getKey();
                    String fieldName = materialFieldMap.getOrDefault(material, material.getName());
                    report_table.append(String.format("\n%s,%d,%s",
                            fieldName,
                            entry3.getIntValue(),
                            I18n.get(material.getUnlocalizedName())));
                }
            }
            report.append("\n\n****报告结束****");
            try {
                Path logDir = Paths.get("logs", "report");
                if (!Files.exists(logDir)) Files.createDirectories(logDir);
                Path reportPath = logDir.resolve("ore_report.md");
                Path reportPath_arrays = logDir.resolve("ore_report_arrays.txt");
                Path reportPath_table = logDir.resolve("ore_report_table.csv");
                try (BufferedWriter writer = Files.newBufferedWriter(reportPath)) {
                    writer.write(report.toString());
                }
                try (BufferedWriter writer = Files.newBufferedWriter(reportPath_arrays)) {
                    writer.write(report_arrays.toString());
                }
                try (BufferedWriter writer = Files.newBufferedWriter(reportPath_table, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    writer.write('\uFEFF');
                    writer.write(report_table.toString());
                }
                GTOCore.LOGGER.info("矿石统计报告已生成: {}", reportPath.toAbsolutePath());
            } catch (Exception e) {
                GTOCore.LOGGER.error("生成矿石统计报告失败", e);
            }
        }
    }

    private static final GTOreDefinition BAUXITE_VEIN = create("bauxite_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.3f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(MOON, GANYMEDE, OTHERSIDE)
            .heightRangeUniform(10, 80)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(2).mat(Bauxite).size(1, 4))
                            .layer(l -> l.weight(1).mat(Ilmenite).size(1, 2))
                            .layer(l -> l.weight(1).mat(Aluminium).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Bauxite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition CHROMITE_VEIN = create("chromite_vein", vein -> vein
            .clusterSize(UniformInt.of(38, 44)).density(0.15f).weight(30)
            .layer(ALL_LAYER)
            .dimensions(VENUS, TITAN, OTHERSIDE)
            .heightRangeUniform(20, 80)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Magnetite).size(1, 3))
                            .layer(l -> l.weight(1).mat(VanadiumMagnetite).size(1, 1))
                            .layer(l -> l.weight(4).mat(Chromite).size(1, 4))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Magnetite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition NAQUADAH_VEIN = create("naquadah_vein", vein -> vein
            .clusterSize(UniformInt.of(48, 80)).density(0.25f).weight(30)
            .layer(ALL_LAYER)
            .dimensions(IO, PLUTO, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(10, 90)
            .cuboidVeinGenerator(generator -> generator
                    .top(b -> b.mat(Naquadah).size(2))
                    .middle(b -> b.mat(Naquadah).size(3))
                    .bottom(b -> b.mat(Naquadah).size(2))
                    .spread(b -> b.mat(Plutonium239)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Naquadah)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition PITCHBLENDE_VEIN = create("pitchblende_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 64)).density(0.25f).weight(30)
            .layer(ALL_LAYER)
            .dimensions(MOON, TITAN, PLUTO, OTHERSIDE)
            .heightRangeUniform(30, 60)
            .cuboidVeinGenerator(generator -> generator
                    .top(b -> b.mat(Pitchblende).size(2))
                    .middle(b -> b.mat(Pitchblende).size(3))
                    .bottom(b -> b.mat(Pitchblende).size(2))
                    .spread(b -> b.mat(Uraninite)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Pitchblende)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition SCHEELITE_VEIN = create("scheelite_vein", vein -> vein
            .clusterSize(UniformInt.of(50, 64)).density(0.7f).weight(20)
            .layer(ALL_LAYER)
            .dimensions(MARS, GLACIO, CERES, OTHERSIDE)
            .heightRangeUniform(20, 60)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Scheelite, 3, 20, 60))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Tungstate, 2, 35, 55))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Lithium, 1, 20, 40)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Scheelite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition SHELDONITE_VEIN = create("sheldonite_vein", vein -> vein
            .clusterSize(UniformInt.of(25, 29)).density(0.2f).weight(10)
            .layer(ALL_LAYER)
            .dimensions(MARS, MERCURY, ENCELADUS, GLACIO, OTHERSIDE)
            .heightRangeUniform(5, 50)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Bornite).size(2, 4))
                            .layer(l -> l.weight(2).mat(Cooperite).size(1, 1))
                            .layer(l -> l.weight(2).mat(Platinum).size(1, 1))
                            .layer(l -> l.weight(1).mat(Palladium).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Platinum)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition BANDED_IRON_VEIN = create("banded_iron_vein", vein -> vein
            .clusterSize(UniformInt.of(40, 52)).density(1.0f).weight(30)
            .layer(ALL_LAYER)
            .dimensions(VENUS, THE_NETHER, OTHERSIDE)
            .heightRangeUniform(20, 40)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Goethite, 3))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(YellowLimonite, 2))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Hematite, 2))
                    .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Gold, 1))
                    .rareBlockChance(0.075f)
                    .veininessThreshold(0.01f)
                    .maxRichnessThreshold(0.175f)
                    .minRichness(0.7f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.1f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Goethite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition BERYLLIUM_VEIN = create("beryllium_vein", vein -> vein
            .clusterSize(UniformInt.of(50, 64)).density(0.75f).weight(30)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, THE_NETHER, GANYMEDE, OTHERSIDE)
            .heightRangeUniform(5, 30)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Beryllium, 3, 5, 30))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Emerald, 2, 5, 19))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Emerald, 2, 16, 30)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Beryllium)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition CERTUS_QUARTZ_VEIN = create("certus_quartz", vein -> vein
            .clusterSize(UniformInt.of(25, 29)).density(0.25f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, PLUTO, THE_NETHER, OTHERSIDE)
            .heightRangeUniform(20, 120)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Quartzite).size(2, 4))
                            .layer(l -> l.weight(2).mat(CertusQuartz).size(1, 1))
                            .layer(l -> l.weight(1).mat(Barite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(CertusQuartz)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.BELOW)));

    private static final GTOreDefinition MANGANESE_VEIN = create("manganese_vein", vein -> vein
            .clusterSize(UniformInt.of(50, 64)).density(0.75f).weight(20)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, ANCIENT_WORLD, MERCURY, THE_NETHER, CERES, OTHERSIDE)
            .heightRangeUniform(-30, 0)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Grossular, 3, -50, -5))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Spessartine, 2, -40, -15))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Pyrolusite, 2, -40, -15))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Tantalite, 1, -30, -5)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Grossular)
                    .density(0.15f)
                    .radius(3)));

    private static final GTOreDefinition MOLYBDENUM_VEIN = create("molybdenum_vein", vein -> vein
            .clusterSize(UniformInt.of(25, 29)).density(0.25f).weight(5)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, VENUS, THE_NETHER, IO, ENCELADUS, OTHERSIDE)
            .heightRangeUniform(20, 50)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Wulfenite).size(2, 4))
                            .layer(l -> l.weight(2).mat(Molybdenite).size(1, 1))
                            .layer(l -> l.weight(1).mat(Molybdenum).size(1, 1))
                            .layer(l -> l.weight(1).mat(Powellite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Molybdenum)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition MONAZITE_VEIN = create("monazite_vein", vein -> vein
            .clusterSize(UniformInt.of(25, 29)).density(0.25f).weight(30)
            .layer(ALL_LAYER)
            .dimensions(MOON, CERES, GLACIO, OTHERSIDE)
            .heightRangeUniform(20, 40)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Bastnasite).size(2, 4))
                            .layer(l -> l.weight(1).mat(Monazite).size(1, 1))
                            .layer(l -> l.weight(1).mat(Neodymium).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Bastnasite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition NETHER_QUARTZ_VEIN = create("nether_quartz_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.2f).weight(80)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, THE_NETHER, OTHERSIDE)
            .heightRangeUniform(0, 40)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(NetherQuartz).size(2, 4))
                            .layer(l -> l.weight(1).mat(Quartzite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(NetherQuartz)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition REDSTONE_VEIN = create("redstone_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.2f).weight(60)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, THE_NETHER, ENCELADUS, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(5, 40)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Redstone).size(2, 4))
                            .layer(l -> l.weight(2).mat(Ruby).size(1, 1))
                            .layer(l -> l.weight(1).mat(Cinnabar).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Redstone)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition SALTPETER_VEIN = create("saltpeter_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.25f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, MARS, MERCURY, THE_NETHER, OTHERSIDE)
            .heightRangeUniform(5, 45)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Saltpeter).size(2, 4))
                            .layer(l -> l.weight(2).mat(Diatomite).size(1, 1))
                            .layer(l -> l.weight(2).mat(Electrotine).size(1, 1))
                            .layer(l -> l.weight(1).mat(Alunite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Saltpeter)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition SULFUR_VEIN = create("sulfur_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.2f).weight(100)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, VENUS, THE_NETHER, IO, OTHERSIDE)
            .heightRangeUniform(10, 30)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Sulfur).size(2, 4))
                            .layer(l -> l.weight(2).mat(Pyrite).size(1, 1))
                            .layer(l -> l.weight(1).mat(Sphalerite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Sulfur)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition TETRAHEDRITE_VEIN = create("tetrahedrite_vein", vein -> vein
            .clusterSize(UniformInt.of(40, 52)).density(1.0f).weight(70)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, MARS, THE_NETHER, TITAN, OTHERSIDE)
            .heightRangeUniform(20, 100)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Tetrahedrite, 4))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Copper, 2))
                    .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Stibnite, 1))
                    .rareBlockChance(0.15f)
                    .veininessThreshold(0.01f)
                    .maxRichnessThreshold(0.175f)
                    .minRichness(0.7f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.1f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Tetrahedrite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.BELOW)));

    private static final GTOreDefinition TOPAZ_VEIN = create("topaz_vein", vein -> vein
            .clusterSize(UniformInt.of(25, 29)).density(0.25f).weight(70)
            .layer(ALL_LAYER)
            .dimensions(ANCIENT_WORLD, MERCURY, THE_NETHER, ENCELADUS, OTHERSIDE)
            .heightRangeUniform(20, 70)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(BlueTopaz).size(2, 4))
                            .layer(l -> l.weight(2).mat(Topaz).size(1, 1))
                            .layer(l -> l.weight(2).mat(Chalcocite).size(1, 1))
                            .layer(l -> l.weight(1).mat(Bornite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Topaz)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.BELOW)));

    private static final GTOreDefinition APATITE_VEIN = create("apatite_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.25f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MARS, TITAN, PLUTO, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(10, 80)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Apatite).size(2, 4))
                            .layer(l -> l.weight(2).mat(TricalciumPhosphate).size(1, 1))
                            .layer(l -> l.weight(1).mat(Pyrochlore).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Apatite)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition CASSITERITE_VEIN = create("cassiterite_vein", vein -> vein
            .clusterSize(UniformInt.of(40, 52)).density(1.0f).weight(80)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MOON, GANYMEDE, GLACIO, OTHERSIDE)
            .heightRangeUniform(10, 80)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Tin, 4))
                    .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Cassiterite, 2))
                    .rareBlockChance(0.33f)
                    .veininessThreshold(0.01f)
                    .maxRichnessThreshold(0.175f)
                    .minRichness(0.7f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.1f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Cassiterite)));

    private static final GTOreDefinition COAL_VEIN = create("coal_vein", vein -> vein
            .clusterSize(UniformInt.of(38, 44)).density(0.25f).weight(80)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, GLACIO, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(10, 140)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Coal).size(2, 4))
                            .layer(l -> l.weight(3).mat(Coal).size(2, 4))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Coal)));

    private static final GTOreDefinition COPPER_TIN_VEIN = create("copper_tin_vein", vein -> vein
            .clusterSize(UniformInt.of(40, 52)).density(1.0f).weight(50)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MERCURY, GANYMEDE, CERES, OTHERSIDE)
            .heightRangeUniform(-10, 160)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Chalcopyrite, 5))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Zeolite, 2))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Cassiterite, 2))
                    .rareBlock(new VeinedVeinGenerator.VeinBlockDefinition(Realgar, 1))
                    .rareBlockChance(0.1f)
                    .veininessThreshold(0.01f)
                    .maxRichnessThreshold(0.175f)
                    .minRichness(0.7f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.1f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Chalcopyrite)));

    private static final GTOreDefinition GALENA_VEIN = create("galena_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.25f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, VENUS, ENCELADUS, PLUTO, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(-15, 45)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Galena).size(2, 4))
                            .layer(l -> l.weight(2).mat(Silver).size(1, 1))
                            .layer(l -> l.weight(1).mat(Lead).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Galena)));

    private static final GTOreDefinition GARNET_TIN_VEIN = create("garnet_tin_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.4f).weight(80)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MOON, TITAN, GLACIO, OTHERSIDE)
            .heightRangeUniform(30, 60)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(CassiteriteSand).size(2, 4))
                            .layer(l -> l.weight(2).mat(GarnetSand).size(1, 1))
                            .layer(l -> l.weight(2).mat(Asbestos).size(1, 1))
                            .layer(l -> l.weight(1).mat(Diatomite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(GarnetSand)));

    private static final GTOreDefinition GARNET_VEIN = create("garnet_vein", vein -> vein
            .clusterSize(UniformInt.of(50, 64)).density(0.75f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MARS, PLUTO, OTHERSIDE)
            .heightRangeUniform(-10, 50)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(GarnetRed, 3, -10, 50))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(GarnetYellow, 2, -10, 50))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Amethyst, 2, -10, 22))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Opal, 1, 18, 50)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(GarnetRed)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition IRON_VEIN = create("iron_vein", vein -> vein
            .clusterSize(UniformInt.of(40, 52)).density(1.0f).weight(120)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MARS, IO, TITAN, CERES, OTHERSIDE)
            .heightRangeUniform(-10, 60)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Goethite, 5))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(YellowLimonite, 2))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Hematite, 2))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Malachite, 1))
                    .veininessThreshold(0.01f)
                    .maxRichnessThreshold(0.175f)
                    .minRichness(0.7f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.1f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Goethite)));

    private static final GTOreDefinition LUBRICANT_VEIN = create("lubricant_vein", vein -> vein
            .clusterSize(UniformInt.of(25, 29)).density(0.25f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MOON, GANYMEDE, CERES, OTHERSIDE)
            .heightRangeUniform(0, 50)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Soapstone).size(2, 4))
                            .layer(l -> l.weight(2).mat(Talc).size(1, 1))
                            .layer(l -> l.weight(2).mat(GlauconiteSand).size(1, 1))
                            .layer(l -> l.weight(1).mat(Pentlandite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Talc)));

    private static final GTOreDefinition MAGNETITE_VEIN = create("magnetite_vein", vein -> vein
            .clusterSize(UniformInt.of(38, 44)).density(0.15f).weight(80)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MOON, MARS, OTHERSIDE)
            .heightRangeUniform(10, 60)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Magnetite).size(2, 4))
                            .layer(l -> l.weight(2).mat(VanadiumMagnetite).size(1, 1))
                            .layer(l -> l.weight(1).mat(Gold).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Magnetite)));

    private static final GTOreDefinition MINERAL_SAND_VEIN = create("mineral_sand_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.2f).weight(80)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MARS, IO, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(15, 60)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(BasalticMineralSand).size(2, 4))
                            .layer(l -> l.weight(2).mat(GraniticMineralSand).size(1, 1))
                            .layer(l -> l.weight(2).mat(FullersEarth).size(1, 1))
                            .layer(l -> l.weight(1).mat(Gypsum).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(BasalticMineralSand)));

    private static final GTOreDefinition NICKEL_VEIN = create("nickel_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.25f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MERCURY, ANCIENT_WORLD, GANYMEDE, OTHERSIDE)
            .heightRangeUniform(-10, 60)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Garnierite).size(2, 4))
                            .layer(l -> l.weight(2).mat(Nickel).size(1, 1))
                            .layer(l -> l.weight(2).mat(Cobaltite).size(1, 1))
                            .layer(l -> l.weight(1).mat(Pentlandite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Nickel)));

    private static final GTOreDefinition SALTS_VEIN = create("salts_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.2f).weight(50)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, ANCIENT_WORLD, GLACIO, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(30, 70)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(RockSalt).size(2, 4))
                            .layer(l -> l.weight(2).mat(Salt).size(1, 1))
                            .layer(l -> l.weight(1).mat(Lepidolite).size(1, 1))
                            .layer(l -> l.weight(1).mat(Spodumene).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Salt)));

    private static final GTOreDefinition OILSANDS_VEIN = create("oilsands_vein", vein -> vein
            .clusterSize(UniformInt.of(25, 29)).density(0.3f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, GLACIO, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(30, 80)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Oilsands).size(2, 4))
                            .layer(l -> l.weight(2).mat(Oilsands).size(1, 1))
                            .layer(l -> l.weight(1).mat(Oilsands).size(1, 1))
                            .layer(l -> l.weight(1).mat(Oilsands).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Oilsands)));

    private static final GTOreDefinition COPPER_VEIN = create("copper_vein", vein -> vein
            .clusterSize(UniformInt.of(40, 52)).density(1.0f).weight(80)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, ANCIENT_WORLD, MARS, ENCELADUS, OTHERSIDE)
            .heightRangeUniform(-40, 10)
            .veinedVeinGenerator(generator -> generator
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Chalcopyrite, 5))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Iron, 2))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Pyrite, 2))
                    .oreBlock(new VeinedVeinGenerator.VeinBlockDefinition(Copper, 2))
                    .veininessThreshold(0.01f)
                    .maxRichnessThreshold(0.175f)
                    .minRichness(0.7f)
                    .maxRichness(1.0f)
                    .edgeRoundoffBegin(3)
                    .maxEdgeRoundoff(0.1f))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Copper)));

    private static final GTOreDefinition DIAMOND_VEIN = create("diamond_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.25f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, VENUS, IO, OTHERSIDE)
            .heightRangeUniform(-55, -30)
            .classicVeinGenerator(generator -> generator
                    .primary(b -> b.mat(Graphite).size(4))
                    .secondary(b -> b.mat(Graphite).size(3))
                    .between(b -> b.mat(Diamond).size(3))
                    .sporadic(b -> b.mat(Coal)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Diamond)
                    .density(0.1f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                    .radius(2)));

    private static final GTOreDefinition LAPIS_VEIN = create("lapis_vein", vein -> vein
            .clusterSize(UniformInt.of(40, 52)).density(0.75f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, ANCIENT_WORLD, GLACIO, OTHERSIDE)
            .heightRangeUniform(-60, 10)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Lazurite, 3, -60, 10))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Sodalite, 2, -50, 0))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Lapis, 2, -50, 0))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(Calcite, 1, -40, 10)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Lapis)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                    .radius(3)));

    private static final GTOreDefinition MICA_VEIN = create("mica_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.25f).weight(20)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, MARS, PLUTO, GANYMEDE, GLACIO, OTHERSIDE)
            .heightRangeUniform(-40, -10)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Kyanite).size(2, 4))
                            .layer(l -> l.weight(2).mat(Mica).size(1, 1))
                            .layer(l -> l.weight(1).mat(Pollucite).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Mica)
                    .radius(3)));

    private static final GTOreDefinition OLIVINE_VEIN = create("olivine_vein", vein -> vein
            .clusterSize(UniformInt.of(32, 40)).density(0.25f).weight(20)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, VENUS, IO, CERES, OTHERSIDE)
            .heightRangeUniform(-20, 10)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Bentonite).size(2, 4))
                            .layer(l -> l.weight(2).mat(Magnetite).size(1, 1))
                            .layer(l -> l.weight(2).mat(Olivine).size(1, 1))
                            .layer(l -> l.weight(1).mat(GlauconiteSand).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Olivine)
                    .density(0.15f)
                    .radius(3)));

    private static final GTOreDefinition SAPPHIRE_VEIN = create("sapphire_vein", vein -> vein
            .clusterSize(UniformInt.of(25, 30)).density(0.25f).weight(60)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, ANCIENT_WORLD, TITAN, OTHERSIDE)
            .heightRangeUniform(-40, 0)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(3).mat(Almandine).size(2, 4))
                            .layer(l -> l.weight(2).mat(Pyrope).size(1, 1))
                            .layer(l -> l.weight(1).mat(Sapphire).size(1, 1))
                            .layer(l -> l.weight(1).mat(GreenSapphire).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Sapphire)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)
                    .radius(3)));

    private static final GTOreDefinition CELESTINE_VEIN = create("celestine_vein", vein -> vein
            .clusterSize(UniformInt.of(20, 24)).density(0.15f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(IO, GLACIO, OTHERSIDE)
            .heightRangeUniform(0, 40)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(1).mat(GTOMaterials.Celestine).size(1, 4))
                            .layer(l -> l.weight(1).mat(Cooperite).size(2, 3))
                            .layer(l -> l.weight(2).mat(Trona).size(2, 4))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Cooperite)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition DESH_VEIN = create("desh_vein", vein -> vein
            .clusterSize(UniformInt.of(20, 24)).density(0.15f).weight(20)
            .layer(ALL_LAYER)
            .dimensions(VENUS, TITAN, OTHERSIDE)
            .heightRangeUniform(-40, 0)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(1).mat(GTOMaterials.Desh).size(2, 4))
                            .layer(l -> l.weight(2).mat(Magnesite).size(1, 4))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Magnesite)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition CALORITE_VEIN = create("calorite_vein", vein -> vein
            .clusterSize(UniformInt.of(20, 24)).density(0.15f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(MERCURY, BARNARDA_C, OTHERSIDE)
            .heightRangeUniform(-40, -10)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(1).mat(GTOMaterials.Calorite).size(2, 4))
                            .layer(l -> l.weight(2).mat(Cobalt).size(1, 4))
                            .layer(l -> l.weight(1).mat(Pyrochlore).size(1, 1))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Cobalt)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition OSTRUM_VEIN = create("ostrum_vein", vein -> vein
            .clusterSize(UniformInt.of(20, 24)).density(0.1f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(CERES, GLACIO, OTHERSIDE)
            .heightRangeUniform(-40, 0)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(1).mat(GTOMaterials.Ostrum).size(2, 4))
                            .layer(l -> l.weight(2).mat(Gold).size(1, 4))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Gold)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition TITANIUM_VEIN = create("titanium_vein", vein -> vein
            .clusterSize(UniformInt.of(15, 25)).density(0.1f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(ENCELADUS, OTHERSIDE)
            .heightRangeUniform(-40, -20)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(2).mat(Ilmenite).size(2, 4))
                            .layer(l -> l.weight(1).mat(Titanium).size(1, 4))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Ilmenite)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition ZIRCON_VEIN = create("zircon_vein", vein -> vein
            .clusterSize(UniformInt.of(24, 29)).density(0.2f).weight(40)
            .layer(ALL_LAYER)
            .dimensions(PLUTO, GLACIO, OTHERSIDE)
            .heightRangeUniform(-40, 0)
            .layeredVeinGenerator(generator -> generator
                    .buildLayerPattern(config -> config
                            .layer(l -> l.weight(1).mat(GTOMaterials.Zircon).size(1, 4))
                            .layer(l -> l.weight(1).mat(Tungsten).size(1, 2))
                            .layer(l -> l.weight(1).mat(Pyrolusite).size(2, 4))
                            .layer(l -> l.weight(1).mat(Tantalite).size(1, 2))
                            .build()))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(Grossular)
                    .density(0.15f)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition CRYSTAL_VEIN_WATER_FIRE = create("crystal_vein_water_fire", vein -> vein
            .clusterSize(UniformInt.of(20, 40)).density(0.95f).weight(20)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, OTHERSIDE)
            .heightRangeUniform(-50, 0)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(PerditioCrystal, 1, -60, 20))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(SalamanderCrystal, 1, -60, 20))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(PerditioCrystal, 1, -60, 20))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(UndineCrystal, 1, -60, 20)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(PerditioCrystal)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static final GTOreDefinition CRYSTAL_VEIN_EARTH_WIND = create("crystal_vein_earth_wind", vein -> vein
            .clusterSize(UniformInt.of(20, 40)).density(0.95f).weight(20)
            .layer(ALL_LAYER)
            .dimensions(OVERWORLD, OTHERSIDE)
            .heightRangeUniform(-50, 0)
            .dikeVeinGenerator(generator -> generator
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(PerditioCrystal, 1, -60, 20))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(GnomeCrystal, 1, -60, 20))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(PerditioCrystal, 1, -60, 20))
                    .withBlock(new DikeVeinGenerator.DikeBlockDefinition(SylphCrystal, 1, -60, 20)))
            .surfaceIndicatorGenerator(indicator -> indicator
                    .surfaceRock(PerditioCrystal)
                    .placement(SurfaceIndicatorGenerator.IndicatorPlacement.ABOVE)));

    private static GTOreDefinition create(String name, Consumer<GTOreDefinition> config) {
        ResourceLocation id = GTCEu.id(name);
        GTOreDefinition definition = GTOres.create(id, config);
        List<VeinGenerator.VeinEntry> entries = definition.veinGenerator().getAllEntries();
        Object2IntOpenHashMap<Material> materialMap = new Object2IntOpenHashMap<>();
        List<WeightedMaterial> materials = new ArrayList<>(entries.size());
        for (VeinGenerator.VeinEntry entry : entries) {
            Material material = entry.vein().right().orElse(null);
            if (material != null) {
                int value = entry.chance();
                materialMap.put(material, value);
                materials.add(new WeightedMaterial(material, value));
            }
        }
        for (ResourceKey<Level> dimension : definition.dimensionFilter()) {
            Object2IntOpenHashMap<Material> materialIntegerMap = ALL_ORES.computeIfAbsent(dimension.location(), k -> new Object2IntOpenHashMap<>());
            materialMap.forEach((material, amount) -> materialIntegerMap.mergeInt(material, amount, Math::max));
            ALL_ORES.put(dimension.location(), materialIntegerMap);
        }
        BEDROCK_ORES.put(id, new BedrockOre(definition.dimensionFilter(), definition.weight(), materials));
        return definition;
    }

    public record BedrockOre(Set<ResourceKey<Level>> dimensions, int weight, List<WeightedMaterial> materials) {}

    public static Material selectMaterial(ResourceLocation dimension) {
        MaterialSelector selector = RANDOM_ORES.computeIfAbsent(dimension, k -> {
            Object2IntOpenHashMap<Material> ores = ALL_ORES.get(k);
            if (ores == null) return null;
            return new MaterialSelector(ores);
        });
        if (selector == null) return GTMaterials.NULL;
        return selector.selectMaterial();
    }

    private static final class MaterialSelector {

        private final List<Material> materialList;
        private final List<Integer> cumulativeWeights;
        private int totalWeight;

        private MaterialSelector(Object2IntOpenHashMap<Material> materials) {
            this.materialList = new ArrayList<>(materials.keySet());
            this.cumulativeWeights = new ArrayList<>();
            this.totalWeight = 0;
            for (Integer weight : materials.values()) {
                this.totalWeight += weight;
                this.cumulativeWeights.add(this.totalWeight);
            }
        }

        private Material selectMaterial() {
            int randomValue = GTValues.RNG.nextInt(this.totalWeight);
            int index = Collections.binarySearch(this.cumulativeWeights, randomValue);
            if (index < 0) {
                index = -index - 1;
            }
            return materialList.get(index);
        }
    }

    private static Map<Material, String> getMaterialStringMap() {
        Map<Material, String> materialFieldMap = new HashMap<>();

        // 填充GTOMaterials类的静态字段
        for (java.lang.reflect.Field field : GTOMaterials.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                if (Material.class.isAssignableFrom(field.getType())) {
                    try {
                        Material material = (Material) field.get(null);
                        if (material != null) {
                            materialFieldMap.put(material, "GTOMaterials." + field.getName());
                        }
                    } catch (IllegalAccessException e) {
                        // 忽略无法访问的字段
                    }
                }
            }
        }

        // 填充GTMaterials类的静态字段
        for (java.lang.reflect.Field field : GTMaterials.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                if (Material.class.isAssignableFrom(field.getType())) {
                    try {
                        Material material = (Material) field.get(null);
                        if (material != null && !materialFieldMap.containsKey(material)) {
                            materialFieldMap.put(material, "GTMaterials." + field.getName());
                        }
                    } catch (IllegalAccessException e) {
                        // 忽略无法访问的字段
                    }
                }
            }
        }
        return materialFieldMap;
    }

    private static class DimensionOreInfo {

        final Dimension dimension;
        int totalVeinWeight = 0;
        Map<Material, OreData> ores = new HashMap<>();

        DimensionOreInfo(Dimension dimension) {
            this.dimension = dimension;
        }
    }

    private static class OreData {

        double totalOreWeight;
        int totalVeinWeight;
        int combinedWeight;
        float density;

        public OreData(double totalOreWeight, int totalVeinWeight, int combinedWeight, float density) {
            this.totalOreWeight = totalOreWeight;
            this.totalVeinWeight = totalVeinWeight;
            this.combinedWeight = combinedWeight;
            this.density = density;
        }
    }
}
