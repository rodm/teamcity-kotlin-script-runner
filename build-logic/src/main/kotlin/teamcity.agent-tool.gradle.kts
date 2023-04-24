
plugins {
    id ("io.github.rodm.teamcity-agent")
}

group = "org.jetbrains.teamcity"

artifacts {
    add("default", tasks.named("agentPlugin"))
}
