package cc.woverflow.onecore.api.aether.client

import org.java_websocket.handshake.ServerHandshake
import xyz.deftu.quicksocket.client.QuickSocketClient
import cc.woverflow.onecore.api.aether.auth.AetherAuth
import java.net.URI
import java.nio.ByteBuffer

/*
Aether WebsocketClient class.\
Not ready yet. May be deprecated or removed at any time.
 */
class AetherClient : QuickSocketClient(
    URI.create("ws://ws-aether.woverflow.cc")
) {
    override fun onConnectionOpened(handshake: ServerHandshake) {
        println("Connection opened!")
    }

    override fun onMessageReceived(message: String) {
        AetherAuth.parse(message)
    }

    override fun onMessageReceived(bytes: ByteBuffer) {
        AetherAuth.parseBytes(bytes)
    }


}