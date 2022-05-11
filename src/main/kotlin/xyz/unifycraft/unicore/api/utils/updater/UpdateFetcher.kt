package xyz.unifycraft.unicore.api.utils.updater

interface UpdateFetcher {
    /**
     * Makes checks if an update is
     * available.
     */
    suspend fun check(updater: Updater, mod: UpdaterMod)
    /**
     * @return Whether an update is available or not.
     */
    fun hasUpdate(): Boolean
}