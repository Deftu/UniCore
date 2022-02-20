package cc.woverflow.onecore.api.utils

import cc.woverflow.onecore.api.OneCore
import com.google.gson.JsonElement
import com.google.gson.JsonParser

class JsonHelper {

    //#if MC<=11202
    val jsonParser = JsonParser()
    //#else

    fun parse(json: String): JsonElement {
        //#if MC<=11202
        return jsonParser.parse(json)
        //#else
        //$$ JsonParser.parseString(json)
        //#endif
    }
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
fun String.toJson() = OneCore.getJsonHelper().parse(this)