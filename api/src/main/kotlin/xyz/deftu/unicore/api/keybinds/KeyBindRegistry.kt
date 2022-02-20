package xyz.deftu.unicore.api.keybinds

import xyz.deftu.unicore.api.keybinds.annotations.KeyBind
import com.google.common.collect.Multimap

interface KeyBindRegistry {
    val keyBinds: Multimap<Int, BaseKeyBind>
    fun registerKeyBind(keyBind: BaseKeyBind): Boolean
    fun registerKeyBind(command: Class<*>) = registerKeyBind(command.getConstructor().newInstance())
    fun registerKeyBind(command: Any) = registerKeyBind(AnnotationKeyBind(command::class.java.getAnnotation(KeyBind::class.java), command::class.java, command))
}