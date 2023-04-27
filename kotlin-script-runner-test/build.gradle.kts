
plugins {
    id ("teamcity.environments")
}

extra["java11Home"] = project.findProperty("java11.home") ?: "/opt/jdk-11.0.2"

val plugins by configurations.creating

dependencies {
    plugins (project(path = ":kotlin-script-runner-server", configuration = "plugin"))
}

teamcity {
    environments {
        baseHomeDir = "${projectDir}/servers"
        baseDataDir = "${projectDir}/data"

        register("TeamCity2022.10") {
            version = "2022.10.3"
            javaHome = extra["java11Home"] as String
            plugins = configurations["plugins"]
        }
    }
}
