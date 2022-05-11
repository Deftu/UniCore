package xyz.unifycraft.unicore.api.utils.dsl

import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Makes it possible to create
 * "delayed" instances, which
 * are effectively lateinit.
 */
fun <T> instance() = DelayedInstanceDelegator<T>()
/**
 * Creates an instance of a class
 * using Java's service loader API.
 */
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
