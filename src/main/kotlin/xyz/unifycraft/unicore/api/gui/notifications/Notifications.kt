package xyz.unifycraft.unicore.api.gui.notifications

/**
 * UniCore's notifications manager.
 */
interface Notifications {
    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, description: String)
    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, description: String, duration: Float = DEFAULT_DURATION)
    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, description: String, click: Runnable)
    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, description: String, duration: Float = DEFAULT_DURATION, click: Runnable)

    companion object {
        const val DEFAULT_DURATION = 5f
    }
}