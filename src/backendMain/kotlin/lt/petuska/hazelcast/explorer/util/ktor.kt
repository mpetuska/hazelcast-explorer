package lt.petuska.hazelcast.explorer.util

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

fun Route.route(path: lt.petuska.hazelcast.explorer.domain.enumerator.Route, build: Route.() -> Unit) =
    route(path.relativePath, build)

fun Route.get(path: lt.petuska.hazelcast.explorer.domain.enumerator.Route, body: PipelineInterceptor<Unit, ApplicationCall>) =
    get(path.relativePath, body)
