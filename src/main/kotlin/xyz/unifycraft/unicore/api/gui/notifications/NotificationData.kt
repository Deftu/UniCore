package xyz.unifycraft.unicore.api.gui.notifications

data class NotificationData(
    val title: String,
    val content: String,
    val action: Runnable
)
