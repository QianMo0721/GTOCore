package com.gtocore.common.data;

import com.gtocore.common.recipe.custom.ClearFluidContainer;
import com.gtocore.common.recipe.custom.ClearFluidContainerRecipeSerializer;

import com.gtolib.GTOCore;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class GTORecipeSerializers {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, GTOCore.MOD_ID);

    public static final RegistryObject<RecipeSerializer<ClearFluidContainer>> CLEAR_FLUID_CONTAINER = RECIPE_SERIALIZERS.register("clear_fluid_container", () -> ClearFluidContainerRecipeSerializer.INSTANCE);

    public static void init(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
