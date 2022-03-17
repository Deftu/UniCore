package xyz.unifycraft.unicore.api.utils.language.fetchers

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import okhttp3.Request
import xyz.deftu.quicksocket.common.utils.QuickSocketJsonHandler
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.language.Language
import xyz.unifycraft.unicore.api.utils.language.LanguageFetcher

class UrlLanguageFetcher : LanguageFetcher {
    override val supportsMultipleFiles = true
    override suspend fun retrieveSingular(path: String): Language? {
        return UniCore.getHttpRequester().request(Request.Builder()
            .get()
            .url(path)
            .build()) {
            it.body?.let { body ->
                val str = body.string()
                if (!QuickSocketJsonHandler.parser.isValidJson(str)) return@let null
                val json = QuickSocketJsonHandler.parser.parse(str)
                if (!json.isJsonObject) return@let null
                val jsonObject = json.asJsonObject
                Language(path.substringAfterLast("/"), serializeToPairs(jsonObject))
            }
        }
    }

    private fun serializeToPairs(jsonObject: JsonObject): List<Pair<String, String>> =
        jsonObject.entrySet().map(this::serializeToPair)
    private fun serializeToPair(entry: Map.Entry<String, JsonElement>): Pair<String, String> {
        if (entry.value !is JsonPrimitive) throw IllegalArgumentException("Expected JsonPrimitive, received ${entry.value::class.java.simpleName}.")
        return entry.key to entry.value.asJsonPrimitive.asString
    }
}