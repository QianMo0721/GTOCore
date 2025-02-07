package com.gto.gtocore.mixin.kubejs;

import com.gto.gtocore.integration.kjs.EmptyScriptManager;

import net.minecraft.server.packs.resources.ResourceManager;

import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.KubeJSCommon;
import dev.latvian.mods.kubejs.script.ScriptManager;
import dev.latvian.mods.kubejs.script.ScriptType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;
import java.util.function.Supplier;

@Mixin(KubeJS.class)
public class KubeJSMixin {

    @Shadow(remap = false)
    private static ScriptManager startupScriptManager;

    @Shadow(remap = false)
    private static ScriptManager clientScriptManager;

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/architectury/utils/EnvExecutor;getEnvSpecific(Ljava/util/function/Supplier;Ljava/util/function/Supplier;)Ljava/lang/Object;"), index = 0, remap = false)
    private Supplier<Supplier<?>> modifyArg(Supplier<Supplier<?>> client) {
        return () -> KubeJSCommon::new;
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/script/ScriptManager;reload(Lnet/minecraft/server/packs/resources/ResourceManager;)V"), remap = false)
    private void script(ScriptManager instance, ResourceManager resourceManager) {
        startupScriptManager = new EmptyScriptManager(ScriptType.STARTUP);
        clientScriptManager = new EmptyScriptManager(ScriptType.CLIENT);
    }

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/level/KubeJSWorldEventHandler;init()V"), remap = false)
    private void world() {}

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/player/KubeJSPlayerEventHandler;init()V"), remap = false)
    private void player() {}

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/entity/KubeJSEntityEventHandler;init()V"), remap = false)
    private void entity() {}

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/block/KubeJSBlockEventHandler;init()V"), remap = false)
    private void block() {}

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/item/KubeJSItemEventHandler;init()V"), remap = false)
    private void item() {}

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/server/KubeJSServerEventHandler;init()V"), remap = false)
    private void server() {}

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/gui/KubeJSMenu;init()V"), remap = false)
    private void gui() {}

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Ldev/latvian/mods/kubejs/script/data/GeneratedResourcePack;scanForInvalidFiles(Ljava/lang/String;Ljava/nio/file/Path;)V"), remap = false)
    private void scan(String c, Path fileName) {}

    @Inject(method = "loadComplete", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;<init>(Ljava/lang/Runnable;)V"), remap = false, cancellable = true)
    private void loadComplete(CallbackInfo ci) {
        ci.cancel();
    }
}
