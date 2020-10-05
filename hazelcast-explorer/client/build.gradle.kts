import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
}

repositories {
    jcenter()
}

object Version {
    const val fritz = "0.7"
    const val hazelcast = "3.8.6"
}

// kapt {
//   arguments {
//     arg("fritz2.visibility", "internal")
//   }
// }

kotlin {
    explicitApi()
    jvm()
    js(LEGACY) {
        browser {
            runTask {
                devServer = devServer!!.copy(
                    port = 3000,
                    proxy = mapOf(
                        "*" to "http://localhost:8080"
                    )
                )
            }
        }
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0-RC2")
            }
        }
        val jvmMain by getting {
            dependencies {
                api("com.hazelcast:hazelcast-client:${Version.hazelcast}")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("dev.fritz2:core:${Version.fritz}")
                implementation(npm("popper.js", "1.16.1"))
                implementation(npm("jquery", "3.5.1"))
                implementation(npm("bootstrap", "4.5.2"))
                implementation(devNpm("file-loader", "6.1.0"))
            }
        }
        all {
            languageSettings.apply {
                // useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                // useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
                // useExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")
                useExperimentalAnnotation("kotlinx.serialization.ExperimentalSerializationApi")
                useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
                // useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
            }
        }
    }
}

tasks {
    val jsBrowserProductionWebpack by getting(KotlinWebpack::class)
    val compileKotlinJs by getting(org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile::class)
    val jsProcessResources by getting(Copy::class) {
        eachFile {
            if (file.name == "index.html") {
                expand(project.properties + mapOf("kotlinJsOutputFile" to "${project.name}.js"))
            }
        }
    }
    getByName<Jar>("jvmJar") {
        dependsOn(jsBrowserProductionWebpack)
        from(jsBrowserProductionWebpack.destinationDirectory) {
            into("WEB-INF")
        }
    }
    withType<KotlinWebpack> {
        cssSupport.enabled = true
        cssSupport.mode = "extract"
    }
}
