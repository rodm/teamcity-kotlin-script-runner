
import java.text.SimpleDateFormat
import java.util.Date
import com.github.jk1.license.render.JsonReportRenderer

plugins {
    id ("teamcity.base")
    id ("com.github.jk1.dependency-license-report") version "1.17"
}

val timestamp = SimpleDateFormat("yyMMdd_HHmm").format(Date())
val pluginVersion by extra(project.findProperty("PluginVersion") ?: "SNAPSHOT_${timestamp}")
version = pluginVersion

extra["teamcityVersion"] = project.findProperty("TeamCityVersion") ?: "2022.10"

teamcity {
    version = rootProject.extra["teamcityVersion"] as String
}

tasks.register<Copy>("pluginZip") {
    from("kotlin-script-runner-server/build/distributions/kotlin-script-runner.zip")
    into("build/distributions")
}

licenseReport {
    renderers = arrayOf(JsonReportRenderer("third-party-libs.json"))
}

