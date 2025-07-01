package com.gtocore.common.machine.trait;

import com.gtocore.common.machine.multiblock.part.ae.MEPatternBufferPartMachine;

import com.gtolib.api.machine.trait.ExtendedRecipeHandlerList;
import com.gtolib.api.machine.trait.IExtendRecipeHandler;
import com.gtolib.api.recipe.Recipe;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.machine.multiblock.part.MultiblockPartMachine;
import com.gregtechceu.gtceu.api.machine.trait.IRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.machine.trait.NotifiableRecipeHandlerTrait;
import com.gregtechceu.gtceu.api.machine.trait.RecipeHandlerList;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import it.unimi.dsi.fastutil.objects.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class InternalSlotRecipeHandler {

    private final List<RecipeHandlerList> slotHandlers;

    public InternalSlotRecipeHandler(MEPatternBufferPartMachine buffer, MEPatternBufferPartMachine.InternalSlot[] slots) {
        this.slotHandlers = new ArrayList<>(slots.length);
        for (int i = 0; i < slots.length; i++) {
            var rhl = new SlotRHL(buffer, slots[i], i);
            slotHandlers.add(rhl);
        }
    }

    public static abstract class AbstractRHL extends ExtendedRecipeHandlerList {

        public RecipeHandlerList rhl = this;
        private final MEPatternBufferPartMachine.InternalSlot slot;

        AbstractRHL(IO handlerIO, MEPatternBufferPartMachine.InternalSlot slot, MultiblockPartMachine part) {
            super(handlerIO, part);
            this.slot = slot;
            this.slot.rhl = this;
        }

        @Override
        public boolean isDistinct() {
            return true;
        }

        @Override
        public void setDistinct(boolean ignored, boolean notify) {}

        public boolean handleRecipeContent(GTRecipe recipe, Object2ObjectOpenHashMap<RecipeCapability<?>, List<Object>> contents, boolean simulate) {
            if (slot.isEmpty()) return false;
            byte success = 0;
            for (ObjectIterator<Object2ObjectMap.Entry<RecipeCapability<?>, List<Object>>> it = contents.object2ObjectEntrySet().fastIterator(); it.hasNext();) {
                Map.Entry<RecipeCapability<?>, List<Object>> entry = it.next();
                List left = entry.getValue();
                var handlerList = getCapability(entry.getKey());
                for (var handler : handlerList) {
                    left = handler.handleRecipe(IO.IN, recipe, left, simulate);
                    if (left == null) {
                        success++;
                        break;
                    }
                }
            }
            if (success == contents.size()) {
                slot.setRecipe(((Recipe) recipe).getRootRecipe());
                return true;
            }
            return false;
        }
    }

    static final class SlotRHL extends AbstractRHL {

        final IRecipeHandlerTrait<Ingredient> itemRecipeHandler;
        final IRecipeHandlerTrait<FluidIngredient> fluidRecipeHandler;

        private SlotRHL(MEPatternBufferPartMachine buffer, MEPatternBufferPartMachine.InternalSlot slot, int idx) {
            super(IO.IN, slot, buffer);
            itemRecipeHandler = new SlotItemRecipeHandler(buffer, slot, idx);
            fluidRecipeHandler = new SlotFluidRecipeHandler(buffer, slot, idx);
            addHandlers(itemRecipeHandler, fluidRecipeHandler, buffer.getCircuitInventory(), buffer.getShareInventory(), buffer.getShareTank());
        }
    }

    private static final class SlotItemRecipeHandler extends NotifiableRecipeHandlerTrait<Ingredient> implements IExtendRecipeHandler {

        private final MEPatternBufferPartMachine.InternalSlot slot;

        private SlotItemRecipeHandler(MEPatternBufferPartMachine buffer, MEPatternBufferPartMachine.InternalSlot slot, int index) {
            super(buffer);
            this.slot = slot;
            slot.setOnContentsChanged(this::notifyListeners);
        }

        @Override
        public List<Ingredient> handleRecipe(IO io, GTRecipe recipe, List left, boolean simulate) {
            if (slot.itemInventory.isEmpty()) return left;
            return handleRecipeInner(io, recipe, new ArrayList<>(left), simulate);
        }

        @Override
        public List<Ingredient> handleRecipeInner(IO io, GTRecipe recipe, List<Ingredient> left, boolean simulate) {
            return slot.handleItemInternal(left, simulate);
        }

        @Override
        @NotNull
        public List<Object> getContents() {
            return (List) slot.getItems();
        }

        @Override
        public double getTotalContentAmount() {
            double a = 0;
            for (ObjectIterator<Object2LongMap.Entry<ItemStack>> it = slot.itemInventory.object2LongEntrySet().fastIterator(); it.hasNext();) {
                a += it.next().getLongValue();
            }
            return a;
        }

        @Override
        public int getSize() {
            return 81;
        }

        @Override
        public boolean isDistinct() {
            return true;
        }

        @Override
        public RecipeCapability<Ingredient> getCapability() {
            return ItemRecipeCapability.CAP;
        }

        @Override
        public IO getHandlerIO() {
            return IO.IN;
        }

        @Override
        public Object2LongOpenCustomHashMap<ItemStack> getItemMap() {
            return slot.itemInventory.isEmpty() ? null : slot.itemInventory;
        }
    }

    private static final class SlotFluidRecipeHandler extends NotifiableRecipeHandlerTrait<FluidIngredient> implements IExtendRecipeHandler {

        private final MEPatternBufferPartMachine.InternalSlot slot;

        private SlotFluidRecipeHandler(MEPatternBufferPartMachine buffer, MEPatternBufferPartMachine.InternalSlot slot, int index) {
            super(buffer);
            this.slot = slot;
            slot.setOnContentsChanged(this::notifyListeners);
        }

        @Override
        public List<FluidIngredient> handleRecipe(IO io, GTRecipe recipe, List left, boolean simulate) {
            if (slot.fluidInventory.isEmpty()) return left;
            return handleRecipeInner(io, recipe, new ArrayList<>(left), simulate);
        }

        @Override
        public List<FluidIngredient> handleRecipeInner(IO io, GTRecipe recipe, List<FluidIngredient> left, boolean simulate) {
            return slot.handleFluidInternal(left, simulate);
        }

        @Override
        @NotNull
        public List<Object> getContents() {
            return (List) slot.getFluids();
        }

        @Override
        public double getTotalContentAmount() {
            double a = 0;
            for (ObjectIterator<Object2LongMap.Entry<FluidStack>> it = slot.fluidInventory.object2LongEntrySet().fastIterator(); it.hasNext();) {
                a += it.next().getLongValue();
            }
            return a;
        }

        @Override
        public int getSize() {
            return 81;
        }

        @Override
        public boolean isDistinct() {
            return true;
        }

        @Override
        public RecipeCapability<FluidIngredient> getCapability() {
            return FluidRecipeCapability.CAP;
        }

        @Override
        public IO getHandlerIO() {
            return IO.IN;
        }

        @Override
        public Object2LongOpenHashMap<FluidStack> getFluidMap() {
            return slot.fluidInventory.isEmpty() ? null : slot.fluidInventory;
        }
    }

    public List<RecipeHandlerList> getSlotHandlers() {
        return this.slotHandlers;
    }
}
