package lt.petuska.hazelcast.explorer

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import lt.petuska.hazelcast.explorer.configuration.application.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import lt.petuska.hazelcast.explorer.domain.enumerator.Route
import lt.petuska.hazelcast.explorer.util.*
import org.kodein.di.ktor.*

fun Application.hazelcastExplorer(hazelcastExplorerConfig: HazelcastExplorerConfig) {
  setupFeatures()
  kodein {
    registerBindings()
  }

  routing {
    route(Route.BASE) {
      get(Route.META) {
        call.respond(hazelcastExplorerConfig.toDTO())
      }
    }
    static {
      resources("assets")
      defaultResource("assets/index.html")
    }
  }
}