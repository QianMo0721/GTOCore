package com.gto.gtocore.mixin.ae2.gui;

import com.gto.gtocore.integration.ae2.GTOSettings;
import com.gto.gtocore.integration.ae2.IPatternProviderMenu;

import appeng.api.config.YesNo;
import appeng.helpers.patternprovider.PatternProviderLogic;
import appeng.menu.guisync.GuiSync;
import appeng.menu.implementations.PatternProviderMenu;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PatternProviderMenu.class)
public class PatternProviderMenuMixin implements IPatternProviderMenu {

    @Shadow(remap = false)
    @Final
    protected PatternProviderLogic logic;

    @Unique
    @GuiSync(8)
    private YesNo gtocore$enhancedblockingmode = YesNo.NO;

    @Inject(method = "broadcastChanges", at = @At(value = "INVOKE", target = "Lappeng/helpers/patternprovider/PatternProviderLogic;getUnlockStack()Lappeng/api/stacks/GenericStack;", remap = false))
    private void broadcastChanges(CallbackInfo ci) {
        gtocore$enhancedblockingmode = logic.getConfigManager().getSetting(GTOSettings.ENHANCED_BLOCKING_MODE);
    }

    @Override
    public YesNo gtocore$getEnhancedBlockingMode() {
        return gtocore$enhancedblockingmode;
    }
}
