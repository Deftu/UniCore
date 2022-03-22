package xyz.unifycraft.gradle.unishadow

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.jvm.tasks.Jar

class UniShadowPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val configuration = project.configurations.maybeCreate("shade")
        project.configurations.getByName("implementation").extendsFrom(configuration)
        project.tasks.getByName("shadowJar").doFirst { throw GradleException("Incorrect task! You're looking for unishadowJar") }
        val unishadowJarTask = project.tasks.register("unishadowJar", ShadowJar::class.java) { configureTask(project, configuration, it) }
        project.tasks.getByName("assemble").dependsOn(unishadowJarTask)
    }

    private fun configureTask(project: Project, configuration: Configuration, task: ShadowJar) {
        task.group = "unishadow"
        task.duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        task.configurations = listOf(configuration)

        val javaPlugin = project.convention.getPlugin(JavaPluginConvention::class.java)
        val jarTask = project.tasks.getByName("jar") as Jar

        task.manifest.inheritFrom(jarTask.manifest)
        val libsProvider = project.provider { listOf(jarTask.manifest.attributes["Class-Path"]) }
        val files = project.objects.fileCollection().from(configuration)
        task.doFirst {
            if (!files.isEmpty) {
                val libs = libsProvider.get().toMutableList()
                libs.addAll(files.map { it.name })
                task.manifest.attributes(mapOf("Class-Path" to libs.filterNotNull().joinToString(" ")))
            }
        }

        task.from(javaPlugin.sourceSets.getByName("main").output)
        task.exclude("META-INF/INDEX.LIST", "META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA", "module-info.class")

        if (project.plugins.hasPlugin("gg.essential.loom")) {
            val remapJar = project.tasks.getByName("remapJar") as Jar
            remapJar.archiveClassifier.set("remapped")
            task.dependsOn(remapJar)
            task.from(remapJar.archiveFile.get())
        }

        project.artifacts.add("shade", project.tasks.named("unishadowJar"))
    }
}