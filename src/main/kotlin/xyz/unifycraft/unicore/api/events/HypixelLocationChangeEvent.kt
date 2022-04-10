package xyz.unifycraft.unicore.api.events

import xyz.unifycraft.unicore.api.utils.hypixel.HypixelLocraw

class HypixelLocationChangeEvent(
    val locraw: HypixelLocraw
) : Event()