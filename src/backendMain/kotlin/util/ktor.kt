package lt.petuska.hazelcast.explorer.util

import io.ktor.application.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import lt.petuska.hazelcast.explorer.domain.enumerator.Route as RouteEnum

fun Route.route(path: RouteEnum, build: Route.() -> Unit) =
    route(path.relativePath, build)

fun Route.get(path: RouteEnum, body: PipelineInterceptor<Unit, ApplicationCall>) =
    get(path.relativePath, body)
