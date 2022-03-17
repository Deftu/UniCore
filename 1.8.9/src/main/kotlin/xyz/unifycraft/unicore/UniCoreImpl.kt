package xyz.unifycraft.unicore

import xyz.unifycraft.unicore.commands.CommandRegistryImpl
import xyz.unifycraft.unicore.commands.UniCoreCommand
import xyz.unifycraft.unicore.keybinds.KeyBindRegistryImpl
import xyz.unifycraft.unicore.utils.FileHelperImpl
import xyz.unifycraft.unicore.utils.http.HttpRequesterImpl
import xyz.unifycraft.unicore.utils.hypixel.HypixelHelperImpl
import com.google.gson.GsonBuilder
import gg.essential.universal.wrappers.UPlayer
import me.kbrewster.eventbus.*
import net.minecraft.client.Minecraft
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.ProgressManager
import org.apache.logging.log4j.LogManager
import xyz.deftu.quicksocket.common.utils.QuickSocketJsonHandler
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.UniCoreConfig
import xyz.unifycraft.unicore.api.commands.CommandRegistry
import xyz.unifycraft.unicore.api.events.InitializationEvent
import xyz.unifycraft.unicore.api.gui.ElementaHud
import xyz.unifycraft.unicore.api.gui.notifications.Notifications
import xyz.unifycraft.unicore.api.keybinds.KeyBindRegistry
import xyz.unifycraft.unicore.api.utils.*
import xyz.unifycraft.unicore.api.utils.deleter.Deleter
import xyz.unifycraft.unicore.api.utils.http.HttpRequester
import xyz.unifycraft.unicore.api.utils.hypixel.HypixelHelper
import xyz.unifycraft.unicore.api.utils.language.TranslationRegistry
import xyz.unifycraft.unicore.api.utils.updater.Updater
import xyz.unifycraft.unicore.cloud.CloudConnection
import xyz.unifycraft.unicore.quicksocket.JsonParser
import xyz.unifycraft.unicore.utils.language.TranslationRegistryImpl
import xyz.unifycraft.unicore.utils.updater.UpdaterEventListener
import java.util.UUID

@Mod(
    name = "UniCore",
    version = "__VERSION__",
    modid = "unicore",
    clientSideOnly = true
) class UniCoreImpl : UniCore {
    private val logger = LogManager.getLogger(UniCore.getName())
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    private val eventBus = eventbus {  }

    private lateinit var fileHelper: FileHelper
    private lateinit var config: UniCoreConfig
    private lateinit var jsonHelper: JsonHelper
    private lateinit var guiHelper: GuiHelper
    private lateinit var elementaHud: ElementaHud
    private lateinit var notifications: Notifications
    private lateinit var commandRegistry: CommandRegistry
    private lateinit var keyBindRegistry: KeyBindRegistry
    private lateinit var httpRequester: HttpRequester
    private lateinit var deleter: Deleter
    private lateinit var updater: Updater
    private lateinit var mojangHelper: MojangHelper
    private lateinit var translationRegistry: TranslationRegistry
    private lateinit var hypixelHelper: HypixelHelper
    private lateinit var internetHelper: InternetHelper
    private lateinit var colorHelper: ColorHelper

    private lateinit var cloudConnection: CloudConnection

    override fun initialize(event: InitializationEvent) {
        val progressBar = ProgressManager.push(UniCore.getName(), 3)
        logger.info("Hello, Minecraft!")
        progressBar.step("Extending Forge events")
        listOf(
            ForgeEventExtender(),
            UpdaterEventListener()
        ).forEach(MinecraftForge.EVENT_BUS::register)

        progressBar.step("Initializing APIs")
        fileHelper = FileHelperImpl(event.gameDir)
        config = UniCoreConfig().also { it.initialize() }
        jsonHelper = JsonHelper()
        guiHelper = GuiHelper()
        elementaHud = ElementaHud().also { it.initialize() }
        notifications = Notifications()
        commandRegistry = CommandRegistryImpl().also { it.registerCommand(UniCoreCommand()) }
        keyBindRegistry = KeyBindRegistryImpl(fileHelper.dataDir)
        httpRequester = HttpRequesterImpl()
        deleter = Deleter(fileHelper.dataDir).also { it.initialize() }
        updater = Updater()
        mojangHelper = MojangHelper()
        translationRegistry = TranslationRegistryImpl()
        hypixelHelper = HypixelHelperImpl()
        internetHelper = InternetHelper()
        colorHelper = ColorHelper()

        progressBar.step("Connecting to the UniCore Cloud")
        QuickSocketJsonHandler.applyJsonParser(JsonParser())
        cloudConnection = CloudConnection(
            sessionId = UUID.randomUUID(),
            headers = arrayOf("uuid" to Minecraft.getMinecraft().session.profile.id.toString())
        ).apply {
            connectBlocking()
        }
    }

    override fun withInstance(instance: UniCore) {
        UniCoreImpl.instance = instance as UniCoreImpl
    }

    override fun logger() = logger
    override fun gson() = gson
    override fun eventBus() = eventBus

    override fun fileHelper() = fileHelper
    override fun config() = config
    override fun jsonHelper() = jsonHelper
    override fun guiHelper() = guiHelper
    override fun elementaHud() = elementaHud
    override fun notifications() = notifications
    override fun commandRegistry() = commandRegistry
    override fun keyBindRegistry() = keyBindRegistry
    override fun httpRequester() = httpRequester
    override fun deleter() = deleter
    override fun updater() = updater
    override fun mojangHelper() = mojangHelper
    override fun translationRegistry() = translationRegistry
    override fun hypixelHelper() = hypixelHelper
    override fun internetHelper() = internetHelper
    override fun colorHelper() = colorHelper

    fun cloudConnection() = cloudConnection

    companion object {
        lateinit var instance: UniCoreImpl
            private set
    }
}