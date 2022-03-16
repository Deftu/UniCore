package xyz.unifycraft.unicore.utils.updater

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiOptions
import net.minecraftforge.client.event.GuiScreenEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.utils.updater.ModUpdateCheckConfirmation
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs

class UpdaterEventListener {
    private val mc: Minecraft
        get() = Minecraft.getMinecraft()
    private var id = 0

    @SubscribeEvent
    fun onGuiInitialized(event: GuiScreenEvent.InitGuiEvent) {
        if (mc.theWorld != null) return
        if (event.gui !is GuiOptions) return
        if (id == 0) id = createSecureId(event.buttonList)
        event.buttonList.add(GuiButton(id, 2, event.gui.height - 22, 100, 20, "UniCore Updater"))
    }

    @SubscribeEvent
    fun onGuiAction(event: GuiScreenEvent.ActionPerformedEvent) {
        if (mc.theWorld != null) return
        if (event.gui !is GuiOptions) return
        if (id == 0) return
        if (event.button.id != id) return
        UniCore.getGuiHelper().showScreen(ModUpdateCheckConfirmation())
    }

    private fun createSecureId(
        buttonList: List<GuiButton> = listOf()
    ): Int {
        var id = abs(ThreadLocalRandom.current().nextInt())
        if (buttonList.any {
                it.id == id
        }) id = createSecureId(buttonList)
        return id
    }
}