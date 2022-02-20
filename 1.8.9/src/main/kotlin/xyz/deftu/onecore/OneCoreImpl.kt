package xyz.deftu.onecore

import xyz.deftu.onecore.commands.CommandRegistryImpl
import xyz.deftu.onecore.commands.OneCoreCommand
import xyz.deftu.onecore.keybinds.KeyBindRegistryImpl
import xyz.deftu.onecore.utils.FileHelperImpl
import xyz.deftu.onecore.utils.http.HttpRequesterImpl
import xyz.deftu.onecore.utils.hypixel.HypixelHelperImpl
import com.google.gson.GsonBuilder
import me.kbrewster.eventbus.*
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import xyz.deftu.onecore.api.OneCore
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

@Mod(
    name = "OneCore",
    version = "__VERSION__",
    modid = "onecore",
    clientSideOnly = true
) class OneCoreImpl : OneCore {
    private val logger = LogManager.getLogger(name())
    private val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    private val eventBus = eventbus {  }

    private lateinit var fileHelper: FileHelper
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
    private lateinit var hypixelHelper: HypixelHelper
    private lateinit var internetHelper: InternetHelper
    private lateinit var colorHelper: ColorHelper

    override fun initialize(event: InitializationEvent) {
        MinecraftForge.EVENT_BUS.register(ForgeEventExtender())

        fileHelper = FileHelperImpl(event.gameDir)
        jsonHelper = JsonHelper()
        guiHelper = GuiHelper()
        elementaHud = ElementaHud().also { it.initialize() }
        notifications = Notifications()
        commandRegistry = CommandRegistryImpl().also { it.registerCommand(OneCoreCommand()) }
        keyBindRegistry = KeyBindRegistryImpl(fileHelper.dataDir)
        httpRequester = HttpRequesterImpl()
        deleter = Deleter(fileHelper.dataDir).also { it.initialize() }
        updater = Updater()
        mojangHelper = MojangHelper()
        hypixelHelper = HypixelHelperImpl()
        internetHelper = InternetHelper()
        colorHelper = ColorHelper()
    }

    override fun logger() = logger
    override fun gson() = gson
    override fun eventBus() = eventBus

    override fun fileHelper() = fileHelper
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
    override fun hypixelHelper() = hypixelHelper
    override fun internetHelper() = internetHelper
    override fun colorHelper() = colorHelper
}