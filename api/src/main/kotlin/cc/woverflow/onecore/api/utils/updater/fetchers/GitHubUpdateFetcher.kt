package cc.woverflow.onecore.api.utils.updater.fetchers

import cc.woverflow.onecore.api.OneCore
import cc.woverflow.onecore.api.utils.updater.UpdateFetcher
import cc.woverflow.onecore.api.utils.updater.UpdateVersion
import cc.woverflow.onecore.api.utils.updater.Updater
import cc.woverflow.onecore.api.utils.updater.UpdaterMod
import okhttp3.Request

object GitHubUpdateFetcher : UpdateFetcher {
    private var hasUpdate = false

    override suspend fun check(updater: Updater, mod: UpdaterMod) {
        try {
            val request = Request.Builder()
                .url(mod.path)
                .build()
            val response = OneCore.getHttpRequester().request(request) {
                it.body?.let { body ->
                    val raw = OneCore.getJsonHelper().parse(body.string())
                    if (!raw.isJsonObject) return@let
                    val json = raw.asJsonObject
                    if (UpdateVersion(
                            json["tag_name"].asString.substringAfter("v"),
                            json["assets"].asJsonArray[0].asJsonObject["browser_download_url"].asString
                        ) > UpdateVersion(mod.version)
                    ) {
                        hasUpdate = true
                    }
                } ?: throw RuntimeException()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            OneCore.getNotifications().post(OneCore.getName(), "Failed to check for ${mod.name} updates.")
        }
    }

    override fun hasUpdate() = hasUpdate
}