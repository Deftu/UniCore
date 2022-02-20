package cc.woverflow.onecore.api.events

class KeyboardInputEvent(
    val released: Boolean,
    val repeated: Boolean,
    val typedChar: Char,
    val keyCode: Int
)