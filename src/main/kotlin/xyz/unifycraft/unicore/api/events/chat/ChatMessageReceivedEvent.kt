package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.util.IChatComponent

/**
 * Event called when a chat
 * message is received.
 */
class ChatMessageReceivedEvent(
    text: IChatComponent,
    val sender: AbstractClientPlayer?
) : ChatReceivedEvent(text, 0.toByte())
