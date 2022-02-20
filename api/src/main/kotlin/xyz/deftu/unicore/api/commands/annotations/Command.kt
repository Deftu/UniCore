package xyz.deftu.unicore.api.commands.annotations

@Target(AnnotationTarget.CLASS)
annotation class Command(
    val name: String,
    val aliases: Array<String> = []
)