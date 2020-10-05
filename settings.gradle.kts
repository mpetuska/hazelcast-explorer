pluginManagement {
    repositories {
        mavenCentral()
        jcenter()
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://kotlin.bintray.com/kotlinx")
        gradlePluginPortal()
        mavenLocal()
    }
}

rootProject.name = "hazelcast-explorer-root"

include(":hazelcast-explorer")
include(":hazelcast-explorer:client")
include(":sample")
