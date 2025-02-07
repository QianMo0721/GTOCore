package com.gto.gtocore.mixin.avaritia;

import net.minecraftforge.eventbus.api.SubscribeEvent;

import committee.nova.mods.avaritia.api.init.event.RegisterRecipesEvent;
import committee.nova.mods.avaritia.init.handler.InternalRecipeHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(InternalRecipeHandler.class)
public final class InternalRecipeHandlerMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    @SubscribeEvent
    public static void onRegisterRecipes(RegisterRecipesEvent event) {}
}
