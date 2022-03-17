package xyz.unifycraft.unicore.utils

import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.FileHelper
import java.io.File

class FileHelperImpl(
    override val gameDir: File
) : FileHelper {
    override val configDir: File
        get() = File(
            gameDir,
            "config"
        ).apply(fileChecks)
    override val orgDir: File
        get() = File(
            gameDir,
            "UnifyCraft"
        ).apply(fileChecks)
    override val dataDir: File
        get() = File(
            orgDir,
            UniCore.getName()
        ).apply(fileChecks)
    companion object {
        private val fileChecks: File.() -> Unit = {
            if (!exists() && !mkdirs())
                throw IllegalStateException("Failed to create a ${UniCore.getName()} directory.")
        }
    }
}