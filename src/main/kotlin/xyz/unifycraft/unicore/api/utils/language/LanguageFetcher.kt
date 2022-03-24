package xyz.unifycraft.unicore.api.utils.language

interface LanguageFetcher {
    val supportsMultipleFiles: Boolean
    suspend fun retrieveSingular(path: String): Language? =
        null
    suspend fun retrieveMultiple(path: String): List<Language?> =
        emptyList()
}