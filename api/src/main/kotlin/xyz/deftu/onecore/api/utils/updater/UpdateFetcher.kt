package xyz.deftu.onecore.api.utils.updater

interface UpdateFetcher {
    suspend fun check(updater: Updater, mod: UpdaterMod)
    fun hasUpdate(): Boolean
}