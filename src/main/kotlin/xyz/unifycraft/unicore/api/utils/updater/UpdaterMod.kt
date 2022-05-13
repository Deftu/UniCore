package xyz.unifycraft.unicore.api.utils.updater

import java.io.File

data class UpdaterMod(
    val name: String,
    val version: String,
    val id: String,
    val path: String,
    val fetcher: UpdateFetcher,
    val file: File,
    val changelog: String = ""
) {
    var allowedUpdate = false
}
