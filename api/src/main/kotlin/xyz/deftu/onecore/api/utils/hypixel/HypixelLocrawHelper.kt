package xyz.deftu.onecore.api.utils.hypixel

interface HypixelLocrawHelper {
    val locraw: HypixelLocraw?
    fun enqueueUpdate(interval: Long)
}