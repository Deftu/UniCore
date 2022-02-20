package xyz.deftu.onecore.api.gui.notifications

import xyz.deftu.onecore.api.OneCore
import xyz.deftu.onecore.api.gui.ofHud
import gg.essential.elementa.utils.ObservableRemoveEvent
import java.util.concurrent.LinkedBlockingDeque
import java.util.function.Consumer

class Notifications {
    private val namespace = "${OneCore.getName()} Notifications"
    private val queue = LinkedBlockingDeque<Notification>()

    init {
        if (created) throw IllegalStateException("The Notifications type has already been instantiated. Please use OneCore's instance.")
        created = true
        OneCore.getElementaHud().namespace(namespace).children.addObserver { _, event ->
            if (event is ObservableRemoveEvent<*>) {
                val element = event.element
                if (element.value != null && element.value is Notification) {
                    val queued = queue.poll()
                    if (queued != null) post(queued)
                }
            }
        }
    }

    fun post(notification: Notification) {
        if (OneCore.getElementaHud().namespace(namespace).childrenOfType(Notification::class.java).isEmpty()) {
            notification ofHud namespace
        } else queue.add(notification)
    }

    @JvmOverloads fun post(
        title: String,
        description: String,
        duration: Float = 5f,
        click: Consumer<Notification> = Consumer {  }
    ) = post(Notification(title, description, duration, click))

    companion object {
        private var created = false
    }
}