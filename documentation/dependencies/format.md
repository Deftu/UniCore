<div align="center">

# `UniCore - Dependency Management - Format`

</div>

# Summary

The UniCore dependency management system uses it's own JSON format to get dependency information,
it is as follows:

## Maven dependencies
The `repository` field is optional, it will default to the central Maven repository.
```json
[
    {
        "maven": true,
        "repository": "https://repo.example.com/",
        "group": "com.example",
        "artifact": "Example",
        "version": "1.0.0"
    }
]
```

## JAR dependencies
```json
[
    {
        "maven": false,
        "url": "https://example.com/dependency.jar",
        "group": "com.example",
        "artifact": "Example",
        "version": "1.0.0"
    }
]
```