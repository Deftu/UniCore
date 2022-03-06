package xyz.unifycraft.unicore.api.utils

import gg.essential.universal.ChatColor

class ChatPrefixBuilder {
    private val block = ChatPrefixBlock()
    fun setName(name: String) = apply { block.name = name }
    fun setColor(color: ChatColor) = apply { block.color = color }
    fun setBracketType(type: ChatPrefixType) = apply { block.brackets.type = type }
    fun setBracketColor(color: ChatColor) = apply { block.brackets.color = color }
    fun setBracketBold(bold: Boolean) = apply { block.brackets.bold = bold }
    fun build() = block.build()
}

fun chatPrefix(block: ChatPrefixBlock.() -> Unit) =
    ChatPrefixBlock().apply(block).build()

class ChatPrefixBlock {
    lateinit var name: String
    lateinit var color: ChatColor
    var brackets = ChatPrefixBracketBlock()

    fun brackets(block: ChatPrefixBracketBlock.() -> Unit) = apply {
        this.brackets.apply(block)
    }

    internal fun build(): String {
        var value = ""
        if (brackets.type == ChatPrefixType.BRACKETS) {
            if (brackets.bold) value += ChatColor.BOLD
            value += brackets.color
            value += "["
        }

        value += ChatColor.RESET
        value += color
        value += name
        value += ChatColor.RESET
        if (brackets.bold) value += ChatColor.BOLD
        value += brackets.color
        value += if (brackets.type == ChatPrefixType.BRACKETS) "]" else " >"
        value += ChatColor.RESET
        value += " "
        return value
    }

    class ChatPrefixBracketBlock {
        var type = ChatPrefixType.BRACKETS
        var color = ChatColor.DARK_GRAY
        var bold = true
    }
}

enum class ChatPrefixType {
    BRACKETS,
    CARET
}