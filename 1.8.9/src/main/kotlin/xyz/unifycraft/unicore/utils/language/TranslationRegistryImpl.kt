package xyz.unifycraft.unicore.utils.language

import com.google.gson.JsonObject
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.I18n
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.language.Language
import xyz.unifycraft.unicore.api.utils.language.LanguageFetcher
import xyz.unifycraft.unicore.api.utils.language.TranslationRegistry
import java.io.File
import java.nio.charset.StandardCharsets

class TranslationRegistryImpl : TranslationRegistry {
    override val translationsDir = File(UniCore.getFileHelper().dataDir, "Translations")
    override val cachedLanguages = mutableMapOf<String, MutableList<Language>>()

    @OptIn(DelicateCoroutinesApi::class)
    override fun retrieve(id: String, path: String, fetcher: LanguageFetcher) {
        GlobalScope.launch(Dispatchers.IO) {
            if (fetcher.supportsMultipleFiles) {
                for (language in Minecraft.getMinecraft().languageManager.languages) {
                    val replacedPath = path.replace("{lang}", language.languageCode)
                    fetcher.retrieveSingular(replacedPath)?.let {
                        cachedLanguages.putIfAbsent(id, mutableListOf())
                        cachedLanguages[id]?.add(it)
                    }
                }
            } else {
                fetcher.retrieveMultiple(path).let {
                    cachedLanguages.putIfAbsent(id, mutableListOf())
                    cachedLanguages[id]?.addAll(it.requireNoNulls())
                }
            }

            for (cachedLang in cachedLanguages) {
                val dir = File(translationsDir, cachedLang.key)
                for (language in cachedLang.value) {
                    val json = JsonObject()
                    language.items.forEach { json.addProperty(it.first, it.second) }
                    val file = File(dir, "${language.code}.json")
                    file.writeText(UniCore.getGson().toJson(json), StandardCharsets.UTF_8)
                }
            }

            I18n.format()
        }
    }
}