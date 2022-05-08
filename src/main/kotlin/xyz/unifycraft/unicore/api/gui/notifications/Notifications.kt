package xyz.unifycraft.unicore.api.gui.notifications

interface Notifications {
    fun post(title: String, description: String)
    fun post(title: String, description: String, duration: Float = DEFAULT_DURATION)
    fun post(title: String, description: String, duration: Float = DEFAULT_DURATION, click: Runnable)

    companion object {
        const val DEFAULT_DURATION = 5f
    }
}