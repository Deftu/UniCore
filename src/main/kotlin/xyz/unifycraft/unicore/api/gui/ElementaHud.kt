package xyz.unifycraft.unicore.api.gui

import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.events.*
import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.UIComponent
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.Window
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.universal.UMatrixStack
import me.kbrewster.eventbus.Subscribe

interface ElementaHud {
    val window: Window
    val namespaces: Map<String, UIContainer>

    fun <T : UIComponent> add(namespace: String, component: T, createIfNotExists: Boolean = true)
    fun namespace(namespace: String, createIfNotExists: Boolean = true): UIContainer
}

infix fun <T : UIComponent> T.ofHud(namespace: String) = apply {
    UniCore.getElementaHud().add(namespace, this)
}
