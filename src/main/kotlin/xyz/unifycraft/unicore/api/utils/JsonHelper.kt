package xyz.unifycraft.unicore.api.utils

import com.google.gson.Gson
import xyz.unifycraft.unicore.api.UniCore
import com.google.gson.JsonElement
import com.google.gson.JsonParser

/**
 * A utility class making
 * JSON parsing easier and
 * more accessible cross-version.
 */
interface JsonHelper {
    val gson: Gson
    //#if MC<=11202
    val jsonParser: JsonParser
    //#else

    /**
     * @return The string provided as a JSON element.
     */
    fun parse(json: String): JsonElement
}

/**
 * @see [JsonHelper.parse]
 * @return the string as a [JsonElement].
 */
fun String.toJsonSafe() = runCatching { return@runCatching toJson() }

/**
 * @see [JsonHelper.parse]
 * @return the string as a [JsonElement].
 */
fun String.toJson() = UniCore.getJsonHelper().parse(this)