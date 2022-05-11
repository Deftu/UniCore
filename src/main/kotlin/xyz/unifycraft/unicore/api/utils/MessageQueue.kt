package xyz.unifycraft.unicore.api.utils

import java.util.function.Consumer

/**
 * A message queue that can be
 * used to send message to the
 * currently open (integrated)
 * server. This is mostly useful
 * for delaying messages on servers
 * which have a chat cooldown.
 */
interface MessageQueue {
    /**
     * Adds a message to the message queue
     * using a callback.
     *
     * @param text The text callback to use.
     * @param delay The delay in ticks to wait before sending the message.
     * @param callback The callback to when sending the message.
     */
    fun queue(text: () -> String, delay: Int, callback: Consumer<String>)
    /**
     * Adds a message to the message queue
     * using a callback.
     *
     * @param text The text callback to use.
     * @param callback The callback to when sending the message.
     */
    fun queue(text: () -> String, callback: Consumer<String>)
    /**
     * Adds a message to the message queue
     * using a callback.
     *
     * @param text The text callback to use.
     * @param delay The delay in ticks to wait before sending the message.
     */
    fun queue(text: () -> String, delay: Int)
    /**
     * Adds a message to the message queue
     * using a callback.
     *
     * @param text The text callback to use.
     */
    fun queue(text: () -> String)

    /**
     * Adds a message to the message queue.
     *
     * @param text The text to use.
     * @param delay The delay in ticks to wait before sending the message.
     * @param callback The callback to when sending the message.
     */
    fun queue(text: String, delay: Int, callback: Consumer<String>)
    /**
     * Adds a message to the message queue.
     *
     * @param text The text to use.
     * @param callback The callback to when sending the message.
     */
    fun queue(text: String, callback: Consumer<String>)
    /**
     * Adds a message to the message queue.
     *
     * @param text The text to use.
     * @param delay The delay in ticks to wait before sending the message.
     */
    fun queue(text: String, delay: Int)
    /**
     * Adds a message to the message queue.
     *
     * @param text The text to use.
     */
    fun queue(text: String)
}
