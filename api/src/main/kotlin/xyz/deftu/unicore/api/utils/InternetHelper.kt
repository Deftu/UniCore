package xyz.deftu.unicore.api.utils

import gg.essential.universal.UDesktop
import java.net.URI

class InternetHelper {
    /* Things to be added when needed. */
}

/**
 * Open a website URL in the user's web browser.
 * @param url website URL
 * @return Whether opening succeeded.
 */
fun UDesktop.browseUrl(url: String) = browse(URI.create(url))