package xyz.unifycraft.unicore.api.utils

import java.util.*

/**
 * Utility class for Mojang's
 * API.
 */
interface MojangHelper {
    /**
     * Gets the UUID of a player.
     *
     * @param name The name of the player.
     * @return The UUID of the player or null if it doesn't exist.
     */
    fun toUuid(username: String, timestamp: Long): UUID?
    /**
     * Gets the UUID of a player.
     *
     * @param name The name of the player.
     * @return The UUID of the player or null if it doesn't exist.
     */
    fun toUuid(username: String): UUID?
    /**
     * Gets a player's name from
     * their UUID.
     *
     * @param uuid The UUID of the player.
     * @return The name of the player or null if it doesn't exist.
     */
    fun fromUuid(uuid: UUID): String?
}