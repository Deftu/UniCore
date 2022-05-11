package xyz.unifycraft.unicore.api.utils.hypixel

/**
 * UniCore's Hypixel location
 * utility.
 */
interface HypixelLocrawHelper {
    val locraw: HypixelLocraw?
    /**
     * Queues a locraw update check.
     */
    fun enqueueUpdate(interval: Long)
}