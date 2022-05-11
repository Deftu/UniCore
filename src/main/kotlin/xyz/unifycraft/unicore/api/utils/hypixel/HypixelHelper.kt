package xyz.unifycraft.unicore.api.utils.hypixel

import net.hypixel.api.HypixelAPI

/**
 * UniCore's Hypixel utility.
 */
interface HypixelHelper {
    val hypixelApi: HypixelAPI
    val locrawHelper: HypixelLocrawHelper
    /**
     * @return Whether the player is in the specified
     * game mode or not.
     */
    fun isGameType(gameType: HypixelGameType): Boolean
    /**
     * @return Whether the player is on the Hypixel
     * server or not.
     */
    fun isHypixel(): Boolean
}