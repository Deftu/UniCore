package xyz.unifycraft.unicore.api.gui.notifications

import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.gui.ofHud
import gg.essential.elementa.utils.ObservableRemoveEvent
import java.util.concurrent.LinkedBlockingDeque
import java.util.function.Consumer

class Notifications {
    private val namespace = "${UniCore.getName()} Notifications"
    private val queue = LinkedBlockingDeque<Notification>()

    init {
        if (created) throw IllegalStateException("The Notifications type has already been instantiated. Please use ${UniCore.getName()}'s instance.")
        created = true
        UniCore.getElementaHud().namespace(namespace).children.addObserver { _, event ->
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
        if (UniCore.getElementaHud().namespace(namespace).childrenOfType(Notification::class.java).isEmpty()) {
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