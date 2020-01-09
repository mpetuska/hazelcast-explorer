package lt.petuska.hazelcast.explorer.route.meta

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import lt.petuska.hazelcast.explorer.configuration.domain.*
import org.kodein.di.generic.*
import org.kodein.di.ktor.*

fun Route.hzeConfigRoute() = route("hzeConfig") {
  get {
    val hzeConfig by kodein().instance<HzeConfig>()
    call.respond(hzeConfig.toDTO())
  }
}