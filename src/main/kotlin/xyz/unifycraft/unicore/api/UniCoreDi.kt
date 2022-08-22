package xyz.unifycraft.unicore.api

import org.kodein.di.DIAware

abstract class UniCoreDi : DIAware {
    companion object {
        @JvmStatic lateinit var instance: UniCoreDi
            private set
        @JvmStatic fun initialize(instance: UniCoreDi) {
            this.instance = instance
        }
    }
}
