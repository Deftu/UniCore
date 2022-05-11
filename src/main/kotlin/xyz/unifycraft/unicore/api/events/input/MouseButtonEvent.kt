package xyz.unifycraft.unicore.api.events.input

import xyz.unifycraft.unicore.api.events.CancellableEvent

/**
 * Event called when the player
 * presses a button on their
 * mouse.
 */
class MouseButtonEvent(
    val button: Int,
    val released: Boolean,
    val x: Double,
    val y: Double
) : CancellableEvent()
