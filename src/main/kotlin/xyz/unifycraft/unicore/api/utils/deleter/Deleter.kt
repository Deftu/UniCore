package xyz.unifycraft.unicore.api.utils.deleter

import java.io.File

interface Deleter {
    fun delete(files: List<File>)
    fun delete(file: File)
}