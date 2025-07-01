package com.gtocore.common.forge;

import com.gtocore.common.data.GTODamageTypes;

import com.gtolib.api.data.GTODimensions;
import com.gtolib.api.player.IEnhancedPlayer;
import com.gtolib.api.player.organ.Controller;
import com.gtolib.api.player.organ.capability.OrganCapability;
import com.gtolib.api.player.organ.data.OrganActivationMethod;

import net.minecraft.network.chat.Component;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.api.planets.PlanetApi;

import java.util.stream.IntStream;

public final class OrganForge {

    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            var cap = OrganCapability.of(player);
            Controller.applyTick(OrganActivationMethod.TICK, cap, player.tickCount);
            if (player.tickCount % 20 == 0) {
                whenPlayer20Tick(player);
            }
        }
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        OrganCapability.of(event.getEntity()).deserializeNBT(OrganCapability.of(event.getOriginal()).serializeNBT());
    }

    private static void whenPlayer20Tick(ServerPlayer player) {
        Planet planet = PlanetApi.API.getPlanet(player.level());
        if (planet == null) return;
        if (!player.gameMode.isSurvival()) return;
        if (GTODimensions.OVERWORLD.equals(planet.dimension().location())) return;
        if (!GTODimensions.isPlanet(planet.dimension().location())) return;
        int tier = planet.tier();
        int lowerTierTag = ((tier - 1) / 2) + 1;
        var cache = IEnhancedPlayer.of(player).getPlayerData();
        if (IntStream.rangeClosed(lowerTierTag, 4).noneMatch(cache.organTierCache::contains)) {
            Component customComponent = Component.translatable(
                    "gtocore.death.attack.turbulence_of_another_star",
                    player.getName(),
                    tier,
                    "最低Tier " + lowerTierTag);
            cache.floatCache.merge("try_attack_count", 1, (aFloat, aFloat2) -> {
                player.hurt(GTODamageTypes.getGenericDamageSource(player, customComponent, () -> cache.floatCache.put("try_attack_count", 0)), aFloat);
                if (aFloat > 40) {
                    player.server.tell(new TickTask(1, player::kill));
                    player.server.getPlayerList().broadcastSystemMessage(customComponent, true);
                    return 0f;
                }
                return aFloat + aFloat2;
            });
        }
    }
}
