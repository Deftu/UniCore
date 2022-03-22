package xyz.unifycraft.gradle.unibuild

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.jvm.tasks.Jar
import xyz.unifycraft.gradle.unibuild.utils.CommandLineHelper
import java.util.*

class UniBuildPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create("unibuild", UniBuildExtension::class.java)
        val logger = project.logger
        val version = project.version as String

        val gitBranch = fetchGitBranch(project, extension.gitBranchName.get())
        val gitHash = fetchGitHash(project, extension.gitHashName.get())

        project.tasks.register("applyBuildInfo") {
            it.group = "unibuild"
            logger.lifecycle("Applying build information to {}.", project.displayName)

            var modifiedArchiveName = false
            if (project.plugins.hasPlugin("base")) {
                val archivesBaseName = fetchArchivesBaseName(project)
                applyArchivesBaseName(
                    project,
                    archivesBaseName + (if (extension.hasMcInfo()) "-${extension.mcVersion.get()}-${extension.mcPlatform.get()}" else "")
                )
                modifiedArchiveName = true
            }
            project.version = "$version$gitBranch.$gitHash"

            logger.lifecycle(
                "Build information was applied to {}.\n" +
                        (if (modifiedArchiveName) "Archive name: ${fetchArchivesBaseName(project)}\n" else "") +
                        "Version: {}\n",
                project.displayName,
                project.version
            )
        }
    }

    fun fetchArchivesBaseName(project: Project) = (project.extensions.getByName("base") as BasePluginExtension).archivesBaseName
    fun applyArchivesBaseName(project: Project, value: String) = (project.extensions.getByName("base") as BasePluginExtension).apply {
        archivesBaseName = value
        project.extensions.extraProperties["buildName"] = value
    }

    companion object {
        private val branchExclusions = Arrays.asList(
            "main",
            "master"
        )

        fun fetchGitBranch(project: Project, name: String): String {
            return try {
                var branch = System.getenv(name)
                if (branch == null) branch = CommandLineHelper.fetchCommandOutput(project, "git", "rev-parse", "--abbrev-ref", "HEAD")
                if (branch != null && !branch.isEmpty() && !branchExclusions.contains(branch)) String.format(
                    "-%s",
                    branch
                ) else ""
            } catch (e: Exception) {
                ""
            }
        }

        fun fetchGitHash(project: Project, name: String): String {
            return try {
                var hash = System.getenv(name)
                if (hash == null) hash = CommandLineHelper.fetchCommandOutput(project, "git", "log", "-n", "1", "--pretty=tformat:%h")
                if (hash != null && !hash.isEmpty()) hash else "LOCAL"
            } catch (e: Exception) {
                ""
            }
        }
    }
}