package xyz.unifycraft.unicore.api.events

import java.io.File

/**
 * Event called when UniCore
 * initializes.
 */
class InitializationEvent(
    val gameDir: File
) : Event()