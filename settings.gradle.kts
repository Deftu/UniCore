pluginManagement {
    file("plugins").listFiles().filter {
        it != null && it.isDirectory
    }.forEach {
        includeBuild(it)
    }

    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        maven("https://jitpack.io/")
        maven("https://maven.architectury.dev/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.minecraftforge.net/")
    }
    
    resolutionStrategy {
        eachPlugin {
            when(requested.id.id) {
                "com.replaymod.preprocess" -> useModule("com.github.replaymod:preprocessor:${requested.version}")
                "com.replaymod.preprocess-root" -> useModule("com.github.replaymod:preprocessor:${requested.version}")
            }
        }
    }
}

rootProject.name = "UniCore"

listOf(
    "1.8.9"
).forEach { version ->
    include(":$version")
    project(":$version").apply {
        projectDir = file("versions/$version")
        buildFileName = "../../version.gradle"
    }
}
