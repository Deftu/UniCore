package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.util.IChatComponent

class ChatMessageReceivedEvent(
    text: IChatComponent,
    val sender: AbstractClientPlayer?
) : ChatReceivedEvent(text, 0.toByte())
