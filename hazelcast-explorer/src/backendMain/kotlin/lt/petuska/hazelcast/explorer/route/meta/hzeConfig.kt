package lt.petuska.hazelcast.explorer.route.meta

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

internal fun Route.hzeConfigRoute() = route("hzeConfig") {
  get {
    val hzeConfig by kodein().instance<HzeConfig>()
    call.respond(hzeConfig)
  }
}