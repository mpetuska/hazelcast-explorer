package lt.petuska.hazelcast.explorer.configuration.application

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.BadRequestException
import io.ktor.features.Compression
import io.ktor.features.ContentNegotiation
import io.ktor.features.ContentTransformationException
import io.ktor.features.DefaultHeaders
import io.ktor.features.NotFoundException
import io.ktor.features.StatusPages
import io.ktor.features.gzip
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.websocket.WebSockets
import lt.petuska.hazelcast.explorer.domain.ErrorMessage

internal fun Application.setupFeatures() {
  install(Compression) {
    gzip()
  }
  install(DefaultHeaders)
  install(ContentNegotiation) {
    gson()
  }
  install(WebSockets)
  install(StatusPages) {
    exception<ContentTransformationException> { cause ->
      throw BadRequestException(cause.message ?: "Invalid request body", cause.cause)
    }
    exception<BadRequestException> { cause ->
      call.respond(HttpStatusCode.BadRequest, ErrorMessage(HttpStatusCode.BadRequest, cause.message))
    }
    exception<NotFoundException> { cause ->
      call.respond(HttpStatusCode.NotFound, ErrorMessage(HttpStatusCode.NotFound, cause.message))
    }
    exception<Exception> {
      it.printStackTrace()
      call.respond(
        HttpStatusCode.InternalServerError,
        ErrorMessage(HttpStatusCode.InternalServerError, "Internal Server Error")
      )
    }
  }
}