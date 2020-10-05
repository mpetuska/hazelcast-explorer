import io.github.httpbuilderng.http.HttpTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.ByteArrayOutputStream

plugins {
    kotlin("multiplatform") version "1.4.20-M1" apply false
    id("org.jetbrains.dokka") version "1.4.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.4.0"
    id("io.github.http-builder-ng.http-plugin") version "0.1.1"
    `maven-publish`
    idea
}

allprojects {
    group = "lt.petuska"
    version = "1.0.1"

    apply(plugin = "idea")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    idea {
        module {
            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }

    repositories {
        jcenter()
        mavenCentral()
        mavenLocal()
        mavenLocal()
        jcenter()
        mavenCentral()
        maven("https://dl.bintray.com/mpetuska/lt.petuska")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://maven.pkg.jetbrains.space/kotlin/p/dokka/dev")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        withType<Jar> {
            manifest {
                attributes += sortedMapOf(
                    "Built-By" to System.getProperty("user.name"),
                    "Build-Jdk" to System.getProperty("java.version"),
                    "Implementation-Version" to project.version,
                    "Created-By" to org.gradle.util.GradleVersion.current()
                )
            }
        }
        withType<Wrapper> {
            gradleVersion = "6.6.1"
            distributionType = Wrapper.DistributionType.ALL
        }
    }
}

fun getCommitHash() = ByteArrayOutputStream().use { os ->
    exec {
        commandLine("git", "rev-parse", "HEAD")
        standardOutput = os
    }
    os.toString().trim()
}

afterEvaluate {
    tasks {
        val lib = project(rootProject.name.removeSuffix("-root"))
        val publish by getting
        create<HttpTask>("gitLabRelease") {
            dependsOn(publish)
            group = publish.group!!

            config {
                it.request.setUri("https://gitlab.com")
            }
            post {
                it.request.uri.setPath("/api/v4/projects/${System.getenv("CI_PROJECT_ID")}/releases")
                it.request.headers["Authorization"] = "Bearer ${System.getenv("PRIVATE_TOKEN")}"
                it.request.setContentType("application/json")
                fun buildPackageLink(project: Project) =
                    """
        {
          "name": "${lib.name}",
          "url": "https://bintray.com/${System.getenv("BINTRAY_USER")}/${lib.group}/${lib.name}/${lib.version}",
          "link_type": "package"
        }
                    """.trimIndent()
                it.request.setBody(
                    """
        {
          "name": "Release v${lib.version}",
          "tag_name": "v${lib.version}",
          "ref": "${getCommitHash()}",
          "assets": {
              "links": [
                  ${setOf(lib).joinToString(",", transform = ::buildPackageLink)}
              ]
          },
          "description": "## Changelog\n### Breaking Changes\nN/A\n\n### New Features\nN/A\n\n### Fixes\nN/A"
        }
                    """.trimIndent()
                )
            }
        }
    }
}
