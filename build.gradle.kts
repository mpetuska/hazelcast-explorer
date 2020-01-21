plugins {
  val kotlinVersion = "1.3.60"
  id("kotlinx-serialization") version kotlinVersion apply false
  kotlin("multiplatform") version kotlinVersion apply false
  id("kotlin-dce-js") version kotlinVersion apply false
  idea
}

allprojects {
  apply(plugin = "idea")
  idea {
    module {
      isDownloadJavadoc = true
      isDownloadSources = true
    }
  }
  
  group = "lt.petuska"
  version = "1.0.0-SNAPSHOT"
}

tasks {
  val wrapper by getting(Wrapper::class) {
    gradleVersion = "6.1"
  }
}