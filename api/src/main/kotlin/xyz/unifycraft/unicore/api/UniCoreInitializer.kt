package xyz.unifycraft.unicore.api

object UniCoreInitializer {
    @JvmStatic fun initialize() {
        UniCore.getLogger().info("${UniCore.getName()} has been initialized!")
    }
}