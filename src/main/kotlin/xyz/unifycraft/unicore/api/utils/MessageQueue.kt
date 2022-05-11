package xyz.unifycraft.unicore.api.utils

import java.util.function.Consumer

interface MessageQueue {
    fun queue(text: () -> String, delay: Int, callback: Consumer<String>)
    fun queue(text: () -> String, callback: Consumer<String>)
    fun queue(text: () -> String, delay: Int)
    fun queue(text: () -> String)

    fun queue(text: String, delay: Int, callback: Consumer<String>)
    fun queue(text: String, callback: Consumer<String>)
    fun queue(text: String, delay: Int)
    fun queue(text: String)
}
