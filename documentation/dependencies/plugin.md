<div align="center">

# `UniCore - Dependency Management - Plugin`

</div>

# Summary
A Gradle plugin was developed for the use with UniCore's dependency management systems,
this will automatically generate a `dependencies.json` file for you.

Implementing it can be seen below:

## Groovy
```gradle
plugins {
    id "xyz.unifycraft.unicore.dependencies" version "{VERSION}"
}

configurations {
    depend
    implementation.extendsFrom(depend)
}

dependencyManagement {
    configurations = [project.configurations.depend]
}

dependencies {
    depend("com.example:Example:1.0.0")
}
```