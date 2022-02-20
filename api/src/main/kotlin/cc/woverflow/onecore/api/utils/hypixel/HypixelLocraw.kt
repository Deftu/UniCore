package cc.woverflow.onecore.api.utils.hypixel

import com.google.gson.annotations.SerializedName

class HypixelLocraw(
    @SerializedName("server") val serverId: String,
    @SerializedName("mode") val gameMode: String,
    @SerializedName("map") val mapName: String,
    @SerializedName("gametype") val gameTypeRaw: String
) {
    val gameType = HypixelGameType.from(gameTypeRaw)
    companion object {
        @JvmStatic val LIMBO = HypixelLocraw("limbo", "", "", "LIMBO")
    }
}

enum class HypixelGameType(
    val gameType: String
) {
    LIMBO("LIMBO"),
    BEDWARS("BEDWARS"),
    SKYWARS("SKYWARS"),
    PROTOTYPE("PROTOTYPE"),
    SKYBLOCK("SKYBLOCK"),
    MAIN("MAIN"),
    MURDER_MYSTERY("MURDER_MYSTERY"),
    HOUSING("HOUSING"),
    ARCADE_GAMES("ARCADE"),
    BUILD_BATTLE("BUILD_BATTLE"),
    DUELS("DUELS"),
    PIT("PIT"),
    UHC_CHAMPIONS("UHC"),
    SPEED_UHC("SPEED_UHC"),
    TNT_GAMES("TNTGAMES"),
    CLASSIC_GAMES("LEGACY"),
    COPS_AND_CRIMS("MCGO"),
    BLITZ_SG("SURVIVAL_GAMES"),
    MEGA_WALLS("WALLS3"),
    SMASH_HEROES("SUPER_SMASH"),
    WARLORDS("BATTLEGROUND"),

    UNKNOWN("");

    companion object {
        @JvmStatic fun from(gameType: String): HypixelGameType {
            for (value in values()) {
                if (value.gameType == gameType) {
                    return value
                }
            }

            return UNKNOWN
        }
    }
}