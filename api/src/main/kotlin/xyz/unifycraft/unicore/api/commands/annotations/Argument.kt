package xyz.unifycraft.unicore.api.commands.annotations

@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class Argument(
    val value: String = ""
)