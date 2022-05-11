package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.util.IChatComponent
import xyz.unifycraft.unicore.api.events.CancellableEvent

/**
 * Base event for chat message
 * events.
 */
abstract class ChatReceivedEvent(
    var text: IChatComponent,
    val type: Byte
) : CancellableEvent()
