import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import xyz.unifycraft.gradle.utils.GameSide
import xyz.unifycraft.gradle.utils.disableRunConfigs
import xyz.unifycraft.gradle.utils.useMinecraftTweaker

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("java")
    id("net.kyori.blossom") version("1.3.0")
    id("xyz.unifycraft.gradle.multiversion")
    id("xyz.unifycraft.gradle.tools")
    id("xyz.unifycraft.gradle.snippets.shadow")
    id("maven-publish")
}

val projectVersion: String by project
version = projectVersion
val projectGroup: String by project
group = "${projectGroup}.api"

useMinecraftTweaker("xyz.unifycraft.unicore.api.mixins.UniCoreDevTweaker")
loom.disableRunConfigs(GameSide.GLOBAL)

blossom {
    replaceToken("__VERSION__", project.version)
}

dependencies {
    unishade("org.spongepowered:mixin:" + mapOf(
        11802 to "0.8.5-SNAPSHOT",
        11202 to "0.7.11-SNAPSHOT",
        10809 to "0.7.11-SNAPSHOT"
    )[mcData.version])

    // Kotlin language.
    unishade("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
    unishade("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Internal libraries, also serve as convenience for other mods.
    unishade("org.kodein.di:kodein-di:7.10.0")
    unishade(api("com.github.KevinPriv:keventbus:master-SNAPSHOT")!!)
    unishade("xyz.deftu.deftils:Deftils:1.2.2")
    unishade("com.github.ben-manes.caffeine:caffeine:2.9.3")
    unishade(api("org.java-websocket:Java-WebSocket:1.5.2")!!)
    unishade(api("xyz.deftu.quicksocket:QuickSocket:1.2.2")!!)
    unishade(api("com.squareup.okhttp3:okhttp:4.9.3")!!)
    unishade(api("xyz.unifycraft.configured:configured-${mcData.versionStr}-${mcData.loader.name}:1.0.0") {
        exclude(module = "kotlin-stdlib-jdk8")
        exclude(module = "kotlin-reflect")
        exclude(module = "elementa-${mcData.versionStr}-${mcData.loader.name}")
    })
    unishade(api("gg.essential:universalcraft-${mcData.versionStr}-${mcData.loader.name}:181") {
        exclude(module = "kotlin-stdlib-jdk8")
    })
    unishade(api("gg.essential:elementa-${mcData.versionStr}-${mcData.loader.name}:441") {
        exclude(module = "kotlin-stdlib-jdk8")
        exclude(module = "kotlin-reflect")
        exclude(module = "universalcraft-${mcData.versionStr}-${mcData.loader.name}")
    })

    // Convenience.
    unishade("me.nullicorn:Nedit:2.1.1")
    unishade("net.hypixel:hypixel-api-transport-reactor:4.2") {
        exclude(module = "gson")
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }

    named<Jar>("jar") {
        val projectName: String by project
        archiveBaseName.set("$projectName-${mcData.versionStr}-${mcData.loader.name}".toLowerCase())
    }

    named<ShadowJar>("unishadowJar") {
        exclude("LICENSE.md")
        exclude("pack.mcmeta")
        exclude("dummyThing")
        exclude("**/module-info.class")
        exclude("*.so")
        exclude("*.dylib")
        exclude("*.dll")
        exclude("*.jnilib")
        exclude("ibxm/**")
        exclude("com/jcraft/**")
        exclude("org/lwjgl/**")
        exclude("net/java/**")

        exclude("META-INF/proguard/**")
        exclude("META-INF/maven/**")
        exclude("META-INF/versions/**")
        exclude("META-INF/com.android.tools/**")

        exclude("**/*.kotlin_metadata")
        exclude("**/*.kotlin_builtins")
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                artifactId = (tasks["jar"] as Jar).archiveBaseName.get()
                group = projectGroup
                version = project.version.toString()

                artifact(tasks["unishadowJar"])
                artifact(tasks["sourcesJar"])
                artifact(tasks["javadocJar"])
            }
        }
    }
}
