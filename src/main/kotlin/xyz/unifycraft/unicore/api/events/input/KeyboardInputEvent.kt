package xyz.unifycraft.unicore.api.events.input

import xyz.unifycraft.unicore.api.events.CancellableEvent

/**
 * Event called when the player
 * inputs something on their
 * keyboard.
 */
class KeyboardInputEvent(
    val released: Boolean,
    val repeated: Boolean,
    val typedChar: Char,
    val keyCode: Int
) : CancellableEvent()
