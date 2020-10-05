import org.jetbrains.dokka.utilities.cast
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    kotlin("plugin.serialization") version "1.4.10"
    `maven-publish`
}

object Version {
    const val ktor = "1.4.0"
    const val logback = "1.2.3"
    const val kodein = "6.5.5"
}

val Version.hazelcast: String
    get() = project.properties.getOrDefault("hazelcastVersion", "3.8.6").toString()
version = "${rootProject.version}-hz-${Version.hazelcast}"

dependencies {
    api(project("${project.path}:client")) {
        // exclude("com.hazelcast")
    }
    api("io.ktor:ktor-server-cio:${Version.ktor}")
    api("io.ktor:ktor-websockets:${Version.ktor}")
    api("io.ktor:ktor-gson:${Version.ktor}")
    api("io.ktor:ktor-serialization:${Version.ktor}")
    api("ch.qos.logback:logback-classic:${Version.logback}")
    api("org.kodein.di:kodein-di-generic-jvm:${Version.kodein}")
    api("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Version.kodein}")
    // api("com.hazelcast:hazelcast-client:${Version.hazelcast}")
}

kotlin {
    explicitApi()
    sourceSets.all {
        languageSettings.apply {
            useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
//         // useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
//         // useExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")
//         useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
            useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
        }
    }
}
// kotlin {
// //  explicitApiWarning()
//   jvm()
//
//   sourceSets {
//     val commonMain by getting {
//       dependencies {
//         api("io.ktor:ktor-client-serialization:${Version.ktor}")
//       }
//     }
//     val jvmMain by getting {
//       dependencies {
//         api("io.ktor:ktor-server-cio:${Version.ktor}")
//         api("io.ktor:ktor-websockets:${Version.ktor}")
//         api("io.ktor:ktor-gson:${Version.ktor}")
//         api("io.ktor:ktor-serialization:${Version.ktor}")
//         api("ch.qos.logback:logback-classic:${Version.logback}")
//         api("org.kodein.di:kodein-di-generic-jvm:${Version.kodein}")
//         api("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Version.kodein}")
//         api("com.hazelcast:hazelcast-client:${Version.hazelcast}")
//       }
//     }
//     val jsMain by getting {
//       dependencies {
//         // implementation("pl.treksoft:kvision-bootstrap:${Version.kvision}")
//         // implementation("pl.treksoft:kvision-fontawesome:${Version.kvision}")
//         // implementation("pl.treksoft:kvision-redux-kotlin:${Version.kvision}")
//         // implementation(npm("bootstrap4-toggle", "3.6.1"))
// //        implementation("io.ktor:ktor-client-serialization-js:${Version.ktor}")
// //         implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.110-kotlin-1.4.10")
// //         implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.110-kotlin-1.4.10")
// //         implementation("org.jetbrains:kotlin-styled:1.0.0-pre.110-kotlin-1.4.10")
// //        implementation(npm("core-js"))
// //        implementation(npm("react"))
// //        implementation(npm("react-dom"))
// //        implementation(npm("redux"))
// //        implementation(npm("react-redux"))
// //        implementation(npm("styled-components"))
// //        implementation(npm("inline-style-prefixer"))
// //        implementation(npm("react-json-editor-ajrm"))
// //        implementation(npm("text-encoding"))
// //        implementation(npm("jquery"))
// //        implementation(npm("bootstrap"))
// //        implementation(npm("popper.js"))
// //        implementation(npm("bootstrap-switch-button-react"))
//         implementation(npm("animate.css", "4.1.1"))
//         implementation(npm("bootstrap-notify", "3.1.3"))
//         implementation(npm("bootstrap4-toggle", "3.6.1"))
// //        implementation(npm("abort-controller"))
// //
// //        //Dev
// //        implementation(devNpm("style-loader", "*"))
// //        implementation(devNpm("css-loader", "*"))
//         implementation(devNpm("file-loader", "6.1.0"))
//       }
//     }
//     all {
//       languageSettings.apply {
//         useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
//         // useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
//         // useExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")
//         useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
//         useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
//       }
//     }
//   }
// }

publishing {
    publications {
        repositories {
            maven {
                name = "bintray"
                url = uri(
                    "https://api.bintray.com/maven/${System.getenv("BINTRAY_USER")}/${project.group}/${project.name}/" +
                        ";publish=${if ("true".equals(project.properties["publish"] as? String?, true)) 1 else 0}" +
                        ";override=${if ("true".equals(project.properties["override"] as? String?, true)) 1 else 0}"
                )
                credentials {
                    username = System.getenv("BINTRAY_USER")
                    password = System.getenv("BINTRAY_KEY")
                }
            }
        }
    }
}

tasks {
    val jsBrowserProductionWebpack = getByPath("${project.path}:client:jsBrowserProductionWebpack").cast<KotlinWebpack>()
    getByName<Jar>("jar") {
        dependsOn(jsBrowserProductionWebpack)
        from(jsBrowserProductionWebpack.destinationDirectory) {
            into("WEB-INF")
        }
    }
}

afterEvaluate {
    tasks {
//    val frontendBrowserWebpack by getting(KotlinWebpack::class) {
//      dependsOn(runDceFrontendKotlin, frontendProcessResources)
//      outputFileName = "${rootProject.name}-${project.name}-frontend.js"
//    }
//    val frontendBrowserRun by getting(KotlinWebpack::class) {
//      group = "run"
//    }
//    val backendJar by getting(Jar::class) {
//      dependsOn(frontendBrowserWebpack)
//      from(frontendBrowserWebpack.destinationDirectory!!) {
//        into("WEB-INF")
//      }
//    }
//    val publish by getting
//    val release by creating(HttpTask::class) {
//      group = publish.group!!
//      dependsOn(publish)
//      config {
//        it.request.setUri("https://gitlab.com")
//      }
//      post {
//        it.request.uri.setPath("/api/v4/projects/${System.getenv("CI_PROJECT_ID")}/releases")
//        it.request.headers["Authorization"] = "Bearer ${System.getenv("PRIVATE_TOKEN")}"
//        it.request.setContentType("application/json")
//        it.request.setBody(
//          """
//        {
//            "name": "Release v${project.version}",
//            "tag_name": "v${project.version}",
//            "ref": "master",
//            "assets": {
//                "links": [
//                    {
//                        "name": "${project.name}",
//                        "url": "https://bintray.com/${System.getenv("BINTRAY_USER")}/${project.group}/${project.name}/${project.version}"
//                    }
//                ]
//            },
//            "description": "## Changelog\n### Breaking Changes\nN/A\n\n### New Features\nN/A\n\n### Fixes\nN/A"
//        }
//      """.trimIndent()
//        )
//      }
//    }
    }
}
