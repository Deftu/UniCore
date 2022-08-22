import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import xyz.unifycraft.gradle.utils.GameSide

plugins {
    kotlin("jvm")
    `java-library`
    `maven-publish`
    id("xyz.unifycraft.gradle.multiversion")
    id("xyz.unifycraft.gradle.tools")
    id("xyz.unifycraft.gradle.tools.shadow")
    id("xyz.unifycraft.gradle.tools.blossom")
    id("xyz.unifycraft.gradle.tools.dokka")
}

base.archivesName.set("${modData.name}-${mcData.versionStr}-${mcData.loader.name}".toLowerCase())

extensions.configure<JavaPluginExtension> {
    toolchain.languageVersion.set(JavaLanguageVersion.of("17"))
}

loomHelper {
    disableRunConfigs(GameSide.GLOBAL)
}

blossom {
    replaceToken("@DOCS@", "https://docs.unifycraft.xyz")
}

repositories {
    maven("https://repo.hypixel.net/repository/Hypixel/")
}

fun Dependency?.excludeVitals(): Dependency = apply {
    check(this is ModuleDependency)
    exclude(module = "kotlin-stdlib")
    exclude(module = "kotlin-stdlib-common")
    exclude(module = "kotlin-stdlib-jdk8")
    exclude(module = "kotlin-stdlib-jdk7")
    exclude(module = "kotlin-reflect")
    exclude(module = "annotations")
    exclude(module = "fabric-loader")
    exclude(module = "elementa-${mcData.versionStr}-${mcData.loader.name}")
    exclude(module = "universalcraft-${mcData.versionStr}-${mcData.loader.name}")
}!!

dependencies {
    unishade("org.spongepowered:mixin:" + mapOf(
        11802 to "0.8.5-SNAPSHOT",
        11202 to "0.7.11-SNAPSHOT",
        10809 to "0.7.11-SNAPSHOT"
    )[mcData.version])
    unishade(annotationProcessor("com.github.LlamaLad7:MixinExtras:${libs.versions.mixinExtras.get()}")!!)

    // Language
    unishade(kotlin("stdlib"))
    unishade("org.jetbrains.kotlinx:kotlinx-coroutines-core:${libs.versions.kotlinxCoroutines.get()}")
    unishade("org.kodein.di:kodein-di:${libs.versions.kodein.get()}")
    unishade("org.kodein.di:kodein-di-jvm:${libs.versions.kodein.get()}")

    // Internal server
    unishade(api("org.java-websocket:Java-WebSocket:${libs.versions.websocket.get()}")!!)
    unishade(api("xyz.deftu.quicksocket:QuickSocket:${libs.versions.quicksocket.get()}")!!)
    unishade(api("com.squareup.okhttp3:okhttp:${libs.versions.okhttp.get()}")!!)

    // UI
    unishade(modApi(libs.versions.configured.map {
        "xyz.unifycraft.configured:configured-${mcData.versionStr}-${mcData.loader.name}:$it"
    }.get()).excludeVitals())
    unishade(modApi(libs.versions.universalcraft.map {
        "gg.essential:universalcraft-${mcData.versionStr}-${mcData.loader.name}:$it"
    }.get()).excludeVitals())
    unishade(modApi(libs.versions.elementa.map {
        "gg.essential:elementa-${mcData.versionStr}-${mcData.loader.name}:$it"
    }.get()).excludeVitals())

    // Utility
    unishade("xyz.unifycraft:UEventBus:${libs.versions.ueventbus.get()}")
    unishade(api("xyz.deftu.deftils:Deftils:${libs.versions.deftils.get()}")!!)
    unishade("com.github.ben-manes.caffeine:caffeine:${libs.versions.caffeine.get()}")
    unishade("me.nullicorn:Nedit:${libs.versions.nedit.get()}")
    unishade("net.hypixel:hypixel-api-transport-reactor:${libs.versions.hypixelapi.get()}") {
        exclude(module = "gson")
    }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
        }
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

afterEvaluate {
    publishing.publications.getByName<MavenPublication>("mavenJava") {
        group = modData.group
        artifactId = base.archivesName.get()
        version = modData.version
    }
}
