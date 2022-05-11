package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.util.IChatComponent

class ChatActionReceivedEvent(
    text: IChatComponent
) : ChatReceivedEvent(text, 2.toByte())
