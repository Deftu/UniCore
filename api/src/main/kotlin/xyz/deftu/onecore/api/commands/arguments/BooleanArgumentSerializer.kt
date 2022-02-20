package xyz.deftu.onecore.api.commands.arguments

import java.lang.reflect.Parameter

class BooleanArgumentSerializer : ArgumentSerializer<Boolean> {
    override fun parse(queue: ArgumentQueue, parameter: Parameter): Boolean {
        return queue.poll().contentEquals("true", true)
    }
}