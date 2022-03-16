package xyz.unifycraft.unicore.api.gui.notifications

import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.FillConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.constraints.animation.Animations
import gg.essential.elementa.dsl.*
import xyz.unifycraft.unicore.api.UniCorePalette
import xyz.unifycraft.unicore.api.gui.components.UIHoverBlock
import java.util.function.Consumer

class Notification(
    val title: String,
    val description: String,
    val duration: Float,
    val click: Consumer<Notification>
) : UIHoverBlock(
    color = UniCorePalette.mediumGray.toConstraint(),
    hoverColor = UniCorePalette.mediumBlue.brighter(),
    originalColor = UniCorePalette.mediumBlue,
    outlineWidth = 1f
) {
    private val progressBar = UIBlock(UniCorePalette.mediumBlue).constrain {
        x = 0.pixels()
        y = 0.pixels(true)
        width = 0.pixels()
        height = 5.pixels()
    } childOf this

    init {
        constrain {
            x = 0.pixels(alignOpposite = true, alignOutside = true)
            y = 2.5f.pixels()
            width = Notification.width.pixels()
            height = Notification.height.pixels()
        }.onMouseEnter {
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
            width = FillConstraint(false) - 10.pixels()
            textScale = 1.3f.pixels()
        } childOf this
        val descriptionText = UIWrappedText(description).constrain {
            x = 2.pixels()
            y = SiblingConstraint(2f)
            width = FillConstraint(false) - 8.pixels()
        } childOf this
    }

    override fun afterInitialization() {
        animateIn()
    }

    private fun highlight() {
        progressBar.animate {
            setColorAnimation(Animations.OUT_EXP, 1f, this@Notification.hoverColor.brighter().toConstraint())
        }
    }

    private fun unhighlight() {
        progressBar.animate {
            setColorAnimation(Animations.OUT_EXP, 1f, this@Notification.hoverColor.toConstraint())
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
                FillConstraint(false)
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
                0.pixels(alignOpposite = true, alignOutside = true)
            )

            onComplete {
                hide(true)
            }
        }
    }

    companion object {
        @JvmStatic val width = 150
        @JvmStatic val height = 50
        @JvmStatic val movementAnimation = Animations.IN_OUT_QUAD
        @JvmStatic val movementDuration = 0.75f
        @JvmStatic val progressAnimation = Animations.LINEAR
    }
}