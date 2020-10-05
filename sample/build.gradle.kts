plugins {
    kotlin("jvm")
    application
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

application {
    mainClassName = "io.ktor.server.cio.EngineMain"
}

dependencies {
    implementation(project(":hazelcast-explorer"))
}
