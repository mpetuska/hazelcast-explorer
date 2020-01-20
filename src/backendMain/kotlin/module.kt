package lt.petuska.hazelcast.explorer

import io.ktor.application.Application
import io.ktor.http.content.defaultResource
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.routing.route
import io.ktor.routing.routing
import lt.petuska.hazelcast.explorer.configuration.application.registerBindings
import lt.petuska.hazelcast.explorer.configuration.application.setupFeatures
import lt.petuska.hazelcast.explorer.configuration.domain.HzeConfig
import lt.petuska.hazelcast.explorer.route.entity.mapRoute
import lt.petuska.hazelcast.explorer.route.entity.topicRoute
import lt.petuska.hazelcast.explorer.route.meta.hzeConfigRoute
import org.kodein.di.ktor.kodein

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
        topicRoute()
      }
    }
    
    static {
      resources("WEB-INF")
      defaultResource("WEB-INF/index.html")
    }
  }
}