package lt.petuska.hazelcast.explorer.domain.enumerator

import lt.petuska.hazelcast.explorer.domain.Target
import lt.petuska.hazelcast.explorer.domain.entity.Map

internal enum class HttpMethod {
  GET,
  POST,
  PUT,
  DELETE;

  fun isValid(target: Target?, map: Map?) = (this == GET) || !(target?.readOnly ?: false || map?.readOnly ?: false)

  companion object {
    val Default = GET
  }
}
