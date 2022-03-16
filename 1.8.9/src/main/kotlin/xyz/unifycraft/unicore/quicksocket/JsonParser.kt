package xyz.unifycraft.unicore.quicksocket

import com.google.gson.JsonElement
import com.google.gson.stream.JsonReader
import xyz.deftu.quicksocket.common.utils.AbstractJsonParser
import java.io.Reader

class JsonParser : AbstractJsonParser {
    private val parser = com.google.gson.JsonParser()

    override fun isValidJson(input: String) = try {
        parse(input)
        true
    } catch (e: Exception) {
        false
    }

    override fun parse(input: JsonReader) =
        parser.parse(input)
    override fun parse(input: Reader) =
        parser.parse(input)
    override fun parse(input: String) =
        parser.parse(input)
}