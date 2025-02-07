package com.gto.gtocore.api.data;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.data.TagsHandler;
import com.gto.gtocore.utils.RLUtils;

import com.gregtechceu.gtceu.api.data.worldgen.IWorldGenLayer;
import com.gregtechceu.gtceu.api.data.worldgen.WorldGeneratorUtils;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import com.kyanite.deeperdarker.DeeperDarker;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public final class GTOWorldGenLayers implements IWorldGenLayer {

    public static final ResourceLocation OVERWORLD = RLUtils.mc("overworld");
    public static final ResourceLocation THE_NETHER = RLUtils.mc("the_nether");
    public static final ResourceLocation THE_END = RLUtils.mc("the_end");
    public static final ResourceLocation MOON = RLUtils.ad("moon");
    public static final ResourceLocation MARS = RLUtils.ad("mars");
    public static final ResourceLocation VENUS = RLUtils.ad("venus");
    public static final ResourceLocation MERCURY = RLUtils.ad("mercury");
    public static final ResourceLocation GLACIO = RLUtils.ad("glacio");
    public static final ResourceLocation ANCIENT_WORLD = GTOCore.id("ancient_world");
    public static final ResourceLocation TITAN = GTOCore.id("titan");
    public static final ResourceLocation PLUTO = GTOCore.id("pluto");
    public static final ResourceLocation IO = GTOCore.id("io");
    public static final ResourceLocation GANYMEDE = GTOCore.id("ganymede");
    public static final ResourceLocation ENCELADUS = GTOCore.id("enceladus");
    public static final ResourceLocation CERES = GTOCore.id("ceres");
    public static final ResourceLocation BARNARDA_C = GTOCore.id("barnarda_c");
    public static final ResourceLocation OTHERSIDE = DeeperDarker.rl("otherside");

    public static final ResourceLocation FLAT = GTOCore.id("flat");
    public static final ResourceLocation VOID = GTOCore.id("void");
    public static final ResourceLocation CREATE = GTOCore.id("create");

    public static Set<ResourceKey<Level>> getDimensions(ResourceLocation resourceLocation) {
        return Set.of(getDimension(resourceLocation));
    }

    public static ResourceKey<Level> getDimension(ResourceLocation resourceLocation) {
        return ResourceKey.create(Registries.DIMENSION, resourceLocation);
    }

    public static String getGalaxy(ResourceLocation d) {
        if (SOLAR.containsKey(d)) return "proxima_centauri";
        if (PROXIMA_CENTAURI.containsKey(d)) return "barnarda";
        if (BARNARDA.containsKey(d)) return "solar";
        return null;
    }

    public static final Map<ResourceLocation, Integer> ALL_GALAXY_DIM = new Object2IntOpenHashMap<>(28, 0.9F);
    private static final Map<ResourceLocation, Integer> SOLAR = new Object2IntOpenHashMap<>(24, 0.9F);
    private static final Map<ResourceLocation, Integer> PROXIMA_CENTAURI = new Object2IntOpenHashMap<>(3, 0.9F);
    private static final Map<ResourceLocation, Integer> BARNARDA = new Object2IntOpenHashMap<>(3, 0.9F);

    static {
        SOLAR.put(OVERWORLD, 3);
        SOLAR.put(RLUtils.ad("earth_orbit"), 3);
        SOLAR.put(MOON, 3);
        SOLAR.put(RLUtils.ad("moon_orbit"), 3);
        SOLAR.put(MARS, 4);
        SOLAR.put(RLUtils.ad("mars_orbit"), 4);
        SOLAR.put(VENUS, 2);
        SOLAR.put(RLUtils.ad("venus_orbit"), 2);
        SOLAR.put(MERCURY, 1);
        SOLAR.put(RLUtils.ad("mercury_orbit"), 1);
        SOLAR.put(TITAN, 5);
        SOLAR.put(GTOCore.id("titan_orbit"), 5);
        SOLAR.put(PLUTO, 7);
        SOLAR.put(GTOCore.id("pluto_orbit"), 7);
        SOLAR.put(IO, 6);
        SOLAR.put(GTOCore.id("io_orbit"), 6);
        SOLAR.put(GANYMEDE, 6);
        SOLAR.put(GTOCore.id("ganymede_orbit"), 6);
        SOLAR.put(ENCELADUS, 5);
        SOLAR.put(GTOCore.id("enceladus_orbit"), 5);
        SOLAR.put(CERES, 7);
        SOLAR.put(GTOCore.id("ceres_orbit"), 7);

        PROXIMA_CENTAURI.put(GLACIO, 1);
        PROXIMA_CENTAURI.put(RLUtils.ad("glacio_orbit"), 1);

        BARNARDA.put(BARNARDA_C, 1);
        BARNARDA.put(GTOCore.id("barnarda_c_orbit"), 1);

        ALL_GALAXY_DIM.putAll(SOLAR);
        ALL_GALAXY_DIM.putAll(PROXIMA_CENTAURI);
        ALL_GALAXY_DIM.putAll(BARNARDA);
    }

    private static final Set<ResourceLocation> SOLAR_PLANET = new LinkedHashSet<>(12, 0.9F);

    private static final Set<ResourceLocation> PROXIMA_CENTAURI_PLANET = new LinkedHashSet<>(2);

    private static final Set<ResourceLocation> BARNARDA_PLANET = new LinkedHashSet<>(2);

    public static final Set<ResourceLocation> ALL_PLANET = new LinkedHashSet<>(14, 0.9F);

    public static final Set<ResourceLocation> ALL_LAYER_DIMENSION = new LinkedHashSet<>(18, 0.9F);

    static {
        SOLAR_PLANET.add(OVERWORLD);
        SOLAR_PLANET.add(MOON);
        SOLAR_PLANET.add(MARS);
        SOLAR_PLANET.add(VENUS);
        SOLAR_PLANET.add(MERCURY);
        SOLAR_PLANET.add(TITAN);
        SOLAR_PLANET.add(PLUTO);
        SOLAR_PLANET.add(IO);
        SOLAR_PLANET.add(GANYMEDE);
        SOLAR_PLANET.add(ENCELADUS);
        SOLAR_PLANET.add(CERES);
        PROXIMA_CENTAURI_PLANET.add(GLACIO);
        BARNARDA_PLANET.add(BARNARDA_C);
        ALL_PLANET.addAll(SOLAR_PLANET);
        ALL_PLANET.addAll(PROXIMA_CENTAURI_PLANET);
        ALL_PLANET.addAll(BARNARDA_PLANET);
        ALL_LAYER_DIMENSION.addAll(ALL_PLANET);
        ALL_LAYER_DIMENSION.add(ANCIENT_WORLD);
        ALL_LAYER_DIMENSION.add(THE_NETHER);
        ALL_LAYER_DIMENSION.add(THE_END);
        ALL_LAYER_DIMENSION.add(OTHERSIDE);
    }

    public static final GTOWorldGenLayers ALL_LAYER = new GTOWorldGenLayers("all_layer", new TagMatchTest(TagsHandler.ALL_LAYER_STONE), ALL_LAYER_DIMENSION);

    private final String id;
    private final RuleTest target;
    private final Set<ResourceLocation> levels;

    private GTOWorldGenLayers(String id, RuleTest target, Set<ResourceLocation> levels) {
        this.id = id;
        this.target = target;
        this.levels = levels;
        WorldGeneratorUtils.WORLD_GEN_LAYERS.put(id, this);
    }

    @Override
    public Set<ResourceLocation> getLevels() {
        return levels;
    }

    @Override
    public RuleTest getTarget() {
        return target;
    }

    @Override
    public boolean isApplicableForLevel(ResourceLocation level) {
        return levels.contains(level);
    }

    @Override
    public @NotNull String getSerializedName() {
        return id;
    }

    public static void init() {}
}
