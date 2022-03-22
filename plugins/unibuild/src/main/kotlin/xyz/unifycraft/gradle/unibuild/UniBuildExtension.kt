package xyz.unifycraft.gradle.unibuild

import org.gradle.api.provider.Property

abstract class UniBuildExtension {
    abstract val gitBranchName: Property<String>
    abstract val gitHashName: Property<String>
    abstract val mcVersion: Property<String>
    abstract val mcPlatform: Property<String>
    init {
        gitBranchName.convention("GITHUB_REF_NAME")
        gitHashName.convention("GIT_COMMIT")
        mcVersion.convention("")
        mcPlatform.convention("")
    }

    fun hasMcInfo() =
        mcVersion.isPresent && mcVersion.get().isNotEmpty() && mcPlatform.isPresent && mcPlatform.get().isNotEmpty()
}