package xyz.unifycraft.unicore.api

import org.kodein.di.DIAware

abstract class UniCoreDi : DIAware {
    companion object {
        @JvmStatic lateinit var instance: UniCoreDi
            private set
        @JvmStatic fun initialize(instance: UniCoreDi) {
            if (::instance.isInitialized)
                throw IllegalStateException("Cannot re-initialize UniCoreDi instance.")

            this.instance = instance
        }
    }
}
