package cc.woverflow.onecore.api.aether.auth

import cc.woverflow.onecore.api.aether.auth.AetherAuth
import org.java_websocket.protocols.Protocol

/*
Parses the data before any other class is called.
Uses the AetherAuth object to return the parsed tokenized data.
Not ready yet. May be deprecated or removed at any time.
@returns Parsed aerData
 */
class AetherProtocol constructor(
    aerData: String,
    providedProtocol: String
) : Protocol(providedProtocol) {
    fun aerMessage(aerData: String) {
        return AetherAuth.parse(aerData)
    }
}