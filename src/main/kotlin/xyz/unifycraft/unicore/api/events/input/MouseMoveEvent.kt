package xyz.unifycraft.unicore.api.events.input

import xyz.unifycraft.unicore.api.events.Event

/**
 * Event called when the player
 * moves their mouse.
 */
class MouseMoveEvent(
    val x: Double,
    val y: Double
) : Event()
