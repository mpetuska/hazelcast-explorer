package lt.petuska.hazelcast.explorer.service

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import lt.petuska.hazelcast.explorer.domain.enumerator.*
import kotlin.browser.*

abstract class FetchService {
  protected val httpClient = HttpClient(Js) {
    install(JsonFeature) {
      serializer = KotlinxSerializer()
    }
    defaultRequest {
      host = window.location.hostname
      port = window.location.port.toInt()
    }
  }

  protected suspend inline fun <reified T> get(url: Route, block: HttpRequestBuilder.() -> Unit = {}): T =
      httpClient.get(
          "$url",
          block
      )
}