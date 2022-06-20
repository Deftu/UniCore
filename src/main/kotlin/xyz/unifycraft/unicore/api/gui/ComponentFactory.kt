package xyz.unifycraft.unicore.api.gui

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.effects.OutlineEffect
import xyz.unifycraft.unicore.api.UniCorePalette
import java.awt.Color

interface ComponentFactory {
    fun createHoverBlock(
        hoverColor: Color = Color.WHITE,
        originalColor: Color = Color(0, 0, 0, 0),
        animation: Animations = Animations.OUT_EXP,
        animationTime: Float = 0.75f,
        outlineWidth: Float = 2f,
        drawAfterChildren: Boolean = false,
        drawInsideChildren: Boolean = false,
        sides: Set<OutlineEffect.Side> = setOf(
            OutlineEffect.Side.Left,
            OutlineEffect.Side.Top,
            OutlineEffect.Side.Right,
            OutlineEffect.Side.Bottom
        )
    ): UIBlock
    fun createLinkText(
        text: String,
        linkColor: Color = UniCorePalette.PRIMARY
    ): UIText
}
