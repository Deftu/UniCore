package xyz.unifycraft.unicore.api.events

class ChatSendEvent(
    var message: String,
    var cancelled: Boolean
)