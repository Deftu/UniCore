package xyz.unifycraft.unicore.api.utils

import gg.essential.universal.UGraphics

inline fun <T, R> T.newMatrix(block: T.() -> R) {
    UGraphics.GL.pushMatrix()
    block.invoke(this)
    UGraphics.GL.popMatrix()
}

inline fun <T, R> T.withColor(rgba: Int, block: T.() -> R) = newMatrix {
    UGraphics.color4f(
        rgba.getRed().toFloat(),
        rgba.getGreen().toFloat(),
        rgba.getBlue().toFloat(),
        rgba.getAlpha().toFloat()
    )
    block.invoke(this)
}

inline fun <T, R> T.withScale(xScale: Float, yScale: Float, zScale: Float, block: T.() -> R) = newMatrix {
    UGraphics.GL.translate(xScale, yScale, zScale)
    block.invoke(this)
}

inline fun <T, R> T.withTranslate(x: Float, y: Float, z: Float, block: T.() -> R) = newMatrix {
    UGraphics.GL.translate(x, y, z)
    block.invoke(this)
}

inline fun <T, R> T.withRotation(angle: Float, x: Float, y: Float, z: Float, block: T.() -> R) = newMatrix {
    UGraphics.GL.rotate(angle, x, y, z)
    block.invoke(this)
}