package xyz.unifycraft.unicore.api.keybinds

import xyz.unifycraft.unicore.api.keybinds.annotations.Hold
import xyz.unifycraft.unicore.api.keybinds.annotations.KeyBind
import xyz.unifycraft.unicore.api.keybinds.annotations.Press
import xyz.unifycraft.unicore.api.keybinds.annotations.Release
import xyz.unifycraft.unicore.api.utils.setAccess
import com.google.common.collect.ArrayListMultimap

internal class AnnotationKeyBind(
    keyBind: KeyBind,
    clz: Class<*>,
    instance: Any
) : BaseKeyBind(
    keyBind.name,
    keyBind.category,
    keyBind.keyCode
) {
    private val callbacks = ArrayListMultimap.create<KeyBindState, Runnable>()

    init {
        for (method in clz.declaredMethods) {
            method.setAccess(true)
            val state: KeyBindState? = if (method.isAnnotationPresent(Press::class.java)) KeyBindState.PRESS
            else if (method.isAnnotationPresent(Hold::class.java)) KeyBindState.HOLD
            else if (method.isAnnotationPresent(Release::class.java)) KeyBindState.RELEASE
            else null
            state?.let { callbacks[state].add { method.invoke(instance) } }
        }
    }

    override fun handle(state: KeyBindState) {
        callbacks[state]?.let {
            for (runnable in it) {
                runnable.run()
            }
        }
    }
}