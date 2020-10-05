package lt.petuska.hazelcast.explorer.domain

import kotlinx.serialization.Serializable

@Serializable
public data class HzeConfig(
  val title: String,
  val environments: List<Environment>
) {
  val version: String = "1.0.0"
}
