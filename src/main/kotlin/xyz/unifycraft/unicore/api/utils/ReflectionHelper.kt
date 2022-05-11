package xyz.unifycraft.unicore.api.utils

import java.lang.reflect.AccessibleObject

/**
 * Sets the accessible flag of
 * the given object to the value
 * provided.
 */
fun AccessibleObject.setAccess(access: Boolean = true) {
    //#if MC<=11202
    isAccessible = access
    //#elseif MC>=11701
    //$$ trySetAccessible()
    //#endif
}