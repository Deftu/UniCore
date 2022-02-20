package xyz.deftu.unicore.api.events

class ChatSendEvent(
    var message: String,
    var cancelled: Boolean
)