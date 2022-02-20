package cc.woverflow.onecore.api.commands.arguments

import java.lang.reflect.Parameter

class IntArgumentSerializer : ArgumentSerializer<Int> {
    override fun parse(queue: ArgumentQueue, parameter: Parameter): Int {
        return queue.poll().toInt()
    }
}