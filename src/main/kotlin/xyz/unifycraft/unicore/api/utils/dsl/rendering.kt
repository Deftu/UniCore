package xyz.unifycraft.unicore.api.utils.dsl

import gg.essential.universal.UGraphics
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
inline fun <T, R> T.newMatrix(block: T.() -> R): R {
    UGraphics.GL.pushMatrix()
    val value = block.invoke(this)
    UGraphics.GL.popMatrix()
    return value
}

/**
 * Runs the callback provided
 * inside a matrix context with
 * the color provided.
 * 
 * @param rgba The color to use.
 * @param block The callback to run.
 * @return The result of the callback.
 */
inline fun <T, R> T.withColor(rgba: Int, block: T.() -> R) = newMatrix {
    UGraphics.color4f(
        rgba.getRed().toFloat(),
        rgba.getGreen().toFloat(),
        rgba.getBlue().toFloat(),
        rgba.getAlpha().toFloat()
    )
    block.invoke(this)
}

/**
 * Runs the callback provided
 * inside a matrix context with
 * the color provided.
 *
 * @param color The color to use.
 * @param block The callback to run.
 * @return The result of the callback.
 */
inline fun <T, R> T.withColor(color: Color, block: T.() -> R) =
    withColor(color.rgb, block)

/**
 * Runs the callback provided
 * inside a matrix context with
 * the scale provided.
 *
 * @param scale The scale to use.
 * @param yScale The y scale to use.
 * @param zScale The z scale to use.
 * @param block The callback to run.
 * @return The result of the callback.
 */
inline fun <T, R> T.withScale(xScale: Float, yScale: Float, zScale: Float, block: T.() -> R) = newMatrix {
    UGraphics.GL.translate(xScale, yScale, zScale)
    block.invoke(this)
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
 * @return The result of the callback.
 */
inline fun <T, R> T.withTranslate(x: Float, y: Float, z: Float, block: T.() -> R) = newMatrix {
    UGraphics.GL.translate(x, y, z)
    block.invoke(this)
}

/**
 * Runs the callback provided
 * inside a matrix context with
 * the rotation provided.
 *
 * @param x The x rotation.
 * @param y The y rotation.
 * @param z The z rotation.
 * @param block The callback to run.
 * @return The result of the callback.
 */
inline fun <T, R> T.withRotation(angle: Float, x: Float, y: Float, z: Float, block: T.() -> R) = newMatrix {
    UGraphics.GL.rotate(angle, x, y, z)
    block.invoke(this)
}
