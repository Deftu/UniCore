package xyz.unifycraft.unicore.api

import xyz.unifycraft.configured.Config
import xyz.unifycraft.configured.data.type.*
import java.io.File

class UniCoreConfig : Config(
    file = File(UniCore.getFileHelper().dataDir, "config.json"),
    title = UniCore.getName()
) {
    var updateCheck by switch(true) {
        name = "Update Check"
        description = "Should UniCore check for updates?"
    }
}