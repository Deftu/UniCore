package cc.woverflow.onecore.keybinds

import cc.woverflow.onecore.api.keybinds.annotations.Hold
import cc.woverflow.onecore.api.keybinds.annotations.KeyBind
import org.lwjgl.input.Keyboard

@KeyBind(
    name = "Test",
    category = "OneCore",
    keyCode = Keyboard.KEY_TAB
) class TestKeyBind {
    @Hold private fun handle() {
        println("TEST KEYBIND BEING HELD!")
    }
}