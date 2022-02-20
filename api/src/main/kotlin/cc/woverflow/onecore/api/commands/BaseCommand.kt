package cc.woverflow.onecore.api.commands

abstract class BaseCommand(
    open val name: String,
    open val aliases: Array<String> = emptyArray()
) {
    abstract fun execute(args: List<String>): Boolean
}