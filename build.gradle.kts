import org.jetbrains.kotlin.gradle.targets.js.webpack.*
import org.jetbrains.kotlin.gradle.tasks.*

plugins {
  kotlin("multiplatform") version System.getProperty("kotlinVersion")
  id("kotlinx-serialization") version System.getProperty("kotlinVersion")
  id("kotlin-dce-js") version System.getProperty("kotlinVersion")
  idea
}

group = "lt.petuska"
version = "1.0.0-SNAPSHOT"

repositories {
  mavenLocal()
  jcenter()
  maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlin-js-wrappers") }
  maven { url = uri("https://dl.bintray.com/kotlin/kotlin-eap") }
  maven { url = uri("https://kotlin.bintray.com/js-externals") }
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
    compilations.all {
      kotlinOptions {
        moduleKind = "commonjs"
        sourceMap = !isProductionBuild
        metaInfo = true
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
                "/api" to "http://localhost:8080"
            )
        )
      }
      webpackTask {
        val compileKotlinFrontend by tasks.getting(Kotlin2JsCompile::class)
        val runDceFrontendKotlin by tasks.getting(KotlinJsDce::class)
        dependsOn(runDceFrontendKotlin)
        inputs.property("production", isProductionBuild)

        val prodConfigFile = file("webpack.config.d/production.js")
        doFirst {
          if (isProductionBuild) {
            prodConfigFile.run {
              parentFile.mkdirs()
              writeText("config.mode = 'production'")
            }
            copy {
              from(runDceFrontendKotlin.destinationDir)
              into("${compileKotlinFrontend.destinationDir}")
              include("${project.name}*")
            }
            copy {
              from(runDceFrontendKotlin.destinationDir)
              into("${compileKotlinFrontend.destinationDir}/node_modules")
              exclude("${project.name}*")
            }
          }
        }
        doLast {
          prodConfigFile.delete()
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
        implementation("io.ktor:ktor-client-serialization:1.2.4")
      }
    }
    val backendMain by getting {
      dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation("io.ktor:ktor-server-cio:1.2.4")
        implementation("io.ktor:ktor-gson:1.2.4")
        implementation("io.ktor:ktor-serialization:1.2.4")
        implementation("ch.qos.logback:logback-classic:1.2.3")
        implementation("org.kodein.di:kodein-di-generic-jvm:6.3.3")
        implementation("org.kodein.di:kodein-di-framework-ktor-server-jvm:6.3.3")
        implementation("com.hazelcast:hazelcast-client:3.8.6")
      }
    }
    val backendTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation(kotlin("test-junit"))
        implementation("tv.sis.datagrid:datagrid-client-racing:6.3.27")
        implementation("tv.sis.channelautomation.common:channelautomation-common:6.2.1")
      }
    }
    val frontendMain by getting {
      resources.srcDir(webDir)
      dependencies {
        implementation("io.ktor:ktor-client-serialization-js:1.2.4")
        implementation("org.jetbrains:kotlin-react-redux:5.0.7-pre.83-kotlin-1.3.50")
        implementation("org.jetbrains:kotlin-react-dom:16.9.0-pre.83-kotlin-1.3.50")
        implementation("org.jetbrains:kotlin-styled:1.0.0-pre.83-kotlin-1.3.50")
        implementation(npm("core-js"))
        implementation(npm("react"))
        implementation(npm("react-dom"))
        implementation(npm("redux"))
        implementation(npm("react-redux"))
        implementation(npm("styled-components"))
        implementation(npm("inline-style-prefixer"))
        implementation(npm("react-json-editor-ajrm"))
        implementation(npm("text-encoding"))
        implementation(npm("jquery", "3.3.1"))
        implementation(npm("bootstrap", "4.3.1"))
        implementation(npm("popper.js", "1.14.3"))
        implementation(npm("bootstrap-switch-button-react"))
        implementation(npm("bootstrap-notify"))
        implementation(npm("animate.css"))

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

//    dependency("bootstrap", "4.3.1")
//    dependency("jquery", "3.3.1")
//    dependency("popper.js", "1.14.3")
//    dependency("bootstrap-switch-button-react")
//    dependency("bootstrap-notify")
//    dependency("animate.css")
//
//    devDependency("style-loader")
//    devDependency("css-loader")
//    devDependency("file-loader")

tasks {
  withType<KotlinJsDce> {
    enabled = isProductionBuild
    dceOptions {
      devMode = !isProductionBuild
    }
    inputs.property("production", isProductionBuild)
  }
  val frontendProcessResources by getting(Copy::class) {
    from("$webDir/index.html") {
      expand(project.properties)
    }
  }
}
afterEvaluate {
  tasks {
    val backendMainClasses by getting
    val frontendBrowserWebpack by getting(KotlinWebpack::class) {
      dependsOn("frontendProcessResources")
    }
    val backendRun by creating(JavaExec::class) {
      dependsOn(frontendBrowserWebpack, kotlin.targets["backend"].compilations["main"].compileKotlinTaskName, kotlin.targets["backend"].compilations["test"].compileKotlinTask)
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
      dependsOn(frontendBrowserWebpack, kotlin.targets["backend"].compilations["main"].compileKotlinTaskName, kotlin.targets["backend"].compilations["test"].compileKotlinTask)
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