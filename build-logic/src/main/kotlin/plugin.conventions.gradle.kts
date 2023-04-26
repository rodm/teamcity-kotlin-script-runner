
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id ("org.jetbrains.kotlin.jvm")
}

group = "org.jetbrains.teamcity"

val defaultVersion = "SNAPSHOT_${SimpleDateFormat("yyMMdd_HHmm").format(Date())}"
version = project.findProject("PluginVersion") ?: defaultVersion

repositories {
    mavenCentral()
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    test {
        useTestNG()
    }
}
