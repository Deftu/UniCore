package xyz.unifycraft.unicore.api.events

class ChatSendEvent(
    var message: String
) : CancellableEvent()