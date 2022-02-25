package xyz.unifycraft.unicore.mixins.client;

import xyz.deftu.unicore.api.UniCore;
import xyz.deftu.unicore.api.events.InitializationEvent;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.unicraft.unicore.api.UniCore;
import xyz.unicraft.unicore.api.events.InitializationEvent;
import xyz.unifycraft.unicore.api.UniCore;
import xyz.unifycraft.unicore.api.events.InitializationEvent;

import java.io.File;

@Mixin({Minecraft.class})
public class MinecraftMixin {
    @Shadow @Final public File mcDataDir;

    @Inject(method = "startGame", at = @At("HEAD"))
    private void onGamePreStarted(CallbackInfo ci) {
        if (!UniCore.initialize()) throw new IllegalStateException("Was already initialized. How?");
    }

    @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EffectRenderer;<init>(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;)V"))
    private void onGameStarted(CallbackInfo ci) {
        UniCore.getEventBus().post(new InitializationEvent(mcDataDir));
    }
}