package xyz.unifycraft.unicore.api.utils

import com.google.gson.Gson
import xyz.unifycraft.unicore.api.UniCore
import com.google.gson.JsonElement
import com.google.gson.JsonParser

interface JsonHelper {
    val gson: Gson
    //#if MC<=11202
    val jsonParser: JsonParser
    //#else

    fun parse(json: String): JsonElement
}

/**
 * Return the string provided as a [JsonElement].
 */
fun String.toJsonSafe() = runCatching { return@runCatching this.toJson() }

/**
 * Return the string provided as a [JsonElement]
 *
 * @return string as a [JsonElement]
 */
fun String.toJson() = UniCore.getJsonHelper().parse(this)