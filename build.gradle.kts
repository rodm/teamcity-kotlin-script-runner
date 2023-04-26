
import com.github.jk1.license.render.JsonReportRenderer

plugins {
    id ("teamcity.base")
    id ("com.github.jk1.dependency-license-report") version "1.17"
}

tasks.register<Copy>("pluginZip") {
    from("kotlin-script-runner-server/build/distributions/kotlin-script-runner.zip")
    into("build/distributions")
}

licenseReport {
    renderers = arrayOf(JsonReportRenderer("third-party-libs.json"))
}

