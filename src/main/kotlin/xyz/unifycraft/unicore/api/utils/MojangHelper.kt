package xyz.unifycraft.unicore.api.utils

import java.util.*

interface MojangHelper {
    fun toUuid(username: String, timestamp: Long): UUID?
    fun toUuid(username: String): UUID?
    fun fromUuid(uuid: UUID): String?
}