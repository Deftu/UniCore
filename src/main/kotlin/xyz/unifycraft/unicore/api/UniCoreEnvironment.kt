package xyz.unifycraft.unicore.api

object UniCoreEnvironment {
    val deleterRepo: String
        get() = System.getProperty("unicore.deleter.repo", "Deftu/Deleter")
    val cloudConnectionUri: String
        get() = System.getProperty("unicore.cloud.connection.uri", "ws://localhost:8080")
}