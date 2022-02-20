package xyz.deftu.onecore.api.mixins

import net.minecraft.launchwrapper.ITweaker
import net.minecraft.launchwrapper.LaunchClassLoader
import java.io.File

class OneCoreDevTweaker : ITweaker {
    override fun acceptOptions(args: MutableList<String>?, gameDir: File?, assetsDir: File?, profile: String?) {
    }

    override fun injectIntoClassLoader(classLoader: LaunchClassLoader?) {
        OneCoreInitializer.initialize()
    }

    override fun getLaunchTarget() = "net.minecraft.client.main.Main"
    override fun getLaunchArguments() = emptyArray<String>()
}