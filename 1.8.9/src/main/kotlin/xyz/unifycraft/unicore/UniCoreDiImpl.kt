package xyz.unifycraft.unicore

import org.kodein.di.*
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.UniCoreDi

class UniCoreDiImpl : UniCoreDi() {
    override val di = DI.invoke {
        bindSingleton<UniCore> { UniCoreImpl() }
    }
}