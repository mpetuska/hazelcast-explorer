import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.Kotlin2JsCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

plugins {
  id("kotlinx-serialization")
  kotlin("multiplatform")
  id("kotlin-dce-js")
  `maven-publish`
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

object Version {
  const val ktor = "1.3.0"
  const val logback = "1.2.3"
  const val kodein = "6.5.1"
  const val hazelcast = "3.8.6"
}

// Custom Properties
val isProductionBuild = project.hasProperty("prod") || project.hasProperty("production")
val webDir = file("$projectDir/src/frontendMain/web")
val mainClassName = "io.ktor.server.cio.EngineMain"

kotlin {
  jvm("backend") {
    mavenPublication {
      groupId = "${project.group}"
      artifactId = rootProject.name
      version = version
      tasks.withType<AbstractPublishToMaven>().all {
        onlyIf { this.publication == this@mavenPublication }
      }
    }
    compilations.all {
      kotlinOptions {
        jvmTarget = "1.8"
      }
    }
  }
  js("frontend") {
    useCommonJs()
    compilations.all {
      kotlinOptions {
        sourceMap = !isProductionBuild
        metaInfo = !isProductionBuild
        if (!isProductionBuild) {
          sourceMapEmbedSources = "always"
        }
      }
    }
    browser {
      runTask {
        val frontendProcessResources by tasks.getting(Copy::class)
        devServer = devServer?.copy(
          port = 3000,
          proxy = mapOf(
            "/api" to "http://localhost:8080"
          ),
          open = false,
          contentBase = listOf("${frontendProcessResources.destinationDir}")
        )
      }
      webpackTask {
        doLast {
          copy {
            from(webDir) {
              include("index.html")
              expand(project.properties)
            }
            from(webDir) {
              exclude("index.html")
            }
            into(destinationDirectory!!)
          }
        }
      }
    }
  }
  sourceSets {
    val commonMain by getting {
      dependencies {
        api(kotlin("stdlib-common"))
        api("io.ktor:ktor-client-serialization:${Version.ktor}")
      }
    }
    val backendMain by getting {
      dependencies {
        api(kotlin("stdlib-jdk8"))
        api("io.ktor:ktor-server-cio:${Version.ktor}")
        api("io.ktor:ktor-websockets:${Version.ktor}")
        api("io.ktor:ktor-gson:${Version.ktor}")
        api("io.ktor:ktor-serialization:${Version.ktor}")
        api("ch.qos.logback:logback-classic:${Version.logback}")
        api("org.kodein.di:kodein-di-generic-jvm:${Version.kodein}")
        api("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Version.kodein}")
        api("com.hazelcast:hazelcast-client:${Version.hazelcast}")
      }
    }
    val frontendMain by getting {
      resources.srcDir(webDir)
      dependencies {
        implementation("io.ktor:ktor-client-serialization-js:${Version.ktor}")
        implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.89-kotlin-1.3.60")
        implementation("org.jetbrains:kotlin-react-dom:16.9.0-pre.89-kotlin-1.3.60")
        implementation("org.jetbrains:kotlin-styled:1.0.0-pre.89-kotlin-1.3.60")
        implementation("org.jetbrains:kotlin-react-router-dom:4.3.1-pre.89-kotlin-1.3.60")
        implementation(npm("core-js"))
        implementation(npm("react"))
        implementation(npm("react-dom"))
        implementation(npm("react-router-dom"))
        implementation(npm("redux"))
        implementation(npm("react-redux"))
        implementation(npm("styled-components"))
        implementation(npm("inline-style-prefixer"))
        implementation(npm("react-json-editor-ajrm"))
        implementation(npm("text-encoding"))
        implementation(npm("jquery"))
        implementation(npm("bootstrap"))
        implementation(npm("popper.js"))
        implementation(npm("bootstrap-switch-button-react"))
        implementation(npm("bootstrap-notify"))
        implementation(npm("animate.css"))
        implementation(npm("abort-controller"))
        
        //Dev
        implementation(npm("style-loader"))
        implementation(npm("css-loader"))
        implementation(npm("file-loader"))
      }
    }
    all {
      languageSettings.apply {
        useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
        useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
        useExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")
        useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
      }
    }
  }
}

afterEvaluate {
  tasks {
    val runDceFrontendKotlin by getting(KotlinJsDce::class)
    val compileKotlinFrontend by getting(Kotlin2JsCompile::class)
    val frontendProcessResources by getting(Copy::class) {
      dependsOn(runDceFrontendKotlin)
      from("$webDir/index.html") {
        expand(project.properties)
      }
    }
    withType<KotlinJsDce> {
      dceOptions {
        devMode = !isProductionBuild
      }
      inputs.property("production", isProductionBuild)
      doFirst {
        classpath = classpath.filter { it.extension != "js" }
        destinationDir.deleteRecursively()
      }
    }
    val frontendBrowserWebpack by getting(KotlinWebpack::class) {
      dependsOn(runDceFrontendKotlin, frontendProcessResources)
      outputFileName = "${rootProject.name}-${project.name}-frontend.js"
    }
    val frontendBrowserRun by getting(KotlinWebpack::class) {
      group = "run"
    }
    val backendJar by getting(Jar::class) {
      dependsOn(frontendBrowserWebpack)
      from(frontendBrowserWebpack.destinationDirectory!!) {
        into("WEB-INF")
      }
    }
  }
}