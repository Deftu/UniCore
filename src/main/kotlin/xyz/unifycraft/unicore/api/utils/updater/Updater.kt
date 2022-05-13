package xyz.unifycraft.unicore.api.utils.updater

import java.io.File

/**
 * UniCore's mod auto-updater.
 */
interface Updater {
    val mods: List<UpdaterMod>
    val outdated: List<UpdaterMod>

    /**
     * Adds a mod to the updater
     * registry.
     */
    fun include(name: String, version: String, id: String, path: String, fetcher: UpdateFetcher, file: File)
    /**
     * Adds a mod to the updater
     * registry.
     */
    fun includeJson(name: String, version: String, id: String, file: File, url: String, versionFieldName: String, checksumFieldName: String?)
    /**
     * Adds a mod to the updater
     * registry.
     */
    fun includeJson(name: String, version: String, id: String, file: File, url: String, versionFieldName: String) = includeJson(name, version, id, file, url, versionFieldName, null)
    /**
     * Adds a mod to the updater
     * registry.
     */
    fun includeGitHub(name: String, version: String, id: String, file: File, repository: String)

    /**
     * Checks for updates in the mods
     * which are currently in the
     * registry.
     */
    fun check()
}
