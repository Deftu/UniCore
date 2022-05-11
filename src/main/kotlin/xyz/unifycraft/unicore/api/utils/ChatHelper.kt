package xyz.unifycraft.unicore.api.utils

import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.util.IChatComponent

interface ChatHelper {
    fun sendMessage(message: String)
    fun sendMessage(message: IChatComponent)

    fun retrievePlayer(text: String): AbstractClientPlayer?
    fun retrievePlayer(text: IChatComponent): AbstractClientPlayer?
    fun addPlayerRegex(regex: String)
}
