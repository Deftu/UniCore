package xyz.deftu.onecore.api.commands

import xyz.deftu.onecore.api.commands.annotations.Command
import xyz.deftu.onecore.api.commands.arguments.ArgumentSerializer

interface CommandRegistry {
    val argumentSerializers: Map<Class<*>, ArgumentSerializer<*>>
    val commands: Map<String, BaseCommand>
    fun registerCommand(command: BaseCommand)
    fun registerCommand(command: Class<*>) = registerCommand(command.getConstructor().newInstance())
    fun registerCommand(command: Any) = registerCommand(AnnotationCommand(argumentSerializers, command::class.java.getAnnotation(Command::class.java), command::class.java, command))
    fun <T> registerArgumentParser(type: Class<T>, parser: ArgumentSerializer<T>)
}