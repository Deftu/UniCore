package xyz.unifycraft.unicore.api.utils.http

import xyz.unifycraft.unicore.api.UniCore
import java.io.BufferedInputStream
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

class SSLBuilder {
    private val factory = CertificateFactory.getInstance("X.509")
    private val keystore = KeyStore.getInstance(KeyStore.getDefaultType())

    init {
        keystore.load(Files.newInputStream(Paths.get(System.getProperty("java.home"), "lib", "security", "cacerts")), null)
    }

    /**
     * Loads the certificate into the keystore.
     *
     * @param name The name of the certificate.
     * @param path The path to the certificate.
     * @return The SSLBuilder instance.
     */
    fun load(name: String, path: String) = apply {
        UniCore::class.java.getResourceAsStream("/$path")?.use { input ->
            val buffer = BufferedInputStream(input)
            val certificate = factory.generateCertificate(buffer)
            keystore.setCertificateEntry(name, certificate)
        }
    }

    /**
     * Builds the SSLContext.
     *
     * @return The SSLContext built from the keystore.
     */
    fun build(): SSLContext {
        val trustFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustFactory.init(keystore)
        val context = SSLContext.getInstance("TLS")
        context.init(null, trustFactory.trustManagers, null)
        return context
    }
}