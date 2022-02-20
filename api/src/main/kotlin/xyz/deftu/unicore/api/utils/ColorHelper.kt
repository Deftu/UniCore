package xyz.deftu.unicore.api.utils

import xyz.deftu.unicore.api.UniCore
import java.awt.Color

class ColorHelper {
    /**
     * @return A changing colour based on the users' computer time. Simulates a "chroma" colour.
     */
    fun getChroma() = Color.HSBtoRGB(System.currentTimeMillis() % 2000L / 2000.0f, 1.0f, 1.0f)

    /**
     * @return The red value of the provided RGBA value.
     */
    fun getRed(rgba: Int) = (rgba shr 16) and 0xFF

    /**
     * @return The green value of the provided RGBA value.
     */
    fun getGreen(rgba: Int) = (rgba shr 8) and 0xFF

    /**
     * @return The blue value of the provided RGBA value.
     */
    fun getBlue(rgba: Int) = (rgba shr 0) and 0xFF

    /**
     * @return The alpha value of the provided RGBA value.
     */
    fun getAlpha(rgba: Int) = (rgba shr 24) and 0xFF
}

/**
 * @return The red value of the provided RGBA value.
 */
fun Int.getRed() = UniCore.getColorHelper().getRed(this)

/**
 * @return The green value of the provided RGBA value.
 */
fun Int.getGreen() = UniCore.getColorHelper().getGreen(this)

/**
 * @return The blue value of the provided RGBA value.
 */
fun Int.getBlue() = UniCore.getColorHelper().getBlue(this)

/**
 * @return The alpha value of the provided RGBA value.
 */
fun Int.getAlpha() = UniCore.getColorHelper().getAlpha(this)

/**
 * @return This int converted to RGB color.
 */
fun Int.toColor() = Color(this)

/**
 * @return The same color with the red provided.
 */
fun Color.withRed(red: Int) = Color(red, green, blue, alpha)

/**
 * @return The same color with the green provided.
 */
fun Color.withGreen(green: Int) = Color(red, green, blue, alpha)

/**
 * @return The same color with the blue provided.
 */
fun Color.withBlue(blue: Int) = Color(red, green, blue, alpha)

/**
 * @return The same color with the alpha provided.
 */
fun Color.withAlpha(alpha: Int) = Color(red, green, blue, alpha)