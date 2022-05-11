package xyz.unifycraft.unicore.api.events

/**
 * Event called when text is
 * rendered in-game.
 */
class TextRenderEvent(
    var text: String
) : CancellableEvent()
