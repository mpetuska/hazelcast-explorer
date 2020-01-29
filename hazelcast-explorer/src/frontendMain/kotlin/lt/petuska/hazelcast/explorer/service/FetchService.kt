package lt.petuska.hazelcast.explorer.service

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.websocket.WebSockets
import io.ktor.client.request.accept
import io.ktor.client.request.host
import io.ktor.client.request.port
import io.ktor.http.ContentType
import kotlinx.serialization.json.Json
import kotlin.browser.window

abstract class FetchService {
  protected val httpClient by lazy {
    HttpClient(Js) {
      install(JsonFeature) {
        serializer = KotlinxSerializer(Json.nonstrict)
      }
      install(WebSockets)
      defaultRequest {
        accept(ContentType.Application.Json)
        host = window.location.hostname
        port = window.location.port.toIntOrNull() ?: 80
      }
    }
  }
}