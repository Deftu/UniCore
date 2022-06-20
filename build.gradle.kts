plugins {
    kotlin("jvm") version("1.6.21") apply(false)
    id("xyz.unifycraft.gradle.multiversion-root") version("1.6.1-beta.2")
}

preprocess {
    val forge10809 = createNode("1.8.9-forge", 10809, "srg")
}
