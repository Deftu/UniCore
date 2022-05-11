package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.util.IChatComponent

/**
 * Event called when an action
 * bar message is received.
 */
class ChatActionReceivedEvent(
    text: IChatComponent
) : ChatReceivedEvent(text, 2.toByte())
