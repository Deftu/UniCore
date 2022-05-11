package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.util.IChatComponent

/**
 * Event called when the player
 * receives a system chat message.
 */
class ChatSystemReceivedEvent(
    text: IChatComponent
) : ChatReceivedEvent(text, 1.toByte())
