package xyz.deftu.onecore.api

import com.google.gson.Gson
import me.kbrewster.eventbus.EventBus
import me.kbrewster.eventbus.Subscribe
import org.apache.logging.log4j.Logger
import xyz.deftu.onecore.api.commands.CommandRegistry
import xyz.deftu.onecore.api.events.InitializationEvent
import xyz.deftu.onecore.api.gui.ElementaHud
import xyz.deftu.onecore.api.gui.notifications.Notifications
import xyz.deftu.onecore.api.keybinds.KeyBindRegistry
import xyz.deftu.onecore.api.utils.*
import xyz.deftu.onecore.api.utils.deleter.Deleter
import xyz.deftu.onecore.api.utils.http.HttpRequester
import xyz.deftu.onecore.api.utils.hypixel.HypixelHelper
import xyz.deftu.onecore.api.utils.updater.Updater
import java.util.*

interface OneCore {
    fun initialize(event: InitializationEvent)

    fun version() = "__VERSION__"
    fun name() = "OneCore"
    fun id() = "onecore"

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
        lateinit var instance: OneCore
            private set

        @JvmStatic fun initialize(): Boolean {
            return if (!initialized) {
                val service = ServiceLoader.load(OneCore::class.java)
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