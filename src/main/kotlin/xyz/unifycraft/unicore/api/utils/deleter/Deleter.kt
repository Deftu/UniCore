package xyz.unifycraft.unicore.api.utils.deleter

import java.io.File

/**
 * UniCore's file deleting utilities.
 */
interface Deleter {
    /**
     * Deletes a list of multiple
     * files.
     *
     * @param files The files to delete.
     */
    fun delete(files: List<File>)
    /**
     * Deletes a single file.
     *
     * @param file The file to delete.
     */
    fun delete(file: File)
}