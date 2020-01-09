package lt.petuska.hazelcast.explorer.configuration.application

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import lt.petuska.hazelcast.explorer.domain.*

fun Application.setupFeatures() {
  install(Compression) {
    gzip()
  }
  install(DefaultHeaders)
  install(ContentNegotiation) {
    gson {}
  }
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
      call.respond(HttpStatusCode.InternalServerError, ErrorMessage(HttpStatusCode.InternalServerError, "Internal Server Error"))
    }
  }
}