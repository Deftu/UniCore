package xyz.unifycraft.unicore.api.utils.updater

import java.io.File

interface Updater {
    val mods: List<UpdaterMod>
    var outdated: List<UpdaterMod>

    fun include(name: String, version: String, id: String, path: String, fetcher: UpdateFetcher, file: File)
    fun includeJson(name: String, version: String, id: String, file: File, url: String, versionFieldName: String, checksumFieldName: String?)
    fun includeJson(name: String, version: String, id: String, file: File, url: String, versionFieldName: String) = includeJson(name, version, id, file, url, versionFieldName, null)
    fun includeGitHub(name: String, version: String, id: String, file: File, repository: String)

    suspend fun check()
}
