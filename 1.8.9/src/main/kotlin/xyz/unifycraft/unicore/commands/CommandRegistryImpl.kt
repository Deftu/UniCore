package xyz.unifycraft.unicore.commands

import gg.essential.universal.ChatColor
import me.kbrewster.eventbus.Subscribe
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiChat
import xyz.deftu.unicore.api.UniCore
import xyz.deftu.unicore.api.commands.BaseCommand
import xyz.deftu.unicore.api.commands.CommandRegistry
import xyz.deftu.unicore.api.commands.arguments.*
import xyz.deftu.unicore.api.events.ChatSendEvent

class CommandRegistryImpl : CommandRegistry {
    override val argumentSerializers = mutableMapOf<Class<*>, ArgumentSerializer<*>>()
    override val commands = mutableMapOf<String, BaseCommand>()
    private var autoCompletion: Array<String>? = null

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

    override fun processAutoComplete(input: String) {
        autoCompletion = null
        if (!input.startsWith("/")) return
        val input = input.replaceFirst("/", "")
        if (Minecraft.getMinecraft().currentScreen is GuiChat) {
            val options = retrieveAutoCompletions(input).toMutableList()
            if (options.isNotEmpty()) {
                if (input.indexOf(' ') == -1) {
                    for (i in options.indices) {
                        options[i] = ChatColor.BOLD + "/" + options[i] + ChatColor.RESET
                    }
                } else {
                    for (i in options.indices) {
                        options[i] = ChatColor.GRAY + options[i] + ChatColor.RESET
                    }
                }
                autoCompletion = options.toTypedArray()
            }
        }
    }

    private fun retrieveAutoCompletions(input: String): List<String> {
        val split = input.split(" ")
        val first = split[0]
        return if (split.size == 1) {
            val value = mutableListOf<String>()
            for (command in commands) {
                if (command.key.startsWith(first)) {
                    value.add(command.key)
                }
            }

            value
        } else listOf()
    }

    override fun getAutoCompletion() = autoCompletion ?: arrayOf()

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