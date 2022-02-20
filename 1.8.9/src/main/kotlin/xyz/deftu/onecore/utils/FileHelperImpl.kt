package xyz.deftu.onecore.utils

import xyz.deftu.onecore.api.OneCore
import xyz.deftu.onecore.api.utils.FileHelper
import java.io.File

class FileHelperImpl(
    override val gameDir: File
) : FileHelper {
    override val dataDir: File
        get() = File(
            gameDir,
            "OneCore"
        ).also { if (!it.exists() && !it.mkdirs()) throw IllegalStateException("Failed to create ${OneCore.getName()} data directory.") }
}