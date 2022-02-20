package cc.woverflow.onecore.api.commands.arguments

import java.lang.reflect.Parameter

class ArgumentsQueueSerializer : ArgumentSerializer<ArgumentQueue> {
    override fun parse(queue: ArgumentQueue, parameter: Parameter) = queue
}