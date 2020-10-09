package lt.petuska.hazelcast.explorer.route.meta

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import lt.petuska.hazelcast.explorer.domain.HzeConfig
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

internal fun Route.hzeConfigRoute() = route("hzeConfig") {
  get {
    val hzeConfig by kodein().instance<HzeConfig>()
    call.respondText(Json.encodeToString(hzeConfig), ContentType.Application.Json)
  }
}
