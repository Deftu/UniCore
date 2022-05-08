package xyz.unifycraft.unicore.api.utils

import xyz.unifycraft.unicore.api.UniCore
import net.minecraft.client.gui.GuiScreen

interface GuiHelper {
    //#if MC<=11202
    var screen: GuiScreen?
    //#endif

    fun showScreen(screen: GuiScreen)
}

/**
 * Queue a new screen to be opened.
 * @see GuiHelper.showScreen
 */
fun GuiScreen.showScreen() = UniCore.getGuiHelper().showScreen(this)