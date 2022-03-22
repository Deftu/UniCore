package xyz.unifycraft.gradle.unisigning

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.plugins.signing.SigningExtension

class UniSigningPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        if (!canPerformSigning) return
        val logger = project.logger
        logger.lifecycle("Performing signing checks for ${project.displayName}.")

        fun disableSigining(reason: String) {
            logger.warn("$reason, cannot perform signing.")
            canPerformSigning = false
        }

        if (!project.hasProperty("signing.secretKeyRingFile") && project.hasProperty("pgpKeyRing")) {
            val keyRing = project.file(project.property("pgpKeyRing")!!)
            if (keyRing.exists() && keyRing.name.endsWith(".gpg")) {
                project.extensions.extraProperties["signing.secretKeyRingFile"] = keyRing.absolutePath
            } else logger.warn("Failed to load PGP keyring from fallback property.")
        }

        if (!project.hasProperty("signing.secretKeyRingFile")) disableSigining("PGP keyring file not present")
        if (!project.hasProperty("signing.keyId")) disableSigining("PGP key ID not present")
        if (!project.hasProperty("signing.password")) disableSigining("PGP password not present")

        if (canPerformSigning) {
            project.plugins.apply("signing")
            project.extensions.configure<SigningExtension>("signing") {
                logger.lifecycle("Artifacts will be signed using PGP.\n")
                it.sign((project.extensions.getByName("publishing") as PublishingExtension).publications)
            }
        } else logger.lifecycle("Artifacts will NOT be signed.\n")
    }

    companion object {
        private var canPerformSigning = true
    }
}