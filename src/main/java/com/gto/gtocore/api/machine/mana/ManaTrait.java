package com.gto.gtocore.api.machine.mana;

import com.gto.gtocore.api.capability.IManaContainer;
import com.gto.gtocore.api.capability.recipe.ManaRecipeCapability;
import com.gto.gtocore.api.machine.trait.MultiblockTrait;

import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.IRecipeHandler;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableMultiblockMachine;

import net.minecraft.network.chat.Component;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;

@Getter
public class ManaTrait extends MultiblockTrait {

    private final Set<IManaContainer> manaContainers = new ObjectOpenHashSet<>();

    public ManaTrait(WorkableMultiblockMachine machine) {
        super(machine);
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        manaContainers.clear();
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        if (((IManaMultiblock) machine).isGeneratorMana()) {
            List<IRecipeHandler<?>> capabilities = getMachine().getCapabilitiesProxy().get(IO.OUT, ManaRecipeCapability.CAP);
            if (capabilities != null) {
                for (IRecipeHandler<?> handler : capabilities) {
                    if (handler instanceof IManaContainer container) {
                        manaContainers.add(container);
                    }
                }
            }
        } else {
            List<IRecipeHandler<?>> capabilities = getMachine().getCapabilitiesProxy().get(IO.IN, ManaRecipeCapability.CAP);
            if (capabilities != null) {
                for (IRecipeHandler<?> handler : capabilities) {
                    if (handler instanceof IManaContainer container) {
                        manaContainers.add(container);
                    }
                }
            }
        }
    }

    public long getCurrentMana() {
        return manaContainers.stream().mapToLong(IManaContainer::getCurrentMana).sum();
    }

    public long getMaxMana() {
        return manaContainers.stream().mapToLong(IManaContainer::getMaxMana).sum();
    }

    public long getMaxConsumption() {
        return manaContainers.stream().mapToLong(IManaContainer::getMaxConsumption).sum();
    }

    @Override
    public void customText(@NotNull List<Component> textList) {
        super.customText(textList);
        textList.add(Component.translatable("gtocore.machine.mana_stored", getCurrentMana(), getMaxMana()));
        textList.add(Component.translatable("gtocore.machine.mana_consumption", getMaxConsumption()));
    }
}
