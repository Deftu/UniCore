package xyz.deftu.unicore.commands

import me.kbrewster.eventbus.Subscribe
import xyz.deftu.unicore.api.UniCore
import xyz.deftu.unicore.api.commands.BaseCommand
import xyz.deftu.unicore.api.commands.CommandRegistry
import xyz.deftu.unicore.api.commands.arguments.*
import xyz.deftu.unicore.api.events.ChatSendEvent

class CommandRegistryImpl : CommandRegistry {
    override val argumentSerializers = mutableMapOf<Class<*>, ArgumentSerializer<*>>()
    override val commands = mutableMapOf<String, BaseCommand>()

    init {
        UniCore.getEventBus().register(this)

        argumentSerializers[Boolean::class.java] = BooleanArgumentSerializer()
        argumentSerializers[Double::class.java] = DoubleArgumentSerializer()
        argumentSerializers[Float::class.java] = FloatArgumentSerializer()
        argumentSerializers[Int::class.java] = IntArgumentSerializer()
        argumentSerializers[String::class.java] = StringArgumentSerializer()
    }

    override fun registerCommand(command: BaseCommand) {
        commands[command.name] = command
        command.aliases.forEach { commands[it] = command }
    }

    override fun <T> registerArgumentParser(type: Class<T>, parser: ArgumentSerializer<T>) {
        argumentSerializers[type] = parser
    }

    override fun autoComplete(): Array<String> {
        TODO("Not yet implemented")
    }

    @Subscribe private fun onChatSent(event: ChatSendEvent) {
        var message = event.message.trim()
        if (!message.startsWith("/")) return

        message = message.replaceFirst("/", "")
        val split = message.split(" ")
        val name = split[0]
        if (!commands.containsKey(name)) return

        val args = split.subList(1, split.size)
        val command = commands[name]!!
        command.execute(args.toList())
        event.cancelled = true
    }
}