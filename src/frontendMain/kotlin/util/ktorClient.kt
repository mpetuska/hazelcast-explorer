package lt.petuska.hazelcast.explorer.util

import domain.route.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import lt.petuska.hazelcast.explorer.domain.common.*
import kotlin.browser.*

fun httpClient() = HttpClient(Js) {
  install(JsonFeature) {
    serializer = KotlinxSerializer()
  }
  defaultRequest {
    accept(ContentType.Application.Json)
    host = window.location.hostname
    port = window.location.port.toInt()
  }
}

suspend inline fun <reified T : DTO> RouteDef<T>.call(_method: HttpMethod, id: String? = null, _body: T? = null) = httpClient().call(absolutePath) {
  _body?.let {
    body = it
  }
  parameter("id", id)
  method = _method
}

suspend inline fun <reified T : DTO> RouteDef<T>.get(): List<T> = call(HttpMethod.Get).receive()
suspend inline fun <reified T : DTO> RouteDef<T>.get(id: String): T = call(HttpMethod.Get, id).receive()
suspend inline fun <reified T : DTO> RouteDef<T>.delete(id: String) = call(HttpMethod.Delete, id)
suspend inline fun <reified T : DTO> RouteDef<T>.post(id: String, body: T) = call(HttpMethod.Post, id, body)
suspend inline fun <reified T : DTO> RouteDef<T>.put(id: String, body: T) = call(HttpMethod.Put, id, body)
