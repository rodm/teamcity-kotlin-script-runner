
plugins {
    id("teamcity.agent-tool")
}

val BUNDLED_TOOL_NAME = "kotlin.compiler.bundled"
val BUNDLED_TOOL_VERSION = "1.7.10"

repositories {
    ivy {
        url = uri("https://github.com/JetBrains/")
        patternLayout {
            artifact("[organisation]/releases/download/v[revision]/[artifact]-[revision].[ext]")
        }
        metadataSources {
            artifact()
        }
    }
}

val bundled: Configuration by configurations.creating

dependencies {
    bundled (group = "kotlin", name = "kotlin-compiler", version = BUNDLED_TOOL_VERSION, ext = "zip")
}

teamcity {
    agent {
        archiveName = BUNDLED_TOOL_NAME
        descriptor = "teamcity-plugin.xml"
        files {
            from(zipTree(configurations["bundled"].singleFile)) {
                includeEmptyDirs = false
                eachFile {
                    path = path.split(Regex.fromLiteral("/"), 2)[1]
                }
            }
        }
    }
}
