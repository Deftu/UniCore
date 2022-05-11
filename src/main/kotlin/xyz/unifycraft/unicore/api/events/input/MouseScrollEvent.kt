package xyz.unifycraft.unicore.api.events.input

import xyz.unifycraft.unicore.api.events.Event

/**
 * Event called when the player
 * scrolls their mouse.
 */
class MouseScrollEvent(
    val delta: Double
) : Event()
