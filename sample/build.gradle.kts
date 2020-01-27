import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("com.github.johnrengelman.shadow") version "5.2.0"
  application
}

application {
  mainClassName = "io.ktor.server.cio.EngineMain"
}

sourceSets["main"].withConvention(KotlinSourceSet::class) {
  dependencies {
    implementation(project(":hazelcast-explorer"))
  }
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "1.8"
  }
}