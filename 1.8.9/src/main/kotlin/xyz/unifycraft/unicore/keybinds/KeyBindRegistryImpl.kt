package xyz.unifycraft.unicore.keybinds

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import me.kbrewster.eventbus.Subscribe
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.events.KeyboardInputEvent
import xyz.unifycraft.unicore.api.events.MouseButtonEvent
import xyz.unifycraft.unicore.api.keybinds.BaseKeyBind
import xyz.unifycraft.unicore.api.keybinds.KeyBindRegistry
import xyz.unifycraft.unicore.api.keybinds.KeyBindState
import java.io.File

class KeyBindRegistryImpl(
    dataDir: File
) : KeyBindRegistry {
    private var previouslyHeldMouse = false
    val serializer = KeyBindSerializer(File(dataDir, "keybinds.json"))
    override val keyBinds: Multimap<Int, BaseKeyBind> = ArrayListMultimap.create()

    init {
        UniCore.getEventBus().register(this)
        serializer.read()
    }

    override fun registerKeyBind(keyBind: BaseKeyBind): Boolean {
        serializer.initialize(keyBind)
        return keyBinds.put(keyBind.keyCode, keyBind)
    }

    @Subscribe private fun onKeyboardInput(event: KeyboardInputEvent) {
        val isChar = keyBinds.containsKey(event.typedChar)
        if (keyBinds.containsKey(event.keyCode) || isChar) {
            val keyBinds = if (!isChar) keyBinds[event.keyCode] else keyBinds[event.typedChar.code]
            for (keyBind in keyBinds) {
                keyBind.handle(if (event.repeated) KeyBindState.HOLD else if (!event.released) KeyBindState.RELEASE else KeyBindState.PRESS)
            }
        }
    }

    @Subscribe private fun onMouseClick(event: MouseButtonEvent) {
        if (keyBinds.containsKey(event.button)) {
            val keyBinds = keyBinds[event.button]
            for (keyBind in keyBinds) {
                keyBind.handle(if (previouslyHeldMouse) KeyBindState.HOLD else if (!event.released) KeyBindState.RELEASE else KeyBindState.PRESS)
            }
            previouslyHeldMouse = !event.released
        }
    }
}