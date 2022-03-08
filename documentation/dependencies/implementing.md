<div align="center">

# `UniCore - Dependency Management - Implementing`

</div>

# Summary
The UniCore dependency management system can be implemented in two ways: manually and automatically.

## Manually
To implement your dependencies manually, you will need to create a `dependencies.json` 
file in your resources directory, and put your dependencies both in your Gradle
dependencies block and inside this JSON file.

## Automatically
To implement your dependencies automatically, you can use the dependency management
Gradle plugin, more on that plugin can be found [here](plugin.md).