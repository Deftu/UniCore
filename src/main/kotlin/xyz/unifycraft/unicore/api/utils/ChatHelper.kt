package xyz.unifycraft.unicore.api.utils

import net.minecraft.client.entity.AbstractClientPlayer
import net.minecraft.util.IChatComponent

/**
 * A utility class making
 * using chat simpler.
 */
interface ChatHelper {
    /**
     * Sends a message to the currently
     * open (integrated) server.
     *
     * @param message The message to send
     */
    fun sendMessage(message: IChatComponent)
    /**
     * Sends a message to the currently
     * open (integrated) server.
     *
     * @see [sendMessage]
     * @param message The message to send
     */
    fun sendMessage(message: String)

    /**
     * Retrieves a player's name from
     * the text and gives you an instance
     * of a player object.
     *
     * @param text The text to search for.
     * @return The player object.
     */
    fun retrievePlayer(text: IChatComponent): AbstractClientPlayer?
    /**
     * Retrieves a player's name from
     * the text and gives you an instance
     * of a player object.
     *
     * @param text The text to search for.
     * @return The player object.
     */
    fun retrievePlayer(text: String): AbstractClientPlayer?
    /**
     * Adds a regex to the player
     * name search for [retrievePlayer].
     *
     * @see [retrievePlayer]
     * @param regex The regex to add.
     */
    fun addPlayerRegex(regex: String)
}
