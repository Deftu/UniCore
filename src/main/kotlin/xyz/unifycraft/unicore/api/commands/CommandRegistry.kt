package xyz.unifycraft.unicore.api.commands

/**
 * UniCore's command registry
 * and manager for it's command
 * API.
 */
interface CommandRegistry {
    val commands: Map<String, BaseCommand>
    fun registerCommand(command: BaseCommand)
    fun registerCommand(command: Any)
    fun registerCommand(command: Class<*>) = registerCommand(command.getConstructor().newInstance())

    fun processAutoComplete(input: String)
    fun getAutoCompletion(): Array<String>
}