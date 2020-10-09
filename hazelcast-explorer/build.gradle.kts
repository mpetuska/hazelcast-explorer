import org.jetbrains.dokka.utilities.cast

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

dependencies {
    implementation(project("${project.path}:client"))
    api("io.ktor:ktor-server-cio:${Version.ktor}")
    api("io.ktor:ktor-websockets:${Version.ktor}")
    api("io.ktor:ktor-gson:${Version.ktor}")
    api("io.ktor:ktor-serialization:${Version.ktor}")
    api("ch.qos.logback:logback-classic:${Version.logback}")
    api("org.kodein.di:kodein-di-generic-jvm:${Version.kodein}")
    api("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Version.kodein}")
}

kotlin {
    explicitApi()
    sourceSets.all {
        languageSettings.apply {
            useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
        }
    }
}

publishing {
    fun checkAnyTrue(vararg props: String) = props.any {
        "true".equals(project.properties[it] as String?, true)
    }

    fun checkNone(vararg props: String) = props.none {
        project.hasProperty(it)
    }
    publications {
        repositories {
            fun repository(name: String, config: MavenArtifactRepository.() -> Unit) {
                if (checkAnyTrue("publish.all", "publish.$name") || checkNone("publish.$name.skip")) {
                    maven {
                        this.name = name
                        config()
                    }
                }
            }
            repository("GitLab") {
                url = uri(
                    "https://gitlab.com/api/v4/projects/${System.getenv("CI_PROJECT_ID")}/packages/maven"
                )
                credentials(HttpHeaderCredentials::class) {
                    val jobToken = System.getenv("CI_JOB_TOKEN")
                    if (jobToken != null) {
                        // GitLab CI
                        name = "Job-Token"
                        value = jobToken
                    } else {
                        name = "Private-Token"
                        value = System.getenv("PRIVATE_TOKEN")
                    }
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
            repository("Bintray") {
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
    val jvmJar = getByPath("${project.path}:client:jvmJar").cast<Jar>()
    getByName<Jar>("jar") {
        dependsOn(jvmJar)
        from(zipTree(jvmJar.archiveFile))
    }
}
