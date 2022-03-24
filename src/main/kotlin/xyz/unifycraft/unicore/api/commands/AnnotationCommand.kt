package xyz.unifycraft.unicore.api.commands

import xyz.unifycraft.unicore.api.commands.annotations.*
import xyz.unifycraft.unicore.api.commands.arguments.ArgumentQueue
import xyz.unifycraft.unicore.api.commands.arguments.ArgumentSerializer
import xyz.unifycraft.unicore.api.utils.setAccess
import java.lang.reflect.Method
import java.lang.reflect.Parameter

internal class AnnotationCommand(
    val argumentSerializers: Map<Class<*>, ArgumentSerializer<*>>,
    command: Command,
    clz: Class<*>,
    val instance: Any
) : BaseCommand(
    command.name,
    command.aliases
) {
    private val defaults = mutableListOf<Runnable>()
    private val subCommands = mutableMapOf<String, InternalSubCommand>()

    init {
        for (method in clz.declaredMethods) {
            method.setAccess(true)
            if (method.isAnnotationPresent(Default::class.java)) defaults.add { method.invoke(instance) }
            else if (method.isAnnotationPresent(SubCommand::class.java)) {
                val annotation = method.getAnnotation(SubCommand::class.java)
                val subCommand = InternalSubCommand(method, method.parameters)
                subCommands[annotation.name] = subCommand
                annotation.aliases.forEach { subCommands[it] = subCommand }
            }
        }
    }

    override fun execute(args: List<String>): Boolean {
        if (args.isNotEmpty()) {
            if (!subCommands.containsKey(args[0])) return false
            subCommands[args[0]]?.let {
                val moddedArgs = args.toMutableList()
                moddedArgs.removeAt(0)
                val queue = ArgumentQueue(moddedArgs).also { queue -> queue.initialize() }
                val arguments = mutableListOf<Any>()
                it.parameters.forEach { parameter ->
                    if (parameter.isAnnotationPresent(Argument::class.java)) {
                        val argumentSerializer = argumentSerializers[parameter.type]
                        argumentSerializer?.let { serializer ->
                            arguments.add(serializer.parse(queue, parameter)!!)
                        }
                    }
                }

                it.method.invoke(instance, *arguments.toTypedArray())
            }
        } else defaults.forEach { it.run() }
        return true
    }

    internal class InternalSubCommand(
        val method: Method,
        val parameters: Array<Parameter>
    )
}