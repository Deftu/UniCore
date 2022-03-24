package xyz.unifycraft.unicore.api.mixins

import net.minecraft.launchwrapper.ITweaker
import net.minecraft.launchwrapper.LaunchClassLoader
import java.io.File

class UniCoreDevTweaker : ITweaker {
    override fun acceptOptions(args: MutableList<String>?, gameDir: File?, assetsDir: File?, profile: String?) {
    }

    override fun injectIntoClassLoader(classLoader: LaunchClassLoader?) {
        UniCoreInitializer.initialize()
    }

    override fun getLaunchTarget() = "net.minecraft.client.main.Main"
    override fun getLaunchArguments() = emptyArray<String>()
}