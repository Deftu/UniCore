package xyz.unifycraft.unicore.api.events

import xyz.unifycraft.unicore.api.utils.hypixel.HypixelLocraw

/**
 * Event called when the player's
 * location changes on Hypixel.
 */
class HypixelLocationChangeEvent(
    val locraw: HypixelLocraw
) : Event()