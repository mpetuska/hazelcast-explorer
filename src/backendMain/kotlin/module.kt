package lt.petuska.hazelcast.explorer

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*
import lt.petuska.hazelcast.explorer.configuration.application.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import lt.petuska.hazelcast.explorer.route.entity.*
import lt.petuska.hazelcast.explorer.route.meta.*
import org.kodein.di.ktor.*

fun Application.hazelcastExplorer(hzeConfig: HzeConfig) {
  setupFeatures()
  kodein {
    registerBindings(hzeConfig)
  }

  routing {
    route("/api") {
      route("/meta") {
        hzeConfigRoute()
      }
      route("/entity/{environment}/{target}") {
        mapRoute()
      }
    }

    static {
      resources("WEB-INF")
      defaultResource("WEB-INF/index.html")
    }
  }
}