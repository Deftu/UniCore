package xyz.unifycraft.unicore.api

import com.google.gson.Gson
import gg.essential.elementa.utils.ResourceCache
import gg.essential.universal.ChatColor
import me.kbrewster.eventbus.EventBus
import me.kbrewster.eventbus.Subscribe
import org.apache.logging.log4j.LogManager
import org.kodein.di.*
import xyz.unifycraft.unicore.api.commands.CommandRegistry
import xyz.unifycraft.unicore.api.events.InitializationEvent
import xyz.unifycraft.unicore.api.gui.ElementaHud
import xyz.unifycraft.unicore.api.gui.hud.HudRegistry
import xyz.unifycraft.unicore.api.gui.notifications.Notifications
import xyz.unifycraft.unicore.api.keybinds.KeyBindRegistry
import xyz.unifycraft.unicore.api.utils.*
import xyz.unifycraft.unicore.api.utils.deleter.Deleter
import xyz.unifycraft.unicore.api.utils.http.HttpRequester
import xyz.unifycraft.unicore.api.utils.hypixel.HypixelHelper
import xyz.unifycraft.unicore.api.utils.updater.Updater

interface UniCore {
    fun initialize(event: InitializationEvent)
    fun withInstance(instance: UniCore)

    fun gson(): Gson
    fun eventBus(): EventBus

    fun fileHelper(): FileHelper
    fun config(): UniCoreConfig
    fun jsonHelper(): JsonHelper
    fun guiHelper(): GuiHelper
    fun elementaResourceCache(): ResourceCache
    fun elementaHud(): ElementaHud
    fun notifications(): Notifications
    fun commandRegistry(): CommandRegistry
    fun keyBindRegistry(): KeyBindRegistry
    fun hudRegistry(): HudRegistry
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
        private val instance: UniCore by UniCoreDi.instance.instance()

        @JvmStatic fun initialize(): Boolean {
            return if (!initialized) {
                instance.withInstance(instance)
                instance.eventBus().register(this)
                true.also { initialized = true }
            } else false
        }

        @Subscribe private fun onInitialize(event: InitializationEvent) = instance.initialize(event)

        @JvmStatic fun getVersion() = "__VERSION__"
        @JvmStatic fun getName() = "UniCore"
        @JvmStatic fun getId() = "unicore"

        private val chatPrefix = chatPrefix {
            name = getName()
            color = ChatColor.BLUE
            brackets {
                type = ChatPrefixType.CARET
            }
        }
        @JvmStatic fun getChatPrefix() = chatPrefix

        @JvmStatic fun getLogger() = LogManager.getLogger(getName())
        @JvmStatic fun getGson() = instance.gson()
        @JvmStatic fun getEventBus() = instance.eventBus()

        @JvmStatic fun getFileHelper() = instance.fileHelper()
        @JvmStatic fun getConfig() = instance.config()
        @JvmStatic fun getJsonHelper() = instance.jsonHelper()
        @JvmStatic fun getGuiHelper() = instance.guiHelper()
        @JvmStatic fun getElementaResourceCache() = instance.elementaResourceCache()
        @JvmStatic fun getElementaHud() = instance.elementaHud()
        @JvmStatic fun getCommandRegistry() = instance.commandRegistry()
        @JvmStatic fun getKeyBindRegistry() = instance.keyBindRegistry()
        @JvmStatic fun getHudRegistry() = instance.hudRegistry()
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