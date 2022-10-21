package xyz.unifycraft.unicore.api.gui.notifications

/**
 * UniCore's notifications manager.
 */
interface Notifications {
    val history: List<NotificationData>

    /**
     * Adds a notification to the queue.
     */
    fun send(data: NotificationData)

    companion object {
        const val DEFAULT_DURATION = 5f

        @JvmStatic
        fun builder() = NotificationBuilder()
    }
}