package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.util.IChatComponent

class ChatSystemReceivedEvent(
    text: IChatComponent
) : ChatReceivedEvent(text, 1.toByte())
