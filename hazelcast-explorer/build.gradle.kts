import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

plugins {
  kotlin("plugin.serialization")
  kotlin("multiplatform")
  id("kotlin-dce-js")
  id("maven-publish")
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
}

val Version.hazelcast: String
  get() = project.properties.getOrDefault("hazelcastVersion", "3.8.6").toString()
version = "${rootProject.version}-hz-${Version.hazelcast}"

// Custom Properties
val isProductionBuild = project.hasProperty("prod") || project.hasProperty("production")
val webDir = file("$projectDir/src/frontendMain/web")
val mainClassName = "io.ktor.server.cio.EngineMain"

kotlin {
  jvm("backend") {
    mavenPublication {
      artifactId = project.name
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
        implementation(npm("core-js"))
        implementation(npm("react"))
        implementation(npm("react-dom"))
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

publishing {
  publications.removeIf { !it.name.endsWith("backend") }
  publications.withType<MavenPublication> {
    pom {
      val repoHost = "gitlab.com"
      val repoPath = "${project.group}/${project.name}"
      val repo = "$repoHost/$repoPath"
      name.set(project.name)
      url.set("https://$repo")
      licenses {
        license {
          name.set("The Apache License, Version 2.0")
          url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
          distribution.set("repo")
        }
      }
      developers {
        developer {
          id.set("mpetuska")
          name.set("Martynas Petuška")
          email.set("martynas.petuska@gmail.com")
        }
      }
      scm {
        url.set("https://$repo")
        connection.set("scm:git:https://$repo.git")
        developerConnection.set("scm:git:git@$repoHost:$repoPath.git")
      }
    }
    
    tasks.create("${name}PostPublish", Exec::class) {
      val publish by tasks.getting
      group = publish.group!!
      publish.dependsOn(this)
      dependsOn("publishAllPublicationsToBintrayRepository")
      
      executable = "curl"
      setArgs(
        listOf(
          "-u", "${System.getenv("BINTRAY_USER")}:${System.getenv("BINTRAY_KEY")}",
          "-X", "DELETE",
          "https://api.bintray.com/packages/${System.getenv("BINTRAY_USER")}/${project.group}/${project.name}/versions/$artifactId"
        )
      )
    }
  }
  
  repositories {
    maven {
      name = "bintray"
      url = uri(
        "https://api.bintray.com/maven/${System.getenv("BINTRAY_USER")}/${group}/${project.name}/" +
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
        keep("ktor-ktor-io.\$\$importsForInline\$\$.ktor-ktor-io.io.ktor.utils.io")
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