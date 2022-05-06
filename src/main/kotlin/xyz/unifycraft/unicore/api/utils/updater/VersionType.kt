package xyz.unifycraft.unicore.api.utils.updater

enum class VersionType(
    val type: String
) {
    RELEASE(""),
    BETA("beta"),
    ALPHA("alpha");

    fun isOf(vararg types: VersionType): Boolean {
        for (type in types) {
            if (this == type) {
                return true
            }
        }
        return false
    }

    fun compare(other: VersionType): Int {
        if (this == VersionType.RELEASE && other != VersionType.RELEASE)
            return -1
        if (this == VersionType.BETA && !other.isOf(VersionType.RELEASE, VersionType.BETA))
            return -1
        return 0
    }
}
