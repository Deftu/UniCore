package xyz.unifycraft.unicore.api.events.chat

import xyz.unifycraft.unicore.api.events.CancellableEvent

class ChatSendEvent(
    var message: String
) : CancellableEvent()