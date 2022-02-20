package cc.woverflow.onecore.api.gui.notifications

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.RelativeConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import gg.essential.elementa.effects.OutlineEffect
import java.awt.Color
import java.util.function.Consumer

class Notification(
    val title: String,
    val description: String,
    val duration: Float,
    val click: Consumer<Notification>
) : UIBlock(Color(31, 31, 31)) {
    private val progressBar = UIBlock(accentColor).constrain {
        x = 0.pixels()
        y = 0.pixels(true)
        width = 0.pixels()
        height = 5.pixels()
    } childOf this

    init {
        constrain {
            x = 0.pixels(true, true)
            y = 2.5f.pixels(true)
            width = Notification.width.pixels()
            height = Notification.height.pixels()
        } effect OutlineEffect(
            color = accentColor,
            width = 1f,
            drawAfterChildren = true
        )

        onMouseEnter {
            highlight()
        }.onMouseLeave {
            unhighlight()
        }.onMouseRelease {
            click.accept(this@Notification)
            animateOut()
        }

        val titleText = UIWrappedText(title).constrain {
            x = 2.pixels()
            y = 2.pixels()
            width = RelativeConstraint() - 10.pixels()
            textScale = 1.3f.pixels()
        } childOf this
        val descriptionText = UIWrappedText(description).constrain {
            x = 2.pixels()
            y = SiblingConstraint(2f)
            width = RelativeConstraint() - 8.pixels()
        } childOf this
    }

    override fun afterInitialization() {
        animateIn()
    }

    private fun highlight() {
        val colour = accentColor.brighter()
        (effects[0] as OutlineEffect)::color.animate(Animations.OUT_EXP, 1f, colour)
        progressBar.animate {
            setColorAnimation(Animations.OUT_EXP, 1f, colour.toConstraint())
        }
    }

    private fun unhighlight() {
        (effects[0] as OutlineEffect)::color.animate(Animations.OUT_EXP, 1f, accentColor)
        progressBar.animate {
            setColorAnimation(Animations.OUT_EXP, 1f, accentColor.toConstraint())
        }
    }

    private fun animateIn() {
        animate {
            setXAnimation(
                movementAnimation,
                movementDuration,
                2.5f.pixels(true)
            )

            onComplete {
                animateProgress()
            }
        }
    }

    private fun animateProgress() {
        progressBar.animate {
            setWidthAnimation(
                progressAnimation,
                duration,
                RelativeConstraint()
            )

            onComplete {
                animateOut()
            }
        }
    }

    private fun animateOut() {
        animate {
            setXAnimation(
                movementAnimation,
                movementDuration,
                0.pixels(true, true)
            )

            onComplete {
                hide(true)
            }
        }
    }

    companion object {
        @JvmStatic val accentColor = Color(3, 177, 252)

        @JvmStatic val width = 150
        @JvmStatic val height = 50
        @JvmStatic val movementAnimation = Animations.IN_OUT_QUAD
        @JvmStatic val movementDuration = 0.75f
        @JvmStatic val progressAnimation = Animations.LINEAR
    }
}