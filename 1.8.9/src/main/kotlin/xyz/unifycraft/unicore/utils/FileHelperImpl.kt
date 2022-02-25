package xyz.unifycraft.unicore.utils

import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.FileHelper
import java.io.File

class FileHelperImpl(
    override val gameDir: File
) : FileHelper {
    override val dataDir: File
        get() = File(
            gameDir,
            UniCore.getName()
        ).also { if (!it.exists() && !it.mkdirs()) throw IllegalStateException("Failed to create ${UniCore.getName()} data directory.") }
}