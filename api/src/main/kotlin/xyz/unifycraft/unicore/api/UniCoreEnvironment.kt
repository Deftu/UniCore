package xyz.unifycraft.unicore.api

object UniCoreEnvironment {
    val connectionUri: String
        get() = System.getProperty("unicore.cloud.uri", "ws://localhost:3500")
    val deleterRepo: String
        get() = System.getProperty("unicore.deleter.repo", "Deftu/Deleter")
}