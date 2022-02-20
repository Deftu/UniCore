package cc.woverflow.onecore.api.gui

import cc.woverflow.onecore.api.OneCore
import cc.woverflow.onecore.api.events.*
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.Window
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.universal.UMatrixStack
import me.kbrewster.eventbus.Subscribe

infix fun <T : UIComponent> T.ofHud(namespace: String) = apply {
    OneCore.getElementaHud().add(namespace, this)
}

class ElementaHud {
    private lateinit var window: Window
    private val namespaces = mutableMapOf<String, UIContainer>()

    fun initialize() {
        window = Window(ElementaVersion.V1)
        OneCore.getEventBus().register(this)
    }

    fun <T : UIComponent> add(namespace: String, component: T, createIfNotExists: Boolean = true) {
        val container =
            if (createIfNotExists) namespaces.getOrPut(namespace) { createNamespaceContainer() } else namespaces[namespace]!!
        component childOf container
        namespaces[namespace] = container
    }

    fun namespace(namespace: String, createIfNotExists: Boolean = true) =
        if (createIfNotExists) namespaces.getOrPut(namespace) { createNamespaceContainer() } else namespaces[namespace]!!

    private fun createNamespaceContainer() = UIContainer().constrain {
        width = RelativeConstraint()
        height = RelativeConstraint()
    } childOf window

    @Subscribe fun onRenderTick(event: RenderTickEvent) { window.draw(UMatrixStack()) }
    @Subscribe fun onMouseScroll(event: MouseScrollEvent) { window.mouseScroll(event.delta) }
    @Subscribe fun onMouseMove(event: MouseMoveEvent) { window.mouseMove(window) }
    @Subscribe fun onMouseClick(event: MouseButtonEvent) { if (!event.released) window.mouseClick(event.x, event.y, event.button) else window.mouseRelease() }
    @Subscribe fun onKeyboardInput(event: KeyboardInputEvent) { window.keyType(event.typedChar, event.keyCode) }
}