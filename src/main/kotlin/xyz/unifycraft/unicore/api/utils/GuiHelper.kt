package xyz.unifycraft.unicore.api.utils

import xyz.unifycraft.unicore.api.UniCore
import net.minecraft.client.gui.GuiScreen

/**
 * A utility class making
 * handling GUIs easier,
 * especially in legacy
 * versions.
 */
interface GuiHelper {
    //#if MC<=11202
    var screen: GuiScreen?
    //#endif

    /**
     * Queues a new screen to be
     * opened.
     */
    fun showScreen(screen: GuiScreen)
}

/**
 * Queue a new screen to be
 * opened.
 *
 * @see GuiHelper.showScreen
 */
fun GuiScreen.showScreen() = UniCore.getGuiHelper().showScreen(this)