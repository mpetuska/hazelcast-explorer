import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

plugins {
  val kotlinVersion = "1.3.60"
  id("kotlinx-serialization") version kotlinVersion
  kotlin("multiplatform") version kotlinVersion
  id("kotlin-dce-js") version kotlinVersion
  idea
}

group = "lt.petuska"
version = "1.0.0-SNAPSHOT"

repositories {
  jcenter()
  maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
  maven { url = uri("https://kotlin.bintray.com/js-externals") }
  maven {
    url = uri("https://dl.bintray.com/gbaldeck/kotlin")
    metadataSources {
      mavenPom()
      artifact()
    }
  }
  maven { url = uri("https://dl.bintray.com/rjaros/kotlin") }
  mavenLocal()
  maven {
    url = uri("http://slpv-grp-maven1.ad.sis.tv:8082/nexus/content/repositories/releases/")
    credentials {
      username = "DDaubaras"
      password = "Windowsor2017"
    }
  }
}

idea {
  module {
    isDownloadJavadoc = true
    isDownloadSources = true
  }
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
        devServer = devServer?.copy(
          port = 3000,
          proxy = mapOf(
            "/api" to "http://localhost:8080",
            "/api" to "ws://localhost:8080"
          ),
          open = false
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
        implementation(kotlin("stdlib-common"))
        implementation("io.ktor:ktor-client-serialization:${Version.ktor}")
      }
    }
    val backendMain by getting {
      dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("io.ktor:ktor-server-cio:${Version.ktor}")
        implementation("io.ktor:ktor-websockets:${Version.ktor}")
        implementation("io.ktor:ktor-gson:${Version.ktor}")
        implementation("io.ktor:ktor-serialization:${Version.ktor}")
        implementation("ch.qos.logback:logback-classic:${Version.logback}")
        implementation("org.kodein.di:kodein-di-generic-jvm:${Version.kodein}")
        implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:${Version.kodein}")
        implementation("com.hazelcast:hazelcast-client:${Version.hazelcast}")
      }
    }
    val backendTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation("tv.sis.datagrid:datagrid-client-racing:6.3.27")
        implementation("tv.sis.channelautomation.common:channelautomation-common:6.2.1")
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

tasks {
  val wrapper by getting(Wrapper::class) {
    gradleVersion = "6.1"
  }
}
afterEvaluate {
  tasks {
    val runDceFrontendKotlin by getting(KotlinJsDce::class)
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
      outputFileName = "${project.name}-frontend.js"
    }
    val frontendBrowserRun by getting(KotlinWebpack::class) {
      group = "run"
    }
    val backendRun by creating(JavaExec::class) {
      group = "run"
      dependsOn(
        kotlin.targets["backend"].compilations["main"].compileKotlinTaskName,
        kotlin.targets["backend"].compilations["test"].compileKotlinTask
      )
      main = mainClassName
      workingDir = buildDir
      classpath += kotlin.targets["backend"].compilations["main"].output.allOutputs +
          kotlin.targets["backend"].compilations["test"].output.allOutputs +
          project.configurations["backendRuntimeClasspath"] +
          project.configurations["backendTestRuntimeClasspath"]
    }
    val backendFatRun by creating(JavaExec::class) {
      group = "run"
      dependsOn(
        frontendBrowserWebpack,
        kotlin.targets["backend"].compilations["main"].compileKotlinTaskName,
        kotlin.targets["backend"].compilations["test"].compileKotlinTask
      )
      main = mainClassName
      val assetTmpDir = file("$temporaryDir/WEB-INF")
      classpath += kotlin.targets["backend"].compilations["main"].output.allOutputs +
          kotlin.targets["backend"].compilations["test"].output.allOutputs +
          project.configurations["backendRuntimeClasspath"] +
          project.configurations["backendTestRuntimeClasspath"] +
          files(assetTmpDir.parentFile)
      doFirst {
        copy {
          from(frontendBrowserWebpack.destinationDirectory)
          into(assetTmpDir)
        }
      }
    }
    val fatJar by creating(Jar::class) {
      group = "build"
      archiveAppendix.set("fat")
      dependsOn(
        frontendBrowserWebpack,
        kotlin.targets["backend"].compilations["main"].compileKotlinTaskName,
        kotlin.targets["backend"].compilations["test"].compileKotlinTask
      )
      dependsOn("backendTestProcessResources")
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
      val assetTmpDir = file("$temporaryDir/WEB-INF")
      from(assetTmpDir.parentFile)
      from((project.configurations["backendRuntimeClasspath"] +
          project.configurations["backendTestRuntimeClasspath"] +
          kotlin.targets["backend"].compilations["main"].output.allOutputs +
          kotlin.targets["backend"].compilations["test"].output.allOutputs).map {
        if (!it.isDirectory && it.extension == "jar") {
          zipTree(it)
        } else {
          it
        }
      })
      exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
      doFirst {
        copy {
          from(frontendBrowserWebpack.destinationDirectory)
          into(assetTmpDir)
        }
      }
    }
  }
}