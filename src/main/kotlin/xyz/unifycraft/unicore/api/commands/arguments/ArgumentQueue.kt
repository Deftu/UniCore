package xyz.unifycraft.unicore.api.commands.arguments

import java.util.concurrent.LinkedBlockingDeque

class ArgumentQueue(
    val arguments: List<String>
) : Iterable<String> {
    private var initialized = false
    private lateinit var queue: LinkedBlockingDeque<String>

    fun initialize() {
        if (!initialized) {
            queue = LinkedBlockingDeque()
            queue.addAll(arguments)
            initialized = true
        }
    }

    fun poll() = queue.poll()
    fun peek() = queue.peek()
    fun isEmpty() = queue.isEmpty()

    override fun toString() = arguments.toString()

    override fun iterator() = arguments.iterator()


}