package xyz.deftu.onecore

import xyz.deftu.onecore.api.OneCore
import xyz.deftu.onecore.api.events.*
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import net.minecraftforge.fml.common.gameevent.TickEvent
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse

class ForgeEventExtender {
    @SubscribeEvent fun onRenderTick(event: TickEvent.RenderTickEvent) {
        OneCore.getEventBus().post(RenderTickEvent())
    }

    @SubscribeEvent fun onMouseInput(event: InputEvent.MouseInputEvent) {
        val isScrolled = Mouse.getEventDWheel() != 0
        if (isScrolled) {
            OneCore.getEventBus().post(MouseScrollEvent(Mouse.getEventDWheel().toDouble()))
        } else {
            val isButton = Mouse.getEventButton() != 0
            if (isButton) {
                OneCore.getEventBus().post(
                    MouseButtonEvent(
                        Mouse.getEventButton(),
                        Mouse.getEventButtonState(),
                        Mouse.getEventX().toDouble(),
                        Mouse.getEventY().toDouble()
                    )
                )
            } else OneCore.getEventBus()
                .post(MouseMoveEvent(Mouse.getEventX().toDouble(), Mouse.getEventY().toDouble()))
        }
    }

    @SubscribeEvent fun onKeyboardInput(event: InputEvent.KeyInputEvent) {
        val repeatEvents = Keyboard.areRepeatEventsEnabled()
        Keyboard.enableRepeatEvents(true)
        OneCore.getEventBus().post(
            KeyboardInputEvent(
                Keyboard.getEventKeyState(),
                Keyboard.isRepeatEvent(),
                Keyboard.getEventCharacter(),
                Keyboard.getEventKey()
            )
        )
        Keyboard.enableRepeatEvents(repeatEvents)
    }
}