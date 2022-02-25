package xyz.unifycraft.unicore.api.utils.updater.fetchers

import xyz.deftu.unicore.api.UniCore
import xyz.deftu.unicore.api.utils.updater.UpdateFetcher
import xyz.deftu.unicore.api.utils.updater.Updater
import xyz.deftu.unicore.api.utils.updater.UpdaterMod
import okhttp3.Request

class JsonUpdateFetcher(
    val versionFieldName: String,
    val checksumFieldName: String? = null
) : UpdateFetcher {
    // TODO 2022/02/13 - THIS IS NOT VERY STABLE OR RELIABLE.
    private var hasUpdate = false

    override suspend fun check(updater: Updater, mod: UpdaterMod) {
        val request = Request.Builder()
            .url(mod.path)
            .build()
        val response = UniCore.getHttpRequester().request(request) {
            it.body?.let { body ->
                val raw = UniCore.getJsonHelper().parse(body.string())
                if (!raw.isJsonObject) return@let
                val json = raw.asJsonObject
                if (!json.has(versionFieldName)) return@let
                val version = json.get(versionFieldName).toString()
                if (version != mod.version) hasUpdate = true
            } ?: UniCore.getNotifications().post(UniCore.getName(), "Failed to check for ${mod.name} updates.")
        }
    }

    override fun hasUpdate() = hasUpdate
}