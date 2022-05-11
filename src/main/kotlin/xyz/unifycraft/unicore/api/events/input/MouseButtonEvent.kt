package xyz.unifycraft.unicore.api.events.input

import xyz.unifycraft.unicore.api.events.CancellableEvent

class MouseButtonEvent(
    val button: Int,
    val released: Boolean,
    val x: Double,
    val y: Double
) : CancellableEvent()