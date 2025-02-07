package com.gto.gtocore.api.recipe;

import com.gto.gtocore.GTOCore;
import com.gto.gtocore.api.capability.recipe.ManaRecipeCapability;
import com.gto.gtocore.common.recipe.condition.GravityCondition;

import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.RecipeCapability;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.recipe.RecipeCondition;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.api.recipe.ingredient.FluidIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.IntCircuitIngredient;
import com.gregtechceu.gtceu.api.recipe.ingredient.SizedIngredient;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.builder.GTRecipeBuilder;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings({ "UnusedReturnValue" })
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public final class GTORecipeBuilder extends GTRecipeBuilder {

    private GTORecipeBuilder(ResourceLocation id, GTRecipeType recipeType) {
        super(id, recipeType);
    }

    public static GTORecipeBuilder of(ResourceLocation id, GTRecipeType recipeType) {
        return new GTORecipeBuilder(id, recipeType);
    }

    public static GTORecipeBuilder of(String id, GTRecipeType recipeType) {
        return of(GTOCore.id(id), recipeType);
    }

    public static GTORecipeBuilder of(GTRecipeType recipeType, ResourceLocation id) {
        return of(id, recipeType);
    }

    public static GTORecipeBuilder of(GTRecipeType recipeType, String id) {
        return of(id, recipeType);
    }

    public static GTORecipeBuilder ofRaw() {
        return of("raw", GTRecipeTypes.DUMMY_RECIPES);
    }

    @Override
    public GTORecipeBuilder addCondition(RecipeCondition condition) {
        conditions.add(condition);
        return this;
    }

    @Override
    public <T> GTORecipeBuilder input(RecipeCapability<T> capability, T obj) {
        (perTick ? tickInput : input).computeIfAbsent(capability, c -> new ArrayList<>()).add(new Content(capability.of(obj), chance, maxChance, tierChanceBoost, slotName, uiName));
        return this;
    }

    @SafeVarargs
    @Override
    public final <T> GTORecipeBuilder input(RecipeCapability<T> capability, T... obj) {
        (perTick ? tickInput : input).computeIfAbsent(capability, c -> new ArrayList<>()).addAll(Arrays.stream(obj).map(capability::of).map(o -> new Content(o, chance, maxChance, tierChanceBoost, slotName, uiName)).toList());
        return this;
    }

    @Override
    public <T> GTORecipeBuilder output(RecipeCapability<T> capability, T obj) {
        (perTick ? tickOutput : output).computeIfAbsent(capability, c -> new ArrayList<>()).add(new Content(capability.of(obj), chance, maxChance, tierChanceBoost, slotName, uiName));
        return this;
    }

    @SafeVarargs
    @Override
    public final <T> GTORecipeBuilder output(RecipeCapability<T> capability, T... obj) {
        (perTick ? tickOutput : output).computeIfAbsent(capability, c -> new ArrayList<>()).addAll(Arrays.stream(obj).map(capability::of).map(o -> new Content(o, chance, maxChance, tierChanceBoost, slotName, uiName)).toList());
        return this;
    }

    @Override
    public <T> GTORecipeBuilder inputs(RecipeCapability<T> capability, Object obj) {
        (perTick ? tickInput : input).computeIfAbsent(capability, c -> new ArrayList<>()).add(new Content(capability.of(obj), chance, maxChance, tierChanceBoost, slotName, uiName));
        return this;
    }

    @Override
    public <T> GTORecipeBuilder inputs(RecipeCapability<T> capability, Object... obj) {
        (perTick ? tickInput : input).computeIfAbsent(capability, c -> new ArrayList<>()).addAll(Arrays.stream(obj).map(capability::of).map(o -> new Content(o, chance, maxChance, tierChanceBoost, slotName, uiName)).toList());
        return this;
    }

    @Override
    public <T> GTORecipeBuilder outputs(RecipeCapability<T> capability, Object obj) {
        (perTick ? tickOutput : output).computeIfAbsent(capability, c -> new ArrayList<>()).add(new Content(capability.of(obj), chance, maxChance, tierChanceBoost, slotName, uiName));
        return this;
    }

    @Override
    public <T> GTORecipeBuilder outputs(RecipeCapability<T> capability, Object... obj) {
        (perTick ? tickOutput : output).computeIfAbsent(capability, c -> new ArrayList<>()).addAll(Arrays.stream(obj).map(capability::of).map(o -> new Content(o, chance, maxChance, tierChanceBoost, slotName, uiName)).toList());
        return this;
    }

    @Override
    public GTORecipeBuilder circuitMeta(int configuration) {
        return (GTORecipeBuilder) notConsumable(IntCircuitIngredient.circuitInput(configuration));
    }

    @Override
    public GTORecipeBuilder inputItems(Item input) {
        return inputItems(new ItemStack(input));
    }

    @Override
    public GTORecipeBuilder inputItems(ItemStack input) {
        return input(ItemRecipeCapability.CAP, SizedIngredient.create(input));
    }

    @Override
    public GTORecipeBuilder inputItems(Ingredient inputs) {
        return input(ItemRecipeCapability.CAP, inputs);
    }

    @Override
    public GTORecipeBuilder inputItems(TagPrefix orePrefix, Material material) {
        return inputItems(orePrefix, material, 1);
    }

    @Override
    public GTORecipeBuilder inputItems(TagPrefix orePrefix, Material material, int count) {
        return (GTORecipeBuilder) super.inputItems(orePrefix, material, count);
    }

    @Override
    public GTORecipeBuilder outputItems(Item input) {
        return outputItems(new ItemStack(input));
    }

    @Override
    public GTORecipeBuilder outputItems(ItemStack output) {
        return output(ItemRecipeCapability.CAP, SizedIngredient.create(output));
    }

    @Override
    public GTORecipeBuilder outputItems(TagPrefix orePrefix, Material material) {
        return outputItems(orePrefix, material, 1);
    }

    @Override
    public GTORecipeBuilder outputItems(TagPrefix orePrefix, Material material, int count) {
        return (GTORecipeBuilder) super.outputItems(orePrefix, material, count);
    }

    @Override
    public GTORecipeBuilder inputFluids(FluidStack input) {
        return input(FluidRecipeCapability.CAP, FluidIngredient.of(input));
    }

    @Override
    public GTORecipeBuilder inputFluids(FluidStack... inputs) {
        return input(FluidRecipeCapability.CAP, Arrays.stream(inputs).map(FluidIngredient::of).toArray(FluidIngredient[]::new));
    }

    @Override
    public GTORecipeBuilder inputFluids(FluidIngredient... inputs) {
        return input(FluidRecipeCapability.CAP, inputs);
    }

    @Override
    public GTORecipeBuilder outputFluids(FluidStack output) {
        return output(FluidRecipeCapability.CAP, FluidIngredient.of(output));
    }

    @Override
    public GTORecipeBuilder outputFluids(FluidStack... outputs) {
        return output(FluidRecipeCapability.CAP, Arrays.stream(outputs).map(FluidIngredient::of).toArray(FluidIngredient[]::new));
    }

    @Override
    public GTORecipeBuilder outputFluids(FluidIngredient... outputs) {
        return output(FluidRecipeCapability.CAP, outputs);
    }

    @Override
    public GTORecipeBuilder EUt(long eu) {
        var lastPerTick = perTick;
        perTick = true;
        if (eu > 0) {
            inputEU(eu);
        } else if (eu < 0) {
            outputEU(-eu);
        }
        perTick = lastPerTick;
        return this;
    }

    @Override
    public GTORecipeBuilder duration(final int duration) {
        this.duration = duration;
        return this;
    }

    public GTORecipeBuilder gravity(boolean noGravity) {
        return addCondition(new GravityCondition(noGravity));
    }

    public GTORecipeBuilder MANAt(int mana) {
        var lastPerTick = perTick;
        perTick = true;
        if (mana > 0) {
            input(ManaRecipeCapability.CAP, mana);
        } else {
            output(ManaRecipeCapability.CAP, -mana);
        }
        perTick = lastPerTick;
        return this;
    }
}
