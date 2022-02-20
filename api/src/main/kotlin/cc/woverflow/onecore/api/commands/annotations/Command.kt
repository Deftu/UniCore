package cc.woverflow.onecore.api.commands.annotations

@Target(AnnotationTarget.CLASS)
annotation class Command(
    val name: String,
    val aliases: Array<String> = []
)