package cc.woverflow.onecore.api.commands.arguments

import java.lang.reflect.Parameter

class DoubleArgumentSerializer : ArgumentSerializer<Double> {
    override fun parse(queue: ArgumentQueue, parameter: Parameter): Double {
        return queue.poll().toDouble()
    }
}