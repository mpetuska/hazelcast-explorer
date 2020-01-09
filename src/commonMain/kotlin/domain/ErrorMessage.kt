package lt.petuska.hazelcast.explorer.domain

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(val status: Int, val message: String? = null) {
  constructor(status: HttpStatusCode, message: String? = null) : this(status.value, message)
}