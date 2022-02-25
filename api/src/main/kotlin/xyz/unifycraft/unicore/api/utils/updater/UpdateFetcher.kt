package xyz.unifycraft.unicore.api.utils.updater

interface UpdateFetcher {
    suspend fun check(updater: Updater, mod: UpdaterMod)
    fun hasUpdate(): Boolean
}