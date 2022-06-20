package xyz.unifycraft.unicore.api.utils.text

import net.minecraft.util.IChatComponent

interface TextHelper {
    fun create(text: String): Text
    fun toVanilla(text: Text):
            //#if MC<=11404
            IChatComponent
            //#else
            //$$ net.minecraft.text.Text
            //#endif
}
