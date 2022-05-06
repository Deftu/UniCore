package xyz.unifycraft.unicore.api.utils.updater

data class UpdateVersion(
    val version: String
): Comparable<UpdateVersion> {
    val versionType = VersionType.values().firstOrNull {
        version.contains(it.type, true)
    } ?: VersionType.RELEASE
    override fun compareTo(other: UpdateVersion): Int {
        return if (version.compareTo(other.version) == -1) {
            versionType.compare(other.versionType)
        } else -1
    }
}
