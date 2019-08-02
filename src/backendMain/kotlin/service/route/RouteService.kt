package lt.petuska.hazelcast.explorer.service.route

import domain.route.*
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.*
import lt.petuska.hazelcast.explorer.domain.common.*

abstract class RouteService<T : DTO>(private val routeDef: RouteDef<T>) {
  protected open val onGetAll: (() -> List<T>)? = null
  protected open val onGet: ((id: String) -> T)? = null
  protected open val onDelete: ((id: String) -> Unit)? = null
  protected open val onPost: ((id: String, body: T) -> Unit)? = null
  protected open val onPut: ((id: String, body: T) -> Unit)? = null

  fun registerRoutes(routing: Routing) = routing.apply {
    if (onGet != null || onGetAll != null) {
      application.log.info("Registering GET ${routeDef.absolutePath}")
      get(routeDef.absolutePath) {
        call.request.queryParameters["id"]?.let { id ->
          onGet?.let { f ->
            call.respond(f(id))
          }
        } ?: onGetAll?.let { f ->
          call.respond(f())
        }
      }
    }
    onPut?.let { f ->
      application.log.info("Registering PUT ${routeDef.absolutePath}")
      put(routeDef.absolutePath) {
        call.request.queryParameters.getOrFail("id").let { id ->
          f(id, call.receive(routeDef.entityType!!))
          call.respond(HttpStatusCode.NoContent)
        }
      }
    }
  }
}

operator fun Routing.plus(routeService: RouteService<*>) = apply {
  routeService.registerRoutes(this)
}