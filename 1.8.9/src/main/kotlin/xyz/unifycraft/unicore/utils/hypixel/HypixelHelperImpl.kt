package xyz.unifycraft.unicore.utils.hypixel

import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ClientChatReceivedEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import okhttp3.Request
import xyz.deftu.deftils.Multithreading
import xyz.deftu.quicksocket.common.utils.isJson
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.events.HypixelApiKeyEvent
import xyz.unifycraft.unicore.api.utils.hypixel.HypixelGameType
import xyz.unifycraft.unicore.api.utils.hypixel.HypixelHelper
import xyz.unifycraft.unicore.api.utils.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class HypixelHelperImpl : HypixelHelper {
    override var apiKey: String = ""
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
        if (isHypixel() /* && UniCore.getConfig().saveHypixelApiKeys */) {
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
                        val failure = { UniCore.getNotifications().post(UniCore.getName(), "Failed to set up your Hypixel API key.") }
                        val str = body.string()
                        if (!str.isJson()) failure.invoke().also { return@use }
                        val raw = str.toJson()
                        if (!raw.isJsonObject) failure.invoke().also { return@use }
                        val json = raw.asJsonObject
                        if (!json.has("success")) failure.invoke().also { return@use }
                        val success = json.get("success")
                        if (!success.isJsonPrimitive) failure.invoke().also { return@use }
                        if (!success.asJsonPrimitive.asBoolean) failure.invoke().also { return@use }
                        this.apiKey = apiKey
                        UniCore.getEventBus().post(HypixelApiKeyEvent(apiKey))
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