package com.gtocore.common.machine.multiblock.noenergy;

import com.gtocore.common.machine.multiblock.part.DroneHatchPartMachine;

import com.gtolib.api.capability.IIWirelessInteractor;
import com.gtolib.api.machine.multiblock.NoEnergyMultiblockMachine;
import com.gtolib.api.machine.trait.CustomRecipeLogic;
import com.gtolib.api.misc.Drone;
import com.gtolib.api.recipe.Recipe;
import com.gtolib.api.recipe.RecipeBuilder;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.trait.RecipeLogic;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DroneControlCenterMachine extends NoEnergyMultiblockMachine {

    public static final Map<ResourceLocation, Set<DroneControlCenterMachine>> NETWORK = new Object2ObjectOpenHashMap<>();

    private DroneHatchPartMachine droneHatchPartMachine;

    public DroneControlCenterMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    @Nullable
    public Drone getFirstUsableDrone(BlockPos pos) {
        if (droneHatchPartMachine == null) return null;
        return droneHatchPartMachine.getFirstUsableDrone(getPos(), pos);
    }

    @Override
    public void onPartScan(@NotNull IMultiPart part) {
        super.onPartScan(part);
        if (droneHatchPartMachine == null && part instanceof DroneHatchPartMachine machine) {
            droneHatchPartMachine = machine;
        }
    }

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();
        IIWirelessInteractor.addToNet(NETWORK, this);
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        droneHatchPartMachine = null;
        IIWirelessInteractor.removeFromNet(NETWORK, this);
    }

    @Override
    public void onUnload() {
        super.onUnload();
        IIWirelessInteractor.removeFromNet(NETWORK, this);
    }

    @Override
    public void customText(@NotNull List<Component> textList) {
        super.customText(textList);
        if (droneHatchPartMachine != null) {
            textList.add(Component.translatable("tooltip.jade.state", ""));
            for (int i = 0; i < droneHatchPartMachine.getSize(); i++) {
                MutableComponent component = Component.translatable("side_config.ad_astra.slots").append(" " + i + ": ");
                Drone drone = droneHatchPartMachine.getDrone(i);
                if (drone == null) {
                    textList.add(component.append(Component.translatable("tooltip.jade.empty")));
                } else {
                    if (drone.isWork()) {
                        textList.add(component.append(Component.translatable(drone.getWorkState()).withStyle(ChatFormatting.AQUA)));
                    } else {
                        textList.add(component.append(Component.translatable("gtceu.multiblock.idling")));
                    }
                }
            }
        }
    }

    @Nullable
    private Recipe getRecipe() {
        if (droneHatchPartMachine == null) return null;
        return RecipeBuilder.ofRaw().duration(20).buildRawRecipe();
    }

    @Override
    protected @NotNull RecipeLogic createRecipeLogic(Object @NotNull... args) {
        return new CustomRecipeLogic(this, this::getRecipe, true);
    }
}
