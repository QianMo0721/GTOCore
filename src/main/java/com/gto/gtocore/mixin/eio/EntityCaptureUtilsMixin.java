package com.gto.gtocore.mixin.eio;

import net.minecraft.resources.ResourceLocation;

import com.enderio.base.common.util.EntityCaptureUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(EntityCaptureUtils.class)
public final class EntityCaptureUtilsMixin {

    /**
     * @author .
     * @reason .
     */
    @Overwrite(remap = false)
    public static List<ResourceLocation> getCapturableEntities() {
        return List.of();
    }
}
