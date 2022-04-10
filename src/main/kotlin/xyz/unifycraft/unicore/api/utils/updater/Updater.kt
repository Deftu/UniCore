package xyz.unifycraft.unicore.api.utils.updater

import gg.essential.universal.UDesktop
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.updater.fetchers.GitHubUpdateFetcher
import xyz.unifycraft.unicore.api.utils.updater.fetchers.JsonUpdateFetcher
import java.io.File

class Updater {
    private val mods = mutableListOf<UpdaterMod>()
    var outdated = listOf<UpdaterMod>()
        private set

    fun include(name: String, version: String, id: String, path: String, fetcher: UpdateFetcher, file: File) = mods.add(UpdaterMod(name, version, id, path, fetcher, file))
    fun includeJson(
        name: String,
        version: String,
        id: String,
        file: File,
        url: String,
        versionFieldName: String,
        checksumFieldName: String? = null
    ) = include(name, version, id, url, JsonUpdateFetcher(versionFieldName, checksumFieldName), file)
    fun includeGitHub(name: String, version: String, id: String, file: File, repository: String) = include(name, version, id, "https://api.github.com/repos/${repository}/releases/latest", GitHubUpdateFetcher(), file)

    @OptIn(DelicateCoroutinesApi::class)
    suspend fun check() {
        if (UniCore.getConfig().updateCheck) {
            val outdated = mutableListOf<UpdaterMod>()
            for (mod in mods) {
                GlobalScope.launch(Dispatchers.IO) {
                    mod.fetcher.check(this@Updater, mod)
                    if (mod.fetcher.hasUpdate()) outdated.add(mod).also {
                        this@Updater.outdated = outdated
                    }
                }
            }

            if (!registeredShutdownHook) {
                Runtime.getRuntime().addShutdownHook(Thread({
                    var changes = false
                    val arguments = mutableListOf<File>()
                    for (mod in outdated) {
                        if (mod.allowedUpdate) {
                            try {
                                if (System.getProperty("os.name").lowercase().contains("mac")) {
                                    val sipStatus = Runtime.getRuntime().exec("csrutil status")
                                    sipStatus.waitFor()
                                    if (!sipStatus.inputStream.use { it.bufferedReader().readText() }
                                            .contains("System Integrity Protection status: disabled.")) {
                                        UDesktop.open(mod.file.parentFile)
                                    }
                                }

                                arguments.add(mod.file)
                                changes = true
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    if (changes) UniCore.getDeleter().delete(arguments)
                }, "${UniCore.getName()} Updater Deletion Thread"))
                registeredShutdownHook = true
            }

            if (outdated.isNotEmpty()) {
                UniCore.getNotifications().post(
                    title = UniCore.getName(),
                    description = "Some of your mods are outdated! Click me to download updates.",
                    click = {
                        UniCore.getGuiHelper().showScreen(ModUpdateListScreen(outdated))
                    })
            }
        }
    }

    companion object {
        private var registeredShutdownHook = false
    }
}

data class UpdaterMod(
    val name: String,
    val version: String,
    val id: String,
    val path: String,
    val fetcher: UpdateFetcher,
    val file: File
) {
    var allowedUpdate = false
}

/**
 * Adapted from SimpleTimeChanger under AGPLv3
 * https://github.com/My-Name-Is-Jeff/SimpleTimeChanger/blob/master/LICENSE
 */
data class UpdateVersion(val version: String, val url: String? = null) : Comparable<UpdateVersion> {

    private val matched = regex.find(version)

    val isSafe = matched != null

    private val versionArtifact = DefaultArtifactVersion(matched!!.groups["version"]!!.value)
    val specialVersionType = run {
        val typeString = matched!!.groups["type"]?.value ?: return@run UpdateType.RELEASE

        return@run UpdateType.values().find { typeString == it.prefix } ?: UpdateType.UNKNOWN
    }
    val specialVersion = run {
        if (specialVersionType == UpdateType.RELEASE) return@run null
        return@run matched!!.groups["typever"]?.value?.toDoubleOrNull()
    }

    override fun compareTo(other: UpdateVersion): Int {
        if (!isSafe || !other.isSafe) return -1
        return if (versionArtifact.compareTo(other.versionArtifact) == 0) {
            if (specialVersionType.ordinal == other.specialVersionType.ordinal) {
                (specialVersion ?: 0.0).compareTo(other.specialVersion ?: 0.0)
            } else other.specialVersionType.ordinal - specialVersionType.ordinal
        } else versionArtifact.compareTo(other.versionArtifact)
    }

    companion object {
        val regex = Regex("^(?<version>[\\d.]+)-?(?<type>\\D+)?(?<typever>\\d+\\.?\\d*)?$")
    }

    enum class UpdateType(val prefix: String) {
        RELEASE(""), PRERELEASE("pre"), BETA("beta"), ALPHA("alpha"), UNKNOWN("unknown")
    }
}