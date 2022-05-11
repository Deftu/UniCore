package xyz.unifycraft.unicore.api.events.chat

import xyz.unifycraft.unicore.api.events.CancellableEvent

/**
 * Event called when the player
 * sends a chat message.
 */
class ChatSendEvent(
    var message: String
) : CancellableEvent()
