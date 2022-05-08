package xyz.unifycraft.unicore.api.utils

import gg.essential.universal.UDesktop
import java.net.URI

interface InternetHelper {
    /* Things to be added when needed. */
}

/**
 * Open a website URL in the user's web browser.
 * @param url website URL
 * @return Whether opening succeeded.
 */
fun UDesktop.browseUrl(url: String) = browse(URI.create(url))

/**
 * Open a website URL in the user's web browser.
 *
 * Runs inside a try-catch to avoid errors.
 *
 * @param url website URL
 * @return Whether opening succeeded.
 */
fun UDesktop.browseUrlSafe(url: String) = runCatching { browseUrl(url) }
