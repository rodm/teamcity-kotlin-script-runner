
plugins {
    id ("io.github.rodm.teamcity-base")
}

group = "org.jetbrains.teamcity"

teamcity {
    version = (project.findProperty("TeamCityVersion") ?: "2022.10") as String
}
