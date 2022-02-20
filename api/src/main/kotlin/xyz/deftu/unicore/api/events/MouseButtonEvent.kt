package xyz.deftu.unicore.api.events

class MouseButtonEvent(
    val button: Int,
    val released: Boolean,
    val x: Double,
    val y: Double
)