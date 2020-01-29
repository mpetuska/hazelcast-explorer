import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("com.github.johnrengelman.shadow") version "5.2.0"
  application
}

application {
  mainClassName = "io.ktor.server.cio.EngineMain"
}

dependencies {
  implementation(project(":hazelcast-explorer"))
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "1.8"
  }
}