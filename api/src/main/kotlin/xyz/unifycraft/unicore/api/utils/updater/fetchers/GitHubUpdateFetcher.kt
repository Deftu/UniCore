package xyz.unifycraft.unicore.api.utils.updater.fetchers

import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.updater.UpdateFetcher
import xyz.unifycraft.unicore.api.utils.updater.UpdateVersion
import xyz.unifycraft.unicore.api.utils.updater.Updater
import xyz.unifycraft.unicore.api.utils.updater.UpdaterMod
import okhttp3.Request

object GitHubUpdateFetcher : UpdateFetcher {
    private var hasUpdate = false

    override suspend fun check(updater: Updater, mod: UpdaterMod) {
        try {
            val request = Request.Builder()
                .url(mod.path)
                .build()
            val response = UniCore.getHttpRequester().request(request) {
                it.body?.let { body ->
                    val raw = UniCore.getJsonHelper().parse(body.string())
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
            UniCore.getNotifications().post(UniCore.getName(), "Failed to check for ${mod.name} updates.")
        }
    }

    override fun hasUpdate() = hasUpdate
}