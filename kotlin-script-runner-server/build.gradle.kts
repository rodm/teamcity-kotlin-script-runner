
plugins {
    id ("teamcity.server-plugin")
}

version = rootProject.version

val bundled: Configuration by configurations.creating

dependencies {
    agent(project(path = ":kotlin-script-runner-agent", configuration = "plugin"))
    bundled (project(":kotlin-script-runner-tool"))
    implementation(project(":kotlin-script-runner-common"))
    implementation(kotlin("stdlib"))
    provided("org.jetbrains.teamcity.internal:server:${rootProject.extra["teamcityVersion"]}")
    provided("org.jetbrains.teamcity.internal:server-tools:${rootProject.extra["teamcityVersion"]}")
}

teamcity {
    server {
        archiveName = "kotlin-script-runner"
        descriptor = file("../teamcity-plugin.xml")
        tokens = mapOf("Version" to project.version)

        files {
            into("bundled") {
                from(bundled)
            }
        }
        files {
            into("kotlin-dsl") {
                from("kotlin-dsl")
            }
        }
    }
}
