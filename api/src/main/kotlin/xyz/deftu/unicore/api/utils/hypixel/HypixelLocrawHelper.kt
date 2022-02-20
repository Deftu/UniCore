package xyz.deftu.unicore.api.utils.hypixel

interface HypixelLocrawHelper {
    val locraw: HypixelLocraw?
    fun enqueueUpdate(interval: Long)
}