package lt.petuska.hazelcast.explorer.service

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import lt.petuska.hazelcast.explorer.domain.*
import kotlin.browser.*

abstract class FetchService {
  protected val httpClient by lazy {
    HttpClient(Js) {
      install(JsonFeature) {
        serializer = KotlinxSerializer()
      }
      defaultRequest {
        accept(ContentType.Application.Json)
        host = window.location.hostname
        port = window.location.port.toInt()
      }
    }
  }
}