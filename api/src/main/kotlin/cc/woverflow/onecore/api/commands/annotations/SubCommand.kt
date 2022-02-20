package cc.woverflow.onecore.api.commands.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class SubCommand(
    val name: String,
    val aliases: Array<String> = []
)