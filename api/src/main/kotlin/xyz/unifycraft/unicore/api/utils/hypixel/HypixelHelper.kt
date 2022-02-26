package xyz.unifycraft.unicore.api.utils.hypixel

interface HypixelHelper {
    val apiKey: String
    val locrawHelper: HypixelLocrawHelper
    fun isGameType(gameType: HypixelGameType): Boolean
    fun isHypixel(): Boolean
}