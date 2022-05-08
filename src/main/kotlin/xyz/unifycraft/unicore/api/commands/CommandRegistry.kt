package xyz.unifycraft.unicore.api.commands

import xyz.unifycraft.unicore.api.commands.annotations.Command

interface CommandRegistry {
    val commands: Map<String, BaseCommand>
    fun registerCommand(command: BaseCommand)
    fun registerCommand(command: Any)
    fun registerCommand(command: Class<*>) = registerCommand(command.getConstructor().newInstance())

    fun processAutoComplete(input: String)
    fun getAutoCompletion(): Array<String>
}