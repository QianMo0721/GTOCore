package com.gtocore.common.machine.monitor;

import com.gtocore.integration.gtmt.GtmtReflect;

import com.gtolib.api.wireless.WirelessManaContainer;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import com.lowdragmc.lowdraglib.syncdata.annotation.DescSynced;

import java.math.BigInteger;
import java.util.List;

public class MonitorMana extends AbstractInfoProviderMonitor implements ITeamInformationProvider {

    @DescSynced
    private long manaBufferCache = 0L;
    private long manaBufferCache0_5s = 0L;
    @DescSynced
    private long manaBufferCache1s = 0L;

    public MonitorMana(IMachineBlockEntity holder) {
        super(holder);
    }

    @Override
    public void syncInfoFromServer() {
        if (getUUID() != null) {
            manaBufferCache1s = manaBufferCache0_5s;
            manaBufferCache0_5s = manaBufferCache;
            manaBufferCache = WirelessManaContainer.getOrCreateContainer(getUUID()).getStorage().longValue();
        }
    }

    public MonitorMana(Object o) {
        this((IMachineBlockEntity) o);
    }

    @Override
    public DisplayComponentList provideInformation() {
        var informationList = ITeamInformationProvider.super.provideInformation();
        if (getUUID() != null) {
            informationList.addIfAbsent(
                    DisplayRegistry.MANA_CURRENT.id(),
                    Component.translatable("gtocore.machine.monitor.mana.current")
                            .withStyle(ChatFormatting.GRAY)
                            .append(Component.literal(String.valueOf(manaBufferCache)).withStyle(ChatFormatting.AQUA))
                            .getVisualOrderText());
            informationList.addIfAbsent(
                    DisplayRegistry.MANA_CURRENT_AS_POOL.id(),
                    Component.translatable("gtocore.machine.monitor.mana.pool.0")
                            .withStyle(ChatFormatting.GRAY)
                            .append(Component.literal(String.format("%.02f", manaBufferCache / 1e6)).withStyle(ChatFormatting.AQUA))
                            .append(Component.translatable("gtocore.machine.monitor.mana.pool.1").withStyle(ChatFormatting.GRAY))
                            .getVisualOrderText());
            long manaChange = (manaBufferCache - manaBufferCache1s);
            long manaChangeAbs = Math.abs(manaChange);
            if (manaChange >= 0) {
                informationList.addIfAbsent(
                        DisplayRegistry.MANA_CHANGE.id(),
                        Component.translatable("gtocore.machine.monitor.mana.increase",
                                manaChangeAbs).withStyle(ChatFormatting.BLUE)
                                .append(Component.literal(String.valueOf(manaChangeAbs / 1e6)).withStyle(ChatFormatting.AQUA))
                                .getVisualOrderText());
                informationList.addIfAbsent(
                        DisplayRegistry.MANA_CHANGE_AS_POOL.id(),
                        Component.translatable("gtocore.machine.monitor.mana.pool.0")
                                .withStyle(ChatFormatting.BLUE)
                                .append(Component.literal(String.format("%.02f", manaChangeAbs / 1e6)).withStyle(ChatFormatting.AQUA))
                                .append(Component.translatable("gtocore.machine.monitor.mana.pool.2").withStyle(ChatFormatting.BLUE))
                                .getVisualOrderText());
            } else {
                informationList.addIfAbsent(
                        DisplayRegistry.MANA_CHANGE.id(),
                        Component.translatable("gtocore.machine.monitor.mana.decrease",
                                manaChangeAbs).withStyle(ChatFormatting.GOLD)
                                .append(Component.literal(String.format(" (%.02f)", manaChangeAbs / MANA_PER_POOL)).withStyle(ChatFormatting.AQUA))
                                .getVisualOrderText());
                informationList.addIfAbsent(
                        DisplayRegistry.MANA_CHANGE_AS_POOL.id(),
                        Component.translatable("gtocore.machine.monitor.mana.pool.0")
                                .withStyle(ChatFormatting.BLUE)
                                .append(Component.literal(String.format("%.02f", manaChangeAbs / MANA_PER_POOL)).withStyle(ChatFormatting.AQUA))
                                .append(Component.translatable("gtocore.machine.monitor.mana.pool.2").withStyle(ChatFormatting.BLUE))
                                .getVisualOrderText());
                informationList.addIfAbsent(
                        DisplayRegistry.MANA_REMAINING_TIME.id(),
                        Component.translatable("gtceu.multiblock.power_substation.time_to_drain",
                                GtmtReflect.getTimeToFillDrainText(BigInteger.valueOf(manaChangeAbs)))
                                .getVisualOrderText());
            }
            informationList.addIfAbsent(
                    DisplayRegistry.MANA_PROGRESS_BAR.id(),
                    new IDisplayComponent() {

                        @Override
                        public ResourceLocation getId() {
                            return DisplayRegistry.MANA_PROGRESS_BAR.id();
                        }

                        @Override
                        public IDisplayComponent setInformation(Object... information) {
                            return this;
                        }

                        @Override
                        public DisplayType getDisplayType() {
                            return DisplayType.CUSTOM_RENDERER;
                        }

                        @Override
                        public int getVisualWidth() {
                            return 16;
                        }

                        @Override
                        public int getVisualHeight() {
                            return 16;
                        }
                    });
        }
        return informationList;
    }

    @Override
    public List<ResourceLocation> getAvailableRLs() {
        var rls = ITeamInformationProvider.super.getAvailableRLs();
        rls.addAll(List.of(
                DisplayRegistry.MANA_CURRENT.id(),
                DisplayRegistry.MANA_CURRENT_AS_POOL.id(),
                DisplayRegistry.MANA_CHANGE.id(),
                DisplayRegistry.MANA_CHANGE_AS_POOL.id(),
                DisplayRegistry.MANA_REMAINING_TIME.id(),
                DisplayRegistry.MANA_PROGRESS_BAR.id()));
        return rls;
    }
}
