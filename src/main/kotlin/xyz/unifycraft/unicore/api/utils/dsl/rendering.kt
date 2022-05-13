package xyz.unifycraft.unicore.api.utils.dsl

import gg.essential.universal.UGraphics
import gg.essential.universal.UMatrixStack
import xyz.unifycraft.unicore.api.utils.getAlpha
import xyz.unifycraft.unicore.api.utils.getBlue
import xyz.unifycraft.unicore.api.utils.getGreen
import xyz.unifycraft.unicore.api.utils.getRed
import java.awt.Color

/**
 * Runs the callback provided
 * inside a matrix context.
 *
 * @param block The callback to run.
 * @return The result of the callback.
 */
inline fun <R> matrix(block: MatrixScope.(UMatrixStack) -> R): R {
    val stack = UMatrixStack.Compat.get()
    val scope = MatrixScope(stack)
    stack.push()
    val value = block.invoke(scope, stack)
    stack.pop()
    return value
}

class MatrixScope(
    val stack: UMatrixStack
) {
    /**
     * Runs the callback provided
     * inside a matrix context with
     * the color provided.
     *
     * @param rgba The color to use.
     * @param block The callback to run.
     */
    fun withColor(rgba: Int, block: () -> Unit = {}) = apply {
        UGraphics.color4f(rgba.getRed().toFloat(), rgba.getGreen().toFloat(), rgba.getBlue().toFloat(), rgba.getAlpha().toFloat())
        block.invoke()
    }

    /**
     * Runs the callback provided
     * inside a matrix context with
     * the color provided.
     *
     * @param color The color to use.
     * @param block The callback to run.
     */
    fun withColor(color: Color, block: () -> Unit = {}) = apply {
        withColor(color.rgb, block)
    }

    /**
     * Runs the callback provided
     * inside a matrix context with
     * the color provided.
     *
     * @param red The red value to use.
     * @param green The green value to use.
     * @param blue The blue value to use.
     * @param alpha The alpha value to use.
     * @param block The callback to run.
     */
    fun withColor(red: Float, green: Float, blue: Float, alpha: Float, block: () -> Unit = {}) = apply {
        withColor(Color(red, green, blue, alpha), block)
        block.invoke()
    }

    /**
     * Runs the callback provided
     * inside a matrix context with
     * the scale provided.
     *
     * @param scale The scale to use.
     * @param block The callback to run.
     */
    fun withScale(scale: Float, block: () -> Unit = {}) = apply {
        stack.scale(scale, scale, scale)
        block.invoke()
    }

    /**
     * Runs the callback provided
     * inside a matrix context with
     * the scale provided.
     *
     * @param x The x scale to use.
     * @param y The y scale to use.
     * @param z The z scale to use.
     * @param block The callback to run.
     */
    fun withScale(x: Float, y: Float, z: Float, block: () -> Unit = {}) = apply {
        stack.scale(x, y, z)
        block.invoke()
    }

    /**
     * Runs the callback provided
     * inside a matrix context with
     * the translation provided.
     *
     * @param x The x translation.
     * @param y The y translation.
     * @param z The z translation.
     * @param block The callback to run.
     */
    fun withTranslate(x: Float, y: Float, z: Float, block: () -> Unit = {}) = apply {
        stack.translate(x, y, z)
        block.invoke()
    }

    /**
     * Runs the callback provided
     * inside a matrix context with
     * the rotation provided.
     *
     * @param angle The angle to rotate by.
     * @param x The x rotation.
     * @param y The y rotation.
     * @param z The z rotation.
     * @param block The callback to run.
     */
    fun withRotate(angle: Float, x: Float, y: Float, z: Float, block: () -> Unit = {}) = apply {
        stack.rotate(angle, x, y, z)
        block.invoke()
    }
}
