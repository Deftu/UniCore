package xyz.unifycraft.unicore.api

import xyz.unifycraft.configured.Config
import java.io.File

class UniCoreConfig : Config(
    directory = File(UniCore.getFileHelper().dataDir, "config"),
    title = UniCore.getName()
)
