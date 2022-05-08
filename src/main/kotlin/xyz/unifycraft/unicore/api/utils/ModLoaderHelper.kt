package xyz.unifycraft.unicore.api.utils

interface ModLoaderHelper {
    /**
     * @return Whether the mod is loaded or not.
     */
    fun isModLoaded(id: String): Boolean

    /**
     * @return Whether mod is loaded or not.
     */
    fun isModLoaded(id: String, version: String): Boolean

    /**
     * @return Whether we are in a developer environment or not.
     */
    fun isDevelopmentEnvironment(): Boolean
}
