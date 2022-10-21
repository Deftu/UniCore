package xyz.unifycraft.unicore.api

object UniCoreEnvironment {
    val deleterRepo = System.getProperty("unicore.deleter.repo", "Deftu/Deleter")
    val cloudConnectionUri = System.getProperty("unicore.cloud.connection.uri", "ws://localhost:8080")
}