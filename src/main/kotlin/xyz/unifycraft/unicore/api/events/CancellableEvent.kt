package xyz.unifycraft.unicore.api.events

abstract class CancellableEvent : Event() {
    var cancelled: Boolean = false
}