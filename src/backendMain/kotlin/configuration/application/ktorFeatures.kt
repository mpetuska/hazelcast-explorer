package lt.petuska.hazelcast.explorer.configuration.application

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.serialization.*
import kotlinx.serialization.json.*

fun Application.setupFeatures() {
  install(Compression) {
    gzip()
  }
  install(DefaultHeaders)
  install(ContentNegotiation) {
    serialization(ContentType.Application.Json, json = Json.nonstrict)
  }
  install(StatusPages) {
    exception<ContentTransformationException> { cause ->
      throw BadRequestException(cause.message ?: "Invalid request body", cause.cause)
    }
    exception<Exception> {
      call.respond(HttpStatusCode.InternalServerError)
    }
  }
}