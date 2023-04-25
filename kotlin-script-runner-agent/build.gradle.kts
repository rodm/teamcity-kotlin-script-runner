
plugins {
    id ("teamcity.agent-plugin")
}

version = rootProject.version

dependencies {
    implementation(project(":kotlin-script-runner-common"))
    provided("org.jetbrains.teamcity.internal:agent:${teamcity.version}")

    testImplementation("io.mockk:mockk:1.10.0")
}

teamcity {
    agent {
        descriptor {
            pluginDeployment {
                useSeparateClassloader = true
            }
        }
    }
}
