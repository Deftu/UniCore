package xyz.unifycraft.unicore.api.mixins

import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins
import xyz.unifycraft.unicore.api.UniCore

object UniCoreInitializer {
    private var initialized = false
    @JvmStatic fun initialize() {
        if (initialized) return
        MixinBootstrap.init()
        Mixins.addConfiguration("mixins.unicore.json")
        UniCore.getLogger().info("${UniCore.getName()} has been initialized!")
        initialized = true
    }
}