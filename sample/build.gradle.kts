import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm")
  id("com.github.johnrengelman.shadow") version "5.2.0"
  application
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
  mavenLocal()
  maven {
    url = uri("http://slpv-grp-maven1.ad.sis.tv:8082/nexus/content/repositories/releases/")
    credentials {
      username = "DDaubaras"
      password = "Windowsor2017"
    }
  }
}

object Version {
  const val ktor = "1.3.0"
  const val logback = "1.2.3"
  const val kodein = "6.5.1"
  const val hazelcast = "3.8.6"
}

application {
  mainClassName = "io.ktor.server.cio.EngineMain"
}

sourceSets["main"].withConvention(KotlinSourceSet::class) {
  dependencies {
    implementation(project(":library"))
    implementation("tv.sis.datagrid:datagrid-client-racing:6.3.45")
    implementation("tv.sis.channelautomation.common:channelautomation-common:6.2.1")
  }
  languageSettings.apply {
    useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
    useExperimentalAnnotation("kotlinx.serialization.UnstableDefault")
    useExperimentalAnnotation("kotlinx.serialization.ImplicitReflectionSerializer")
    useExperimentalAnnotation("io.ktor.util.KtorExperimentalAPI")
  }
}
tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

afterEvaluate {
  //  tasks {
//    val backendRun by creating(JavaExec::class) {
//      group = "run"
//      dependsOn(
//        kotlin.targets["backend"].compilations["main"].compileKotlinTaskName,
//        kotlin.targets["backend"].compilations["test"].compileKotlinTask
//      )
//      main = mainClassName
//      workingDir = buildDir
//      classpath += kotlin.targets["backend"].compilations["main"].output.allOutputs +
//          kotlin.targets["backend"].compilations["test"].output.allOutputs +
//          project.configurations["backendRuntimeClasspath"] +
//          project.configurations["backendTestRuntimeClasspath"]
//    }
//    val backendFatRun by creating(JavaExec::class) {
//      group = "run"
//      dependsOn(
//        frontendBrowserWebpack,
//        kotlin.targets["backend"].compilations["main"].compileKotlinTaskName,
//        kotlin.targets["backend"].compilations["test"].compileKotlinTask
//      )
//      main = mainClassName
//      val assetTmpDir = file("$temporaryDir/WEB-INF")
//      classpath += kotlin.targets["backend"].compilations["main"].output.allOutputs +
//          kotlin.targets["backend"].compilations["test"].output.allOutputs +
//          project.configurations["backendRuntimeClasspath"] +
//          project.configurations["backendTestRuntimeClasspath"] +
//          files(assetTmpDir.parentFile)
//      doFirst {
//        copy {
//          from(frontendBrowserWebpack.destinationDirectory)
//          into(assetTmpDir)
//        }
//      }
//    }
//    val fatJar by creating(Jar::class) {
//      group = "build"
//      archiveAppendix.set("fat")
//      dependsOn(
//        frontendBrowserWebpack,
//        kotlin.targets["backend"].compilations["main"].compileKotlinTaskName,
//        kotlin.targets["backend"].compilations["test"].compileKotlinTask
//      )
//      dependsOn("backendTestProcessResources")
//      manifest {
//        attributes(
//          mapOf(
//            "Implementation-Title" to rootProject.name,
//            "Implementation-Group" to rootProject.group,
//            "Implementation-Version" to rootProject.version,
//            "Timestamp" to System.currentTimeMillis(),
//            "Main-Class" to mainClassName
//          )
//        )
//      }
//      val assetTmpDir = file("$temporaryDir/WEB-INF")
//      from(assetTmpDir.parentFile)
//      from((project.configurations["backendRuntimeClasspath"] +
//          project.configurations["backendTestRuntimeClasspath"] +
//          kotlin.targets["backend"].compilations["main"].output.allOutputs +
//          kotlin.targets["backend"].compilations["test"].output.allOutputs).map {
//        if (!it.isDirectory && it.extension == "jar") {
//          zipTree(it)
//        } else {
//          it
//        }
//      })
//      exclude("META-INF/*.RSA", "META-INF/*.SF", "META-INF/*.DSA")
//      doFirst {
//        copy {
//          from(frontendBrowserWebpack.destinationDirectory)
//          into(assetTmpDir)
//        }
//      }
//    }
//  }
}