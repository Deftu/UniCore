package xyz.unifycraft.unicore.api

import xyz.unifycraft.confide.Confiding
import xyz.unifycraft.confide.data.annotation.Switch
import java.io.File

class UniCoreConfig : Confiding(
    file = File(UniCore.getFileHelper().dataDir, "config.json"),
    title = UniCore.getName()
) {
    @Switch("Save Hypixel API keys") var saveHypixelApiKeys = true
}