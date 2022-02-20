package xyz.deftu.unicore.api.commands.arguments

import java.lang.reflect.Parameter

interface ArgumentSerializer<T> {
    fun parse(queue: ArgumentQueue, parameter: Parameter): T
}