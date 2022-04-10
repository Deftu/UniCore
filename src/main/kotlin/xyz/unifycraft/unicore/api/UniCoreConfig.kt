package xyz.unifycraft.unicore.api

import xyz.unifycraft.confide.Confiding
import xyz.unifycraft.confide.data.type.switch
import java.io.File

class UniCoreConfig : Confiding(
    file = File(UniCore.getFileHelper().dataDir, "config.json"),
    title = UniCore.getName()
) {
    var updateCheck by switch(true) {
        name = "Update Check"
        description = "Should UniCore check for updates?"
    }
}