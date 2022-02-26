package xyz.unifycraft.unicore.api.utils

import gg.essential.universal.UGraphics
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

fun <T> instance() = DelayedInstanceDelegator<T>()
inline fun <reified T> service() = ServiceLoaderDelegator(T::class.java)

class DelayedInstanceDelegator<T> : ReadWriteProperty<Any?, T> {
    private var instance: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (instance == null) throw IllegalAccessException("This instance has not been initialized yet.")
        else return instance!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        instance = value
    }
}

class ServiceLoaderDelegator<T>(
    private val clz: Class<T>
) : ReadWriteProperty<Any?, T> {
    private var instance: T? = null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (instance == null) {
            val service = ServiceLoader.load(clz)
            val iterator = service.iterator()
            if (iterator.hasNext()) {
                instance = iterator.next()
                if (iterator.hasNext()) throw IllegalStateException("There is more than one implementation, this is not supported.")
            } else throw IllegalStateException("Couldn't find implementation.")
            instance
        }
        return instance!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        throw IllegalArgumentException("A service injected property cannot be modified. It is immutable.")
    }
}