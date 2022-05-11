package xyz.unifycraft.unicore.api.events.chat

import net.minecraft.util.IChatComponent
import xyz.unifycraft.unicore.api.events.CancellableEvent

abstract class ChatReceivedEvent(
    var text: IChatComponent,
    val type: Byte
) : CancellableEvent()
