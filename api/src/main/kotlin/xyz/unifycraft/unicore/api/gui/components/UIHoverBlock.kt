package xyz.unifycraft.unicore.api.gui.components

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.constraints.ColorConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.effect
import gg.essential.elementa.dsl.toConstraint
import gg.essential.elementa.effects.OutlineEffect
import java.awt.Color

open class UIHoverBlock(
    val color: ColorConstraint = Color.WHITE.toConstraint(),
    val hoverColor: Color = Color.WHITE,
    val originalColor: Color = Color(0, 0, 0, 0),
    val animation: Animations = Animations.OUT_EXP,
    val animationTime: Float = 0.75f,
    val outlineWidth: Float = 2f,
    val drawAfterChildren: Boolean = false,
    val drawInsideChildren: Boolean = false,
    val sides: Set<OutlineEffect.Side> = setOf(
        OutlineEffect.Side.Left,
        OutlineEffect.Side.Top,
        OutlineEffect.Side.Right,
        OutlineEffect.Side.Bottom
    )
) : UIBlock(
    color
) {
    init {
        val effect = OutlineEffect(originalColor, outlineWidth, drawAfterChildren, drawInsideChildren, sides)
        effect(effect).onMouseEnter {
            effect::color.animate(animation, animationTime, hoverColor)
        }.onMouseLeave {
            effect::color.animate(animation, animationTime, originalColor)
        }
    }
}