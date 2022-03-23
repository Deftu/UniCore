package xyz.unifycraft.unicore.api

import org.spongepowered.asm.launch.MixinBootstrap
import org.spongepowered.asm.mixin.Mixins

object UniCoreInitializer {
    @JvmStatic fun initialize() {
        MixinBootstrap.init()
        Mixins.addConfiguration("mixins.unicore.json")
        UniCore.getLogger().info("${UniCore.getName()} has been initialized!")
    }
}