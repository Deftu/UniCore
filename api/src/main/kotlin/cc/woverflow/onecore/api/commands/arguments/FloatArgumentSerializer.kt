package cc.woverflow.onecore.api.commands.arguments

import java.lang.reflect.Parameter

class FloatArgumentSerializer : ArgumentSerializer<Float> {
    override fun parse(queue: ArgumentQueue, parameter: Parameter): Float {
        return queue.poll().toFloat()
    }
}