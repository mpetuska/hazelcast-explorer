import kotlinx.validation.ApiValidationExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintCheckTask
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

plugins {
    kotlin("multiplatform") version "1.4.20-M1" apply false
    id("org.jetbrains.dokka") version "1.4.0" apply false
    id("org.jlleitschuh.gradle.ktlint") version "9.4.0"
    id("io.github.http-builder-ng.http-plugin") version "0.1.1"
    `maven-publish`
    idea
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlinx:binary-compatibility-validator:0.2.3")
    }
}
apply(plugin = "binary-compatibility-validator")

configure<ApiValidationExtension> {
    ignoredProjects.addAll(listOf("client", "sample"))
}

allprojects {
    group = "lt.petuska"
    version = "1.1.0"

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
        maven("https://dl.bintray.com/mpetuska/lt.petuska")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://kotlin.bintray.com/kotlinx")
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
        withType<KtlintCheckTask> {
            dependsOn("ktlintFormat")
        }
    }
}

val gitCommitHash by lazy {
    ByteArrayOutputStream().use { os ->
        exec {
            commandLine("git", "rev-parse", "HEAD")
            standardOutput = os
        }
        os.toString().trim()
    }
}

afterEvaluate {
    tasks {
        withType<Wrapper> {
            gradleVersion = "6.6.1"
            distributionType = Wrapper.DistributionType.ALL
        }
        val lib = project(rootProject.name.removeSuffix("-root"))
        val publish by getting
        register("gitLabRelease") {
            group = publish.group!!

            doFirst {
                fun buildPackageLink(prj: Project) =
                    """
          {
            "name": "${prj.name}",
            "url": "https://bintray.com/${System.getenv("BINTRAY_USER")!!}/${prj.group}/${prj.name}/${prj.version}",
            "link_type": "package"
          }
                    """.trimIndent()

                val url = URL("https://gitlab.com/api/v4/projects/${System.getenv("CI_PROJECT_ID")!!}/releases")
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                con.setRequestProperty("Content-Type", "application/json")
                con.setRequestProperty("Authorization", "Bearer ${System.getenv("PRIVATE_TOKEN")!!}")
                con.requestMethod = "POST"
                con.doOutput = true

                val changelog = projectDir.resolve("CHANGELOG.MD")
                    .readText()
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                con.outputStream.use {
                    it.write(
                        """
            {
              "name": "Release v${lib.version}",
              "tag_name": "v${lib.version}",
              "ref": "$gitCommitHash",
              "assets": {
                  "links": [
                      ${setOf(lib).joinToString(",", transform = ::buildPackageLink)}
                  ]
              },
              "description": "$changelog"
            }
                        """.trimIndent().toByteArray()
                    )
                }
                val responseBody = BufferedReader(
                    InputStreamReader(con.inputStream, "utf-8")
                ).use { br ->
                    val response = StringBuilder()
                    var responseLine: String?
                    while (br.readLine().also { responseLine = it } != null) {
                        response.append(responseLine!!.trim { it <= ' ' })
                    }
                    println(response.toString())
                }
                val responseStatus = con.responseCode
                println(responseStatus)
                println(responseBody)
                con.disconnect()
                if (con.responseCode >= 400) throw GradleException("Invalid GitLab response. StatusCode: $responseStatus, message: $responseBody")
            }
        }
    }
}
