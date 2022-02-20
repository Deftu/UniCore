package xyz.deftu.onecore.keybinds

import xyz.deftu.onecore.api.OneCore
import xyz.deftu.onecore.api.keybinds.BaseKeyBind
import com.google.gson.JsonObject
import java.io.File

class KeyBindSerializer(
    private val configFile: File
) {
    private lateinit var config: JsonObject

    init {
        if (!configFile.exists() && !configFile.createNewFile()
                .also { configFile.writeText(OneCore.getGson().toJson(JsonObject())) }
        ) throw IllegalStateException("Failed to create ${OneCore.getName()} keybinds configuration file.")
    }

    fun initialize(keyBind: BaseKeyBind) {
        var error = false
        val serializedName = keyBind.getSerializedName()
        if (config.has(serializedName)) {
            val saved = config.get(serializedName)
            if (saved.isJsonPrimitive) {
                val primitive = saved.asJsonPrimitive
                if (primitive.isNumber) {
                    keyBind.keyCode = primitive.asInt
                } else error = true
            } else error = true
        } else error = true
        if (error) update(keyBind)
    }

    fun update(keyBind: BaseKeyBind, keyCode: Int) {
        val serializedName = keyBind.getSerializedName()
        keyBind.keyCode = keyCode
        config.addProperty(serializedName, keyCode)
        configFile.writeText(OneCore.getGson().toJson(config))
    }

    fun update(keyBind: BaseKeyBind) = update(keyBind, keyBind.keyCode)

    private fun BaseKeyBind.getSerializedName() = "$category.$name".lowercase()

    fun read() {
        if (!configFile.exists()) return
        val saved = OneCore.getJsonHelper().parse(configFile.readText())
        if (!saved.isJsonObject) fixConfig().also { return }
        config = saved.asJsonObject
    }

    private fun fixConfig() {
        if (!configFile.exists()) return
        configFile.writeText(OneCore.getGson().toJson(JsonObject()))
    }
}