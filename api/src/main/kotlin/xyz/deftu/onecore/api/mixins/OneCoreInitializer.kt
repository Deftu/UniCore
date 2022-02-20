package xyz.deftu.onecore.api.mixins

import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins

object OneCoreInitializer {
    @JvmStatic fun initialize() {
        MixinBootstrap.init()
        Mixins.addConfiguration("mixins.onecore.json")
    }
}