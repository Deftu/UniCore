package xyz.unifycraft.unicore.api.utils

import java.io.File

interface FileHelper {
    val gameDir: File
    val configDir: File
    val orgDir: File
    val dataDir: File
}