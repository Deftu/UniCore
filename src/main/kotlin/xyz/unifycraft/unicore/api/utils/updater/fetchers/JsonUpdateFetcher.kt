package xyz.unifycraft.unicore.api.utils.updater.fetchers

import com.google.gson.JsonElement
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.updater.UpdateFetcher
import xyz.unifycraft.unicore.api.utils.updater.Updater
import xyz.unifycraft.unicore.api.utils.updater.UpdaterMod
import okhttp3.Request
import org.apache.commons.codec.digest.DigestUtils
import xyz.unifycraft.unicore.api.utils.updater.UpdateVersion
import java.io.File
import java.io.FileInputStream

class JsonUpdateFetcher(
    val versionFieldName: String,
    val checksumFieldName: String? = null
) : UpdateFetcher {
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
                val version = json[versionFieldName].toString()
                var value = UpdateVersion(version, mod.path) > UpdateVersion(mod.version)
                if (checksumFieldName != null && json.has(checksumFieldName)) value = value && checkChecksum(mod.file, json[checksumFieldName])
                hasUpdate = value
            } ?: UniCore.getNotifications().post(UniCore.getName(), "Failed to check for ${mod.name} updates.")
        }
    }

    private fun checkChecksum(file: File, checksumField: JsonElement) =
        checksumField.isJsonPrimitive && DigestUtils.md5Hex(FileInputStream(file)) == checksumField.asJsonPrimitive.asString

    override fun hasUpdate() = hasUpdate
}