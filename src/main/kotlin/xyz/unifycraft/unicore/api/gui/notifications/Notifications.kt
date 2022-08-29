package xyz.unifycraft.unicore.api.gui.notifications

/**
 * UniCore's notifications manager.
 */
interface Notifications {
    val history: List<NotificationData>

    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, content: String)
    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, content: String, duration: Float = DEFAULT_DURATION)
    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, content: String, action: Runnable)
    /**
     * Adds a notification to the queue.
     */
    fun post(title: String, content: String, duration: Float = DEFAULT_DURATION, action: Runnable)

    companion object {
        const val DEFAULT_DURATION = 5f
    }
}