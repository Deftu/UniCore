package xyz.unifycraft.unicore.api

import gg.essential.elementa.utils.ResourceCache
import gg.essential.universal.ChatColor
import me.kbrewster.eventbus.EventBus
import me.kbrewster.eventbus.Subscribe
import org.apache.logging.log4j.LogManager
import org.kodein.di.*
import xyz.deftu.deftils.Multithreader
import xyz.unifycraft.unicore.api.commands.CommandRegistry
import xyz.unifycraft.unicore.api.events.InitializationEvent
import xyz.unifycraft.unicore.api.gui.ElementaHud
import xyz.unifycraft.unicore.api.gui.notifications.Notifications
import xyz.unifycraft.unicore.api.keybinds.KeyBindRegistry
import xyz.unifycraft.unicore.api.utils.*
import xyz.unifycraft.unicore.api.utils.deleter.Deleter
import xyz.unifycraft.unicore.api.utils.http.HttpRequester
import xyz.unifycraft.unicore.api.utils.hypixel.HypixelHelper
import xyz.unifycraft.unicore.api.utils.updater.Updater

/**
 * The main class of the UniCore
 * API, this is where all the magic
 * happens.
 */
interface UniCore {
    fun initialize(event: InitializationEvent)
    fun withInstance(instance: UniCore)

    fun eventBus(): EventBus
    fun multithreader(): Multithreader

    fun fileHelper(): FileHelper
    fun config(): UniCoreConfig
    fun jsonHelper(): JsonHelper
    fun guiHelper(): GuiHelper
    fun chatHelper(): ChatHelper
    fun modLoaderHelper(): ModLoaderHelper
    fun elementaResourceCache(): ResourceCache
    fun elementaHud(): ElementaHud
    fun messageQueue(): MessageQueue
    fun notifications(): Notifications
    fun commandRegistry(): CommandRegistry
    fun keyBindRegistry(): KeyBindRegistry
    fun httpRequester(): HttpRequester
    fun deleter(): Deleter
    fun updater(): Updater
    fun mojangHelper(): MojangHelper
    fun hypixelHelper(): HypixelHelper
    fun colorHelper(): ColorHelper

    companion object {
        var initialized = false
            @JvmStatic get
            private set
        private val instance: UniCore by UniCoreDi.instance.instance()

        /**
         * Initializes UniCore and it's API.
         *
         * This should only be called internally!
         */
        @JvmStatic fun initialize(): Boolean {
            return if (!initialized) {
                instance.withInstance(instance)
                instance.eventBus().register(this)
                true.also { initialized = true }
            } else false
        }

        @Subscribe private fun onInitialize(event: InitializationEvent) = instance.initialize(event)

        /**
         * @return The current version of UniCore.
         */
        @JvmStatic fun getVersion() = "__VERSION__"
        /**
         * @return UniCore's name.
         */
        @JvmStatic fun getName() = "UniCore"
        /**
         * @return UniCore's mod ID.
         */
        @JvmStatic fun getId() = "unicore"

        private val chatPrefix = chatPrefix {
            name = getName()
            color = ChatColor.DARK_PURPLE
            brackets {
                type = ChatPrefixType.CARET
            }
        }
        /**
         * @return UniCore's chat prefix.
         */
        @JvmStatic fun getChatPrefix() = chatPrefix

        /**
         * @return UniCore's logger.
         */
        @JvmStatic fun getLogger() = LogManager.getLogger(getName())
        /**
         * @return The public event bus for UniCore's
         * event API.
         */
        @JvmStatic fun getEventBus() = instance.eventBus()
        /**
         * @return UniCore's multithreader instance
         * made using Deftils. This aides in executing
         * non-blocking tasks.
         */
        @JvmStatic fun getMultithreader() = instance.multithreader()

        /**
         * A file utility which provides easy access
         * to commonly used files. This also provides
         * access to UnifyCraft- and UniCore's internal
         * files.
         *
         * @return UniCore's file utility instance.
         */
        @JvmStatic fun getFileHelper() = instance.fileHelper()
        /**
         * Makes UniCore easily configurable.
         *
         * @return UniCore's configuration instance.
         */
        @JvmStatic fun getConfig() = instance.config()
        /**
         * A JSON utility which makes handling JSON
         * across multiple Minecraft versions much simpler.
         *
         * @return UniCore's JSON utility instance.
         */
        @JvmStatic fun getJsonHelper() = instance.jsonHelper()
        /**
         * A GUI utility which adds fixes to bugs
         * in legacy versions of the game.
         *
         * @return UniCore's GUI utility instance.
         */
        @JvmStatic fun getGuiHelper() = instance.guiHelper()
        /**
         * A chat utility which provides easy access
         * to commonly used chat functions and other
         * miscellaneous utilities.
         *
         * @return UniCore's chat utility instance.
         */
        @JvmStatic fun getChatHelper() = instance.chatHelper()
        /**
         * Provides an easy way to access a few
         * mod loader internals without having to
         * refer to the mod loader itself. This is
         * especially useful for cross-platform mods.
         *
         * @return UniCore's mod loader utility instance.
         */
        @JvmStatic fun getModLoaderHelper() = instance.modLoaderHelper()
        /**
         * An instance of an Elementa resource
         * cache made especially for UniCore.
         *
         * @return UniCore's Elementa resource cache.
         */
        @JvmStatic fun getElementaResourceCache() = instance.elementaResourceCache()
        /**
         * An instance of UniCore's
         * Elementa-based HUD system.
         *
         * @return UniCore's Elementa HUD instance.
         */
        @JvmStatic fun getElementaHud() = instance.elementaHud()
        /**
         * Provides UniCore's message queue utility,
         * which allows mods to queue messages to be
         * sent by the player to the currently open
         * (integrated) server.
         *
         * @return UniCore's message queue instance.
         */
        @JvmStatic fun getMessageQueue() = instance.messageQueue()
        /**
         * Provides UniCore's command registry,
         * which is based on JVM annotations
         * and makes creating commands much
         * simpler than what is built into
         * Minecraft.
         *
         * @return UniCore's command registry instance.
         */
        @JvmStatic fun getCommandRegistry() = instance.commandRegistry()
        /**
         * Provides UniCore's keybind registry,
         * which is based on JVM annotations
         * and makes creating keybinds much
         * simpler than what is built into
         * Minecraft.
         *
         * This also uses a custom GUI for
         * displaying and configuring keybinds.
         *
         * @return UniCore's keybind registry instance.
         */
        @JvmStatic fun getKeyBindRegistry() = instance.keyBindRegistry()
        /**
         * Provides UniCore's notification
         * manager, which allows you to
         * notify the user of anything
         * you'd like with ease.
         *
         * @return UniCore's notification manager instance.
         */
        @JvmStatic fun getNotifications() = instance.notifications()
        /**
         * Provides UniCore's HTTP requester,
         * which allows you to easily
         * request data from the internet
         * using OkHttp.
         *
         * @return UniCore's HTTP requester instance.
         */
        @JvmStatic fun getHttpRequester() = instance.httpRequester()
        /**
         * Gives you a simple utility
         * for deleting files and folders
         * without restrictions using the
         * user's OS's command-line tools.
         *
         * @return UniCore's deleter instance.
         */
        @JvmStatic fun getDeleter() = instance.deleter()
        /**
         * Gives you UniCore's instance
         * of it's mod updater. This updater
         * will notify the user of mod updates
         * based on what's in it's registry
         * and auto-update the mods at the user's
         * request.
         *
         * @return UniCore's updater instance.
         */
        @JvmStatic fun getUpdater() = instance.updater()
        /**
         * UniCore's Mojang API utility, which
         * allows you to easily request data
         * from the Mojang API.
         *
         * @return UniCore's Mojang API utility instance.
         */
        @JvmStatic fun getMojangHelper() = instance.mojangHelper()
        /**
         * Provides UniCore's Hypixel utility,
         * which is an easy gateway to Hypixel's
         * REST API and the player's current
         * Hypixel location data.
         *
         * @return UniCore's Hypixel utility instance.
         */
        @JvmStatic fun getHypixelHelper() = instance.hypixelHelper()
        /**
         * Provides UniCore's color utility,
         * which makes handling colors much
         * easier.
         *
         * @return UniCore's color utility instance.
         */
        @JvmStatic fun getColorHelper() = instance.colorHelper()
    }
}