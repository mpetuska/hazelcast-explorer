import org.jetbrains.kotlin.gradle.frontend.*
import org.jetbrains.kotlin.gradle.frontend.ktor.*
import org.jetbrains.kotlin.gradle.frontend.npm.*
import org.jetbrains.kotlin.gradle.frontend.webpack.*
import org.jetbrains.kotlin.gradle.tasks.*

buildscript {
  extra.set("production", (findProperty("prod") ?: findProperty("production") ?: "false") == "true")
}

plugins {
  val kotlinVersion: String by System.getProperties()
  id("kotlinx-serialization") version kotlinVersion
  id("kotlin-multiplatform") version kotlinVersion
  id("kotlin-dce-js") version kotlinVersion
  kotlin("frontend") version System.getProperty("frontendPluginVersion")
  idea
}

version = "1.0.0-SNAPSHOT"
group = "lt.petuska"

idea {
  module {
    isDownloadJavadoc = true
    isDownloadSources = true
  }
}

repositories {
  jcenter()
  maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
  maven { url = uri("https://kotlin.bintray.com/js-externals") }
  mavenLocal()
}

// Versions
val kotlinVersion: String by System.getProperties()
val kotlinxPreVersion: String by project
val reactVersion: String by project
val reduxVersion: String by project
val kotlinStyledVersion: String by project

val ktorVersion: String by project
val logbackVersion: String by project
val kodeinVersion: String by project
val serializationVersion: String by project
val hazelcastVersion: String by project

// Custom Properties
val webDir = file("src/frontendMain/web")
val isProductionBuild = project.extra.get("production") as Boolean
val mainClassName = "io.ktor.server.netty.EngineMain"

kotlin {
  jvm("backend") {
    compilations.all {
      kotlinOptions {
        jvmTarget = "1.8"
      }
    }
  }
  js("frontend") {
    compilations.all {
      kotlinOptions {
        moduleKind = "umd"
        sourceMap = !isProductionBuild
        metaInfo = true
        if (!isProductionBuild) {
          sourceMapEmbedSources = "always"
        }
      }
    }
  }
  sourceSets {
    getByName("commonMain") {
      dependencies {
        implementation(kotlin("stdlib-common"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serializationVersion")
      }
    }
    getByName("backendMain") {
      dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion")
        implementation("io.ktor:ktor-server-core:$ktorVersion")
        implementation("io.ktor:ktor-server-netty:$ktorVersion")
        implementation("io.ktor:ktor-serialization:$ktorVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
        implementation("org.kodein.di:kodein-di-generic-jvm:$kodeinVersion")
        implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:$kodeinVersion")
        implementation("com.hazelcast:hazelcast-client:$hazelcastVersion")
      }
      languageSettings.apply {
        useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
        useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
      }
    }
    getByName("backendTest") {
      dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
      }
      languageSettings.apply {
        useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
      }
    }
    getByName("frontendMain") {
      resources.srcDir(webDir)
      dependencies {
        implementation(kotlin("stdlib-js"))
        implementation("io.ktor:ktor-client-js:$ktorVersion")
        implementation("io.ktor:ktor-client-json-js:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization-js:$ktorVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-js:$serializationVersion")
        implementation("org.jetbrains:kotlin-react:$reactVersion-pre.$kotlinxPreVersion-kotlin-$kotlinVersion")
        implementation("org.jetbrains:kotlin-react-dom:$reactVersion-pre.$kotlinxPreVersion-kotlin-$kotlinVersion")
        implementation("org.jetbrains:kotlin-react-redux:$reduxVersion-pre.$kotlinxPreVersion-kotlin-$kotlinVersion")
        implementation("org.jetbrains:kotlin-styled:$kotlinStyledVersion-pre.$kotlinxPreVersion-kotlin-$kotlinVersion")
      }
    }
    all {
      languageSettings.apply {
        useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
      }
    }
  }
}

ktor {
  port = 8080
  mainClass = mainClassName
  jvmOptions = arrayOf()
  workDir = buildDir
}

kotlinFrontend {
  npm {
    dependency("redux")
    dependency("react-redux")
    dependency("react")
    dependency("react-dom")
    dependency("styled-components")
    dependency("inline-style-prefixer")
    dependency("react-json-editor-ajrm")
    dependency("core-js")
    dependency("text-encoding")
    dependency("bootstrap", "4.3.1")
    dependency("jquery", "3.3.1")
    dependency("popper.js")
    dependency("bootstrap-switch-button-react", "1.1.0")

    devDependency("style-loader")
    devDependency("css-loader")
    devDependency("file-loader")
  }
  webpackBundle {
    bundleName = "main"
    sourceMapEnabled = false
    port = 3000
    proxyUrl = "http://localhost:8080"
    contentPath = webDir
    mode = if (isProductionBuild) "production" else "development"
  }
}

tasks {
  withType<Kotlin2JsCompile> {
    kotlinOptions {
      moduleKind = "commonjs"
//      sourceMap = !isProductionBuild
      metaInfo = true
      sourceMapEmbedSources = "always"
    }
  }
  withType<KotlinJsDce> {
    dceOptions {
      devMode = !isProductionBuild
    }
    inputs.property("production", isProductionBuild)
  }
}
afterEvaluate {
  tasks {
    create("copyFrontendResources", Copy::class) {
      dependsOn("frontendProcessResources")
      from((project.tasks["frontendProcessResources"] as Copy).destinationDir)
      into((project.tasks["processResources"] as Copy).destinationDir)
    }
    getByName("webpack-run", WebPackRunTask::class) {
      dependsOn( "copyFrontendResources")
    }
    getByName("webpack-bundle") {
      dependsOn("copyFrontendResources")
    }

    val explodedFrontendJarDir = file("$buildDir/exploded/frontend")
    getByName("frontendJar", Jar::class) {
      dependsOn("webpack-bundle")
      group = "package"
      val from = project.tasks["webpack-bundle"].outputs.files + webDir
      into("assets") {
        from(from)
      }
      inputs.files(from)

      manifest {
        attributes(
          mapOf(
            "Implementation-Title" to rootProject.name,
            "Implementation-Group" to rootProject.group,
            "Implementation-Version" to rootProject.version,
            "Timestamp" to System.currentTimeMillis()
          )
        )
      }
      doLast {
        explodedFrontendJarDir.mkdirs()
        copy {
          from(zipTree(archiveFile))
          into(explodedFrontendJarDir)
        }
      }
    }
    getByName("build").dependsOn("frontendJar")
    create("frontendZip", Zip::class) {
      dependsOn("webpack-bundle")
      group = "package"
      archiveAppendix.set("frontend")
      destinationDirectory.set(file("$buildDir/libs"))
      val from = project.tasks["webpack-bundle"].outputs.files + webDir
      from(from)
      inputs.files(from)
      outputs.file(archiveFile)
    }
    getByName("backendJar").group = "package"
    getByName("jar", Jar::class) {
      dependsOn("frontendJar", "backendJar")
      group = "package"
      manifest {
        attributes(
          mapOf(
            "Implementation-Title" to rootProject.name,
            "Implementation-Group" to rootProject.group,
            "Implementation-Version" to rootProject.version,
            "Timestamp" to System.currentTimeMillis(),
            "Main-Class" to mainClassName
          )
        )
      }
      val dependencies = configurations["backendRuntimeClasspath"].filter { it.name.endsWith(".jar") } +
          project.tasks["backendJar"].outputs.files +
          project.tasks["frontendJar"].outputs.files
      dependencies.forEach {
        if (it.isDirectory) from(it) else from(zipTree(it))
      }
      exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
      inputs.files(dependencies)
      outputs.file(archiveFile)
    }
    getByName("ktor-run", KtorStartStopTask::class) {
      dependsOn("backendTestClasses", "frontendJar")
      doFirst {
        val testClassPath = kotlin.targets["backend"].compilations["main"].output.allOutputs.files +
            kotlin.targets["backend"].compilations["test"].output.allOutputs.files +
            project.configurations["backendRuntimeClasspath"] +
            project.configurations["backendTestRuntimeClasspath"] +
            explodedFrontendJarDir

        testClassPath.distinct().joinToString(File.pathSeparator) { it.absolutePath }
        ktor {
          jvmOptions = arrayOf(
            "-cp",
            testClassPath.distinct().joinToString(File.pathSeparator) { it.absolutePath },
            "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
          )
        }
      }
    }
  }
}

fun KotlinFrontendExtension.webpackBundle(block: WebPackExtension.() -> Unit) =
  bundle("webpack", delegateClosureOf(block))

fun KotlinFrontendExtension.npm(block: NpmExtension.() -> Unit) = configure(block)