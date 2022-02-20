package cc.woverflow.onecore.api.utils

import java.lang.reflect.AccessibleObject

fun AccessibleObject.setAccess(access: Boolean = true) {
    //#if MC<=11202
    isAccessible = access
    //#elseif MC>=11701
    //$$ trySetAccessible()
    //#endif
}