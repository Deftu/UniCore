package xyz.unifycraft.unicore.api.events.gui

abstract class NotificationEvent(
    val id: Long
) {
    class NotificationInteractEvent(id: Long) : NotificationEvent(id)
    class NotificationTimeoutEvent(id: Long) : NotificationEvent(id)
}
