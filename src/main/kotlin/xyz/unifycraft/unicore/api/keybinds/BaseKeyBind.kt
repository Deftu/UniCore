package xyz.unifycraft.unicore.api.keybinds

abstract class BaseKeyBind(
    val name: String,
    val category: String,
    var keyCode: Int
) {
    abstract fun handle(state: KeyBindState)
}

enum class KeyBindState {
    PRESS,
    HOLD,
    RELEASE
}