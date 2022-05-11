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

/**
 * UniCore's Elementa-based HUD system.
 *
 * These HUD elements are not saved or
 * loaded. The positioning and their actions
 * should be handled by the developer.
 */
interface ElementaHud {
    val window: Window
    val namespaces: Map<String, UIContainer>

    /**
     * Adds a component to the Elementa
     * HUD under the specified namespace.
     *
     * @param namespace The namespace to add the component to.
     * @param component The component to add.
     * @param createIfNotExists Whether to create the namespace if it doesn't exist.
     */
    fun <T : UIComponent> add(namespace: String, component: T, createIfNotExists: Boolean = true)
    /**
     * Returns a namespace from the
     * namespaces registry.
     *
     * @param namespace The namespace to create.
     * @param createIfNotExists Whether to create the namespace if it doesn't exist.
     * @return The namespace created.
     */
    fun namespace(namespace: String, createIfNotExists: Boolean = true): UIContainer
}

/**
 * Adds this Elementa component to the
 * UniCore HUD under the specified namespace.
 *
 * @param namespace The namespace to add this component to.
 */
infix fun <T : UIComponent> T.ofHud(namespace: String) = apply {
    UniCore.getElementaHud().add(namespace, this)
}
