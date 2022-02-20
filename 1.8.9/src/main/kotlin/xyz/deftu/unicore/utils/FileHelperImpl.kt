package xyz.deftu.unicore.utils

import xyz.deftu.unicore.api.UniCore
import xyz.deftu.unicore.api.utils.FileHelper
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