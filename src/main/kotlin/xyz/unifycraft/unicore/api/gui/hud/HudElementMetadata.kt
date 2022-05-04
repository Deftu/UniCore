package xyz.unifycraft.unicore.api.gui.hud

abstract class HudElementMetadata {
    abstract fun create(): HudElement
    abstract fun getName(): String
    fun getDescription() = "No description provided."
}
