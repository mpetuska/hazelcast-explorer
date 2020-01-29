# Hazelcast Explorer

A multiplatform project, resulting in JVM library encapsulating backend and frontend to provide a quick hazelcast cluster tooling setup.
The library exposes a concise dsl to declare your hazelcast cluster details and uses that to create a web page for direct data access to 
all configured hazelcast targets via REST. On the backend it uses [Ktor server](https://ktor.io/servers), 
yet is exported as one of its modules and leaves all the Ktor configuration for the consumer.

## Setup
The project is published to [JCenter](http://jcenter.bintray.com), however it can also be obtained via its [Bintray repository](https://bintray.com/mpetuska/lt.petuska/kvdom).
To be able to access them, include either to your repositories:
```kotlin
repositories {
    // Either
    jcenter()
    // Or
    maven {
        url = uri("https://dl.bintray.com/mpetuska/lt.petuska")
    }
}
```

Then add the library to your project.
For multiplatform projects:
```kotlin
kotlin {
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation("lt.petuska:hazelcast-explorer:<VERSION>")
            }
        }
    }
}
```
Alternatively for JVM-only projects:
```kotlin
sourceSets["main"].withConvention(KotlinSourceSet::class) {
  dependencies {
    implementation("lt.petuska:hazelcast-explorer:<VERSION>")
  }
}
```

## Usage
For basic setup, please refer to the [Wiki](https://gitlab.com/lt.petuska/hazelcast-explorer/-/wikis/Setup)