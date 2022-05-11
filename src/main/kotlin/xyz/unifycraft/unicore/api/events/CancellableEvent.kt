package xyz.unifycraft.unicore.api.events

/**
 * Base class for all
 * cancellable events.
 */
abstract class CancellableEvent : Event() {
    var cancelled: Boolean = false
}