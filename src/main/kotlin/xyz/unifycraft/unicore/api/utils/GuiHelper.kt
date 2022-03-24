package xyz.unifycraft.unicore.api.utils

import net.minecraft.client.Minecraft
import xyz.unifycraft.unicore.api.UniCore
import net.minecraft.client.gui.GuiScreen
//#if MC<=11202
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
//#elseif MC>=11701
//$$ import net.minecraft.client.MinecraftClient
//#endif

class GuiHelper {
    //#if MC<=11202
    private var screen: GuiScreen? = null
    //#endif

    fun showScreen(screen: GuiScreen) {
        //#if MC<=11202
        this.screen = screen
        MinecraftForge.EVENT_BUS.register(this)
        //#else
        //$$ MinecraftClient.getInstance().setScreen(screen)
        //#endif
    }

    //#if MC<=11202
    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (screen !is PlaceholderScreen) {
            Minecraft.getMinecraft().displayGuiScreen(screen)
            screen = PlaceholderScreen()
            MinecraftForge.EVENT_BUS.unregister(this)
        }
    }

    private class PlaceholderScreen : GuiScreen()
    //#endif
}

//#if MC<=11202
/**
 * Queue a new screen to be opened.
 * @see GuiHelper.showScreen
 */
fun GuiScreen.showScreen() = UniCore.getGuiHelper().showScreen(this)
//#endif