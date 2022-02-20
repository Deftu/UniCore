package xyz.deftu.unicore

import xyz.deftu.unicore.commands.CommandRegistryImpl
import xyz.deftu.unicore.commands.UniCoreCommand
import xyz.deftu.unicore.keybinds.KeyBindRegistryImpl
import xyz.deftu.unicore.utils.FileHelperImpl
import xyz.deftu.unicore.utils.http.HttpRequesterImpl
import xyz.deftu.unicore.utils.hypixel.HypixelHelperImpl
import com.google.gson.GsonBuilder
import me.kbrewster.eventbus.*
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import xyz.deftu.unicore.api.UniCore
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

@Mod(
    name = "UniCore",
    version = "__VERSION__",
    modid = "unicore",
    clientSideOnly = true
) class UniCoreImpl : UniCore {
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
        commandRegistry = CommandRegistryImpl().also { it.registerCommand(UniCoreCommand()) }
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