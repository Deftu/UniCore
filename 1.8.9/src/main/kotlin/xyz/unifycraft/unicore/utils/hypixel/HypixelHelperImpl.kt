package xyz.unifycraft.unicore.utils.hypixel

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import okhttp3.Request
import net.hypixel.api.HypixelAPI
import xyz.deftu.deftils.Multithreading
import xyz.deftu.quicksocket.common.utils.QuickSocketJsonHandler
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.events.HypixelApiKeyEvent
import xyz.unifycraft.unicore.api.utils.hypixel.HypixelGameType
import xyz.unifycraft.unicore.api.utils.hypixel.HypixelHelper
import xyz.unifycraft.unicore.api.utils.*
import java.util.UUID
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class HypixelHelperImpl : HypixelHelper {
    private var apiKey: String = ""
    override lateinit var hypixelApi: HypixelAPI
    override val locrawHelper = HypixelLocrawHelperImpl(this)

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    override fun isGameType(gameType: HypixelGameType) =
        isHypixel() && locrawHelper.locraw?.gameType == gameType

    override fun isHypixel(): Boolean {
        val serverBrand = "Hypixel BungeeCord"

        return if (Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.clientBrand != null) {
            val matcher = hypixelBrandPattern.matcher(Minecraft.getMinecraft().thePlayer.clientBrand)
            if (matcher.find()) {
                matcher.group(1).startsWith(serverBrand)
            } else {
                false
            }
        } else {
            false
        }
    }

    @SubscribeEvent
    fun onChatReceived(event: ClientChatReceivedEvent) {
        if (isHypixel()  && UniCore.getConfig().saveHypixelApiKeys) {
            val msg = event.message.unformattedText
            val matcher = hypixelApiKeyPattern.matcher(msg)
            if (!matcher.find()) return
            val apiKey = matcher.group("key")
            UniCore.getNotifications().post(UniCore.getName(), "Setting up your Hypixel API key... This will take a few seconds.")
            Multithreading.schedule({
                UniCore.getHttpRequester().request(Request.Builder()
                    .get()
                    .url("https://api.hypixel.net/key?key=$apiKey")
                    .build()) { response ->
                    response.body?.use { body ->
                        val failure = { reason: String -> UniCore.getNotifications().post(UniCore.getName(), "Failed to set up your Hypixel API key. ($reason)") }
                        val str = body.string()
                        if (!QuickSocketJsonHandler.parser.isValidJson(str)) failure.invoke("Hypixel provided an invalid JSON response.").also { return@use }
                        val raw = str.toJson()
                        if (!raw.isJsonObject) failure.invoke("Hypixel provided an invalid JSON type, expected object, got ${raw::class.java.simpleName}.").also { return@use }
                        val json = raw.asJsonObject
                        if (!json.has("success")) failure.invoke("Hypixel provided an invalid JSON response, missing API key status.").also { return@use }
                        val success = json.get("success")
                        if (!success.isJsonPrimitive) failure.invoke("Hypixel provided an invalid JSON response, API key status is invalid.").also { return@use }
                        if (!success.asJsonPrimitive.asBoolean) failure.invoke("Hypixel provided an invalid JSON response, API key status is not a boolean.").also { return@use }
                        this.apiKey = apiKey
                        this.hypixelApi = HypixelAPI(UUID.fromString(apiKey))
                        UniCore.getNotifications().post(UniCore.getName(), "Successfully set up your Hypixel API key.")
                    }
                }
            }, 3, TimeUnit.SECONDS)
        }
    }

    companion object {
        private val hypixelBrandPattern = Pattern.compile("(.+) <- (?:.+)")
        private val hypixelApiKeyPattern = Pattern.compile("Your new API key is (?<key>.*)")
    }
}