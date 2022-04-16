package xyz.unifycraft.unicore.api

object UniCoreEnvironment {
    val deleterRepo: String
        get() = System.getProperty("unicore.deleter.repo", "Deftu/Deleter")
}