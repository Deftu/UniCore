package xyz.unifycraft.unicore.api.keybinds

import com.google.common.collect.Multimap

/**
 * UniCore's keybind registry
 * and manager for it's keybind
 * API.
 */
interface KeyBindRegistry {
    val keyBinds: Multimap<Int, BaseKeyBind>
    /**
     * Registers a new keybind
     * using the abstract class.
     *
     * @param keyBind The keybind to register.
     */
    fun registerKeyBind(keyBind: BaseKeyBind): Boolean
    /**
     * Removes a new keybind
     * using the annotation API.
     *
     * @param keyBind The keybind to register.
     */
    fun registerKeyBind(keyBind: Any): Boolean
    /**
     * Removes a new keybind
     * using the annotation API.
     *
     * @param keyBind The keybind to register.
     */
    fun registerKeyBind(keyBind: Class<*>) = registerKeyBind(keyBind.getConstructor().newInstance())
}