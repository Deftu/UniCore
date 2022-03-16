package xyz.unifycraft.unicore.cloud

import com.google.gson.JsonObject
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.LogManager
import org.java_websocket.handshake.ServerHandshake
import xyz.deftu.quicksocket.client.QuickSocketClient
import xyz.deftu.quicksocket.common.packets.PacketBase
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.UniCoreEnvironment
import java.util.UUID

class CloudConnection(
    private var sessionId: UUID,
    vararg headers: Pair<String, String>
) : QuickSocketClient(
    uri = UniCoreEnvironment.connectionUri,
    headers = (headers.toMap() + mapOf(proposedSessionIdName to sessionId.toString()))
) {
    private val logger = LogManager.getLogger("${UniCore.getName()} (Cloud Connection)")

    override fun onConnectionOpened(handshake: ServerHandshake) {
        if (!handshake.hasFieldValue(acceptedSessionIdName)) throw IllegalConnectionException("The connection that the UniCore cloud connected to did not provide a session ID.")
        val acceptedSessionId = handshake.getFieldValue(acceptedSessionIdName).toBoolean()
        if (!acceptedSessionId) runBlocking {
            if (!handshake.hasFieldValue(sessionIdName)) throw IllegalArgumentException("The connection did not accept the proposed session ID, but it did not provide a new one.")
            sessionId = UUID.fromString(handshake.getFieldValue(sessionIdName))
        }

        logger.info("Successfully established connection to ${UniCore.getName()} cloud!")
    }

    override fun onPacketSent(packet: PacketBase, data: JsonObject) =
        data.addProperty(sessionIdName, sessionId.toString())

    companion object {
        const val proposedSessionIdName = "proposed_session_id"
        const val acceptedSessionIdName = "accepted_session_id"
        const val sessionIdName = "session_id"
    }
}