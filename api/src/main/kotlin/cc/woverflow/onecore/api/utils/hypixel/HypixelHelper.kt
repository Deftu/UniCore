package cc.woverflow.onecore.api.utils.hypixel

interface HypixelHelper {
    val apiKey: String
    val locrawHelper: HypixelLocrawHelper
    fun isHypixel(): Boolean
}