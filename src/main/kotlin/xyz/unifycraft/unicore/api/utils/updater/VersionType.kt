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
        if (this == RELEASE && other != RELEASE)
            return -1
        if (this == BETA && !other.isOf(RELEASE, BETA))
            return -1
        return 0
    }
}
