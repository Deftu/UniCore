package xyz.unifycraft.unicore.api.utils.language.fetchers

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import okhttp3.Request
import xyz.deftu.quicksocket.common.utils.QuickSocketJsonHandler
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.language.Language
import xyz.unifycraft.unicore.api.utils.language.LanguageFetcher

class JsonLanguageFetcher : LanguageFetcher {
    override val supportsMultipleFiles = false
    override suspend fun retrieveMultiple(path: String): List<Language?> {
        return UniCore.getHttpRequester().request(
            Request.Builder()
            .get()
            .url(path)
            .build()) {
            it.body?.let { body ->
                val str = body.string()
                if (!QuickSocketJsonHandler.parser.isValidJson(str)) return@let null
                val json = QuickSocketJsonHandler.parser.parse(str)
                if (!json.isJsonObject) return@let null
                val jsonObject = json.asJsonObject
                val languages = jsonObject.entrySet().map { entry ->
                    if (entry.value !is JsonObject) throw IllegalArgumentException("Expected JsonObject, received ${entry.value::class.java.simpleName}.")
                    val innerObject = entry.value.asJsonObject
                    /*Language(entry.key, innerObject.entrySet().map { innerEntry ->
                        if (entry.value !is JsonPrimitive) throw IllegalArgumentException("Expected JsonPrimitive, received ${entry.value::class.java.simpleName}.")
                        innerEntry.key to innerEntry.value.asJsonPrimitive.asString
                    })*/
                    null
                }
                languages
            } ?: emptyList()
        }
    }
}