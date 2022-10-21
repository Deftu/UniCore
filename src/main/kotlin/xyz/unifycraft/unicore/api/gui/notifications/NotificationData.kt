package xyz.unifycraft.unicore.api.gui.notifications

import java.awt.image.BufferedImage
import kotlin.random.Random

class NotificationBuilder {
    private lateinit var title: String
    private var content: String? = null
    private var image: NotificationImage? = null

    fun setTitle(title: String) = apply {
        this.title = title
    }

    fun setContent(content: String?) = apply {
        this.content = content
    }

    @JvmOverloads
    fun setImage(image: BufferedImage, location: NotificationImageLocation = NotificationImageLocation.LEFT) = apply {
        this.image = NotificationImage(image, location)
    }

    fun removeImage() {
        this.image = null
    }

    fun build(): NotificationData {
        if (!::title.isInitialized)
            throw IllegalArgumentException("Title has not been set!")
        return NotificationData(
            id = System.currentTimeMillis() + Random.nextLong(1, 9999),
            title = title,
            content = content,
            image = image
        )
    }
}

data class NotificationData internal constructor(
    val id: Long,
    val title: String,
    val content: String?,
    val image: NotificationImage?
)

data class NotificationImage(
    val image: BufferedImage,
    val location: NotificationImageLocation
)

enum class NotificationImageLocation {
    LEFT,
    RIGHT
}
