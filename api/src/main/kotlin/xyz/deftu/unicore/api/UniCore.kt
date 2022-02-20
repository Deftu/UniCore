package xyz.deftu.unicore.api

import com.google.gson.Gson
import me.kbrewster.eventbus.EventBus
import me.kbrewster.eventbus.Subscribe
import org.apache.logging.log4j.Logger
import xyz.deftu.unicore.api.commands.CommandRegistry
import xyz.deftu.unicore.api.events.InitializationEvent
import xyz.deftu.unicore.api.gui.ElementaHud
import xyz.deftu.unicore.api.gui.notifications.Notifications
import xyz.deftu.unicore.api.keybinds.KeyBindRegistry
import xyz.deftu.unicore.api.utils.*
import xyz.deftu.unicore.api.utils.deleter.Deleter
import xyz.deftu.unicore.api.utils.http.HttpRequester
import xyz.deftu.unicore.api.utils.hypixel.HypixelHelper
import xyz.deftu.unicore.api.utils.updater.Updater
import java.util.*

interface UniCore {
    fun initialize(event: InitializationEvent)

    fun version() = "__VERSION__"
    fun name() = "UniCore"
    fun id() = "unicore"

    fun logger(): Logger
    fun gson(): Gson
    fun eventBus(): EventBus

    fun fileHelper(): FileHelper
    fun jsonHelper(): JsonHelper
    fun guiHelper(): GuiHelper
    fun elementaHud(): ElementaHud
    fun notifications(): Notifications
    fun commandRegistry(): CommandRegistry
    fun keyBindRegistry(): KeyBindRegistry
    fun httpRequester(): HttpRequester
    fun deleter(): Deleter
    fun updater(): Updater
    fun mojangHelper(): MojangHelper
    fun hypixelHelper(): HypixelHelper
    fun internetHelper(): InternetHelper
    fun colorHelper(): ColorHelper

    companion object {
        var initialized = false
            @JvmStatic get
            private set
        lateinit var instance: UniCore
            private set

        @JvmStatic fun initialize(): Boolean {
            return if (!initialized) {
                val service = ServiceLoader.load(UniCore::class.java)
                val iterator = service.iterator()
                if (iterator.hasNext()) {
                    instance = iterator.next()
                    if (iterator.hasNext()) throw IllegalStateException("There is more than one implementation, this is not supported.")
                } else throw IllegalStateException("Couldn't find implementation.")
                instance.eventBus().register(this)
                true.also { initialized = true }
            } else false
        }

        @Subscribe private fun onInitialize(event: InitializationEvent) = instance.initialize(event)

        @JvmStatic fun getVersion() = instance.version()
        @JvmStatic fun getName() = instance.name()
        @JvmStatic fun getId() = instance.id()

        @JvmStatic fun getLogger() = instance.logger()
        @JvmStatic fun getGson() = instance.gson()
        @JvmStatic fun getEventBus() = instance.eventBus()

        @JvmStatic fun getFileHelper() = instance.fileHelper()
        @JvmStatic fun getJsonHelper() = instance.jsonHelper()
        @JvmStatic fun getGuiHelper() = instance.guiHelper()
        @JvmStatic fun getElementaHud() = instance.elementaHud()
        @JvmStatic fun getCommandRegistry() = instance.commandRegistry()
        @JvmStatic fun getKeyBindRegistry() = instance.keyBindRegistry()
        @JvmStatic fun getNotifications() = instance.notifications()
        @JvmStatic fun getHttpRequester() = instance.httpRequester()
        @JvmStatic fun getDeleter() = instance.deleter()
        @JvmStatic fun getUpdater() = instance.updater()
        @JvmStatic fun getMojangHelper() = instance.mojangHelper()
        @JvmStatic fun getHypixelHelper() = instance.hypixelHelper()
        @JvmStatic fun getInternetHelper() = instance.internetHelper()
        @JvmStatic fun getColorHelper() = instance.colorHelper()
    }
}