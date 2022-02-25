package xyz.unifycraft.unicore.utils.hypixel

import net.minecraft.client.Minecraft
import xyz.deftu.unicore.api.utils.hypixel.HypixelHelper
import java.util.regex.Pattern

class HypixelHelperImpl : HypixelHelper {
    override val apiKey: String
        get() = TODO("Not yet implemented")
    override val locrawHelper = HypixelLocrawHelperImpl(this)

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

    companion object {
        private val hypixelBrandPattern = Pattern.compile("(.+) <- (?:.+)")
    }
}