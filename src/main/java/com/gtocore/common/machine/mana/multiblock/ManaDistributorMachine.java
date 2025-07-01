package com.gtocore.common.machine.mana.multiblock;

import com.gtocore.client.ClientUtil;
import com.gtocore.common.wireless.WirelessManaContainer;

import com.gtolib.api.capability.IIWirelessInteractor;
import com.gtolib.api.machine.mana.feature.IManaMultiblock;
import com.gtolib.api.machine.mana.feature.IWirelessManaContainerHolder;
import com.gtolib.api.machine.mana.trait.ManaTrait;
import com.gtolib.api.machine.multiblock.NoRecipeLogicMultiblockMachine;
import com.gtolib.api.misc.ManaContainerList;
import com.gtolib.utils.GTOUtils;
import com.gtolib.utils.MachineUtils;

import com.gregtechceu.gtceu.api.machine.ConditionalSubscriptionHandler;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.IMachineLife;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import com.lowdragmc.lowdraglib.gui.util.ClickData;
import com.lowdragmc.lowdraglib.gui.widget.ComponentPanelWidget;
import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

import javax.annotation.Nullable;

public final class ManaDistributorMachine extends NoRecipeLogicMultiblockMachine implements IManaMultiblock, IMachineLife, IWirelessManaContainerHolder {

    private static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(ManaDistributorMachine.class, NoRecipeLogicMultiblockMachine.MANAGED_FIELD_HOLDER);
    public static final Map<ResourceLocation, Set<ManaDistributorMachine>> NETWORK = new Object2ObjectOpenHashMap<>();
    private int amount = 0;
    private BlockPos centrepos;
    private final ManaTrait manaTrait;
    private final int max;
    private final int radius;
    @Persisted
    private boolean wireless;
    private WirelessManaContainer WirelessManaContainerCache;
    private final ConditionalSubscriptionHandler tickSubs;

    public static Function<IMachineBlockEntity, ManaDistributorMachine> create(int max, int radius) {
        return holder -> new ManaDistributorMachine(holder, max, radius);
    }

    @Override
    @NotNull
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    private ManaDistributorMachine(IMachineBlockEntity holder, int max, int radius) {
        super(holder);
        this.max = max;
        this.radius = radius;
        this.tickSubs = new ConditionalSubscriptionHandler(this, this::tickUpdate, () -> isFormed && wireless);
        this.manaTrait = new ManaTrait(this);
    }

    private void tickUpdate() {
        if (getOffsetTimer() % 20 == 0) {
            var container = getWirelessManaContainer();
            if (container != null) {
                container.setStorage(container.getStorage().add(BigInteger.valueOf(removeMana(getManaContainer().getCurrentMana(), 20, false))));
                tickSubs.updateSubscription();
            } else {
                tickSubs.unsubscribe();
            }
        }
    }

    public boolean add(BlockPos pos) {
        if (GTOUtils.calculateDistance(pos, centrepos) > radius) return false;
        if (amount >= max) return false;
        amount++;
        return true;
    }

    public void remove() {
        if (amount > 0) amount--;
    }

    @Override
    public void customText(@NotNull List<Component> textList) {
        super.customText(textList);
        textList.add(Component.translatable("gtocore.machine.binding_amount", amount));
        textList.add(ComponentPanelWidget.withButton(Component.translatable("gui.enderio.range.show"), "show"));
        if (radius == 128) textList.add(ComponentPanelWidget.withButton(Component.translatable("gtocore.machine.wireless_mode").append("[").append(Component.translatable("gtocore.machine." + (wireless ? "on" : "off"))).append("]"), "wireless"));
        if (wireless) {
            var container = getWirelessManaContainer();
            if (container != null) {
                textList.add(Component.translatable("block.gtceu.long_distance_item_pipeline_network_header"));
                textList.add(Component.translatable("gtocore.machine.mana_stored", container.getStorage()));
            }
        }
    }

    @Override
    public void handleDisplayClick(String componentData, ClickData clickData) {
        if (clickData.isRemote) {
            if ("show".equals(componentData)) ClientUtil.highlighting(MachineUtils.getOffsetPos(2, 2, getFrontFacing(), getPos()), radius);
        } else {
            if ("wireless".equals(componentData)) {
                wireless = !wireless;
                if (wireless) {
                    tickSubs.updateSubscription();
                }
            }
        }
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        if (isRemote()) return;
        centrepos = MachineUtils.getOffsetPos(2, 2, getFrontFacing(), getPos());
        IIWirelessInteractor.addToNet(NETWORK, this);
        if (radius == 128) tickSubs.initialize(getLevel());
    }

    @Override
    public void onStructureInvalid() {
        centrepos = null;
        IIWirelessInteractor.removeFromNet(NETWORK, this);
        super.onStructureInvalid();
    }

    @Override
    public void onUnload() {
        super.onUnload();
        tickSubs.unsubscribe();
        IIWirelessInteractor.removeFromNet(NETWORK, this);
    }

    @Override
    @NotNull
    public ManaContainerList getManaContainer() {
        return manaTrait.getManaContainers();
    }

    @Override
    public boolean isGeneratorMana() {
        return false;
    }

    @Override
    public void setWorkingEnabled(boolean isWorkingAllowed) {
        if (isWorkingAllowed && isFormed()) {
            IIWirelessInteractor.addToNet(NETWORK, this);
        } else {
            IIWirelessInteractor.removeFromNet(NETWORK, this);
        }
        super.setWorkingEnabled(isWorkingAllowed);
    }

    @Override
    public void onMachinePlaced(@org.jetbrains.annotations.Nullable LivingEntity player, ItemStack stack) {
        if (player != null) {
            setOwnerUUID(player.getUUID());
        }
    }

    @Override
    @Nullable
    public UUID getUUID() {
        return getOwnerUUID();
    }

    public void setWirelessManaContainerCache(final WirelessManaContainer WirelessManaContainerCache) {
        this.WirelessManaContainerCache = WirelessManaContainerCache;
    }

    public WirelessManaContainer getWirelessManaContainerCache() {
        return this.WirelessManaContainerCache;
    }
}
