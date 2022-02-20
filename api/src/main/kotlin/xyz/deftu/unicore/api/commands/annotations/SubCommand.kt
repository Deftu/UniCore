package xyz.deftu.unicore.api.commands.annotations

@Target(AnnotationTarget.FUNCTION)
annotation class SubCommand(
    val name: String,
    val aliases: Array<String> = []
)