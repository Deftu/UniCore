pluginManagement {
    repositories {
        maven("https://repo.woverflow.cc/")
    }
    
    resolutionStrategy {
        eachPlugin {
            when(requested.id.id) {
                "org.spongepowered.mixin" -> useModule("com.github.Skytils:MixinGradle:${requested.version}")
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
    include(":api:$version")
    project(":api:$version").apply {
        projectDir = file("api/versions/$version")
        buildFileName = "../../version.gradle"
    }
}
