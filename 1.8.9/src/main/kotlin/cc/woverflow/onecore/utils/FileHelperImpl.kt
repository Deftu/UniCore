package cc.woverflow.onecore.utils

import cc.woverflow.onecore.api.OneCore
import cc.woverflow.onecore.api.utils.FileHelper
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