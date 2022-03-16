package xyz.unifycraft.unicore.api.utils.deleter

import xyz.unifycraft.unicore.api.UniCore
import gg.essential.universal.UDesktop
import okhttp3.Request
import okio.buffer
import okio.sink
import org.apache.logging.log4j.LogManager
import xyz.unifycraft.unicore.api.UniCoreEnvironment
import java.io.File


class Deleter(
    private val dataDir: File
) {
    private val logger = LogManager.getLogger("${UniCore.getName()} (Deleter)")
    private lateinit var file: File

    fun initialize() {
        file = try {
            dataDir.listFiles { dir: File, name: String -> name.contains("deleter", true) }.first()
        } catch (e: Exception) {
            File(dataDir, "Deleter.jar")
        }

        val request = Request.Builder()
            .get()
            .url("https://api.github.com/repos/${UniCoreEnvironment.deleterRepo}/releases/latest")
            .build()
        UniCore.getHttpRequester().request(request) {
            it.body?.let { body ->
                val raw = UniCore.getJsonHelper().parse(body.string())
                if (!raw.isJsonObject) return@request
                val json = raw.asJsonObject
                if (!json.has("assets")) return@request
                val assetsRaw = json.get("assets")
                if (!assetsRaw.isJsonArray) return@request
                val assets = assetsRaw.asJsonArray
                val latestRaw = assets[0]
                if (!latestRaw.isJsonObject) return@request
                val latest = latestRaw.asJsonObject
                if (!latest.has("name") || !latest.has("browser_download_url")) return@request
                val name = latest.get("name").asJsonPrimitive.asString
                if (file.name != name) {
                    val startTime = System.currentTimeMillis()
                    logger.info("Updating Deleter. (file name: $name)")
                    val downloadRequest = Request.Builder()
                        .get()
                        .url(latest.get("browser_download_url").asJsonPrimitive.asString)
                        .build()
                    UniCore.getHttpRequester().request(downloadRequest) { downloadResponse ->
                        downloadResponse.body?.let { downloadBody ->
                            val sink = file.sink().buffer()
                            sink.writeAll(downloadBody.source())
                            sink.close()
                            file.renameTo(File(dataDir, name))
                            logger.info("Updated Deleter in ${System.currentTimeMillis() - startTime}ms.")
                        }
                    }
                }
            }
        }
    }

    fun delete(files: List<File>) {
        if (UDesktop.isLinux) Runtime.getRuntime().exec("chmod +x \"${file.absolutePath}\"")
        if (UDesktop.isMac) Runtime.getRuntime().exec("chmod 755 \"${file.absolutePath}\"")
        Runtime.getRuntime().exec("java -jar ${file.name} ${files.joinToString(" ")}", null, file.parentFile)
    }

    fun delete(file: File) = delete(listOf(file))
}