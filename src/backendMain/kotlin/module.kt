package lt.petuska.hazelcast.explorer

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*
import lt.petuska.hazelcast.explorer.configuration.application.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import lt.petuska.hazelcast.explorer.service.route.*
import org.kodein.di.generic.*
import org.kodein.di.ktor.*
import service.route.*

fun Application.hazelcastExplorer(hzeConfig: HzeConfig) {
  setupFeatures()
  kodein {
    bind<HzeConfig>() with singleton { hzeConfig }
    registerBindings()
  }

  routing {
    applyRemoteService<HzeConfigService>()

    static {
      resources("assets")
      defaultResource("assets/index.html")
    }
  }
}

private inline fun <reified T : RouteService<*>> Routing.applyRemoteService() = apply {
  val tmp: T by kodein().instance()
  this + tmp
}