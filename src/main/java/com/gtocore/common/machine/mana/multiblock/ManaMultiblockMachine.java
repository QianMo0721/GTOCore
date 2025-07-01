package com.gtocore.common.machine.mana.multiblock;

import com.gtolib.api.gui.GTOGuiTextures;
import com.gtolib.api.gui.OverclockConfigurator;
import com.gtolib.api.machine.feature.IOverclockConfigMachine;
import com.gtolib.api.machine.mana.feature.IManaMultiblock;
import com.gtolib.api.machine.mana.trait.ManaTrait;
import com.gtolib.api.machine.multiblock.NoEnergyMultiblockMachine;
import com.gtolib.api.misc.ManaContainerList;

import com.gregtechceu.gtceu.api.gui.fancy.ConfiguratorPanel;
import com.gregtechceu.gtceu.api.gui.fancy.IFancyConfiguratorButton;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;

import com.lowdragmc.lowdraglib.syncdata.annotation.Persisted;
import com.lowdragmc.lowdraglib.syncdata.field.ManagedFieldHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ManaMultiblockMachine extends NoEnergyMultiblockMachine implements IManaMultiblock, IOverclockConfigMachine {

    static final ManagedFieldHolder MANAGED_FIELD_HOLDER = new ManagedFieldHolder(ManaMultiblockMachine.class, NoEnergyMultiblockMachine.MANAGED_FIELD_HOLDER);

    @Override
    @NotNull
    public ManagedFieldHolder getFieldHolder() {
        return MANAGED_FIELD_HOLDER;
    }

    private boolean batchProcessing;
    @Persisted
    private int ocLimit = 20;
    private final ManaTrait manaTrait;

    public ManaMultiblockMachine(IMachineBlockEntity holder) {
        super(holder);
        this.manaTrait = new ManaTrait(this);
    }

    @Override
    public void saveCustomPersistedData(@NotNull CompoundTag tag, boolean forDrop) {
        super.saveCustomPersistedData(tag, forDrop);
        if (isGeneratorMana()) return;
        tag.putBoolean("batchProcessing", batchProcessing);
    }

    @Override
    public void loadCustomPersistedData(@NotNull CompoundTag tag) {
        super.loadCustomPersistedData(tag);
        if (isGeneratorMana()) return;
        batchProcessing = tag.getBoolean("batchProcessing");
    }

    @Override
    public void attachConfigurators(@NotNull ConfiguratorPanel configuratorPanel) {
        super.attachConfigurators(configuratorPanel);
        if (!isGeneratorMana()) {
            configuratorPanel.attachConfigurators(new OverclockConfigurator(this));
            configuratorPanel.attachConfigurators(new IFancyConfiguratorButton.Toggle(GTOGuiTextures.BATCH_MODE.getSubTexture(0, 0, 1, 0.5), GTOGuiTextures.BATCH_MODE.getSubTexture(0, 0.5, 1, 0.5), this::isBatchProcessing, (clickData, pressed) -> batchProcessing = pressed).setTooltipsSupplier(pressed -> List.of(Component.translatable("gtocore.machine.batch_processing").append("[").append(Component.translatable(pressed ? "gtocore.machine.on" : "gtocore.machine.off").append("]")))));
        }
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
    public void gTOCore$setOCLimit(int number) {
        ocLimit = number;
    }

    @Override
    public int gTOCore$getOCLimit() {
        return ocLimit;
    }

    public boolean isBatchProcessing() {
        return this.batchProcessing;
    }
}
