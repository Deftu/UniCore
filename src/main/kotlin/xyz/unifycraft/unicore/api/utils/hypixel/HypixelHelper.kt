package xyz.unifycraft.unicore.api.utils.hypixel

import net.hypixel.api.HypixelAPI

interface HypixelHelper {
    val hypixelApi: HypixelAPI
    val locrawHelper: HypixelLocrawHelper
    fun isGameType(gameType: HypixelGameType): Boolean
    fun isHypixel(): Boolean
}