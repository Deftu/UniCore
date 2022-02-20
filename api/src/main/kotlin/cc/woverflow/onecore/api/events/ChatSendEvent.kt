package cc.woverflow.onecore.api.events

class ChatSendEvent(
    var message: String,
    var cancelled: Boolean
)