package lt.petuska.hazelcast.explorer.domain

import kotlinx.serialization.Serializable

@Serializable
public data class ErrorMessage(val status: Int, val message: String? = null) {
  public companion object {
    public operator fun invoke(status: Int, message: String? = null): ErrorMessage = ErrorMessage(status, message)
  }
}
