package xyz.unifycraft.unicore.api.utils.language

import xyz.unifycraft.unicore.api.utils.language.fetchers.JsonLanguageFetcher
import xyz.unifycraft.unicore.api.utils.language.fetchers.UrlLanguageFetcher
import java.io.File

interface TranslationRegistry {
    val translationsDir: File
    val cachedLanguages: Map<String, List<Language>>
    fun retrieve(id: String, path: String, fetcher: LanguageFetcher)
    // TODO
    //fun retrieveCrowdin(id: String, name: String = id) = retrieve(id, name, CrowdinLanguageFetcher())
    fun retrieveJson(id: String, url: String) = retrieve(id, url, JsonLanguageFetcher())
    fun retrieveUrl(id: String, baseUrl: String) = retrieve(id, baseUrl, UrlLanguageFetcher())

    fun translate(id: String, path: String, vararg params: Any): String?
}

