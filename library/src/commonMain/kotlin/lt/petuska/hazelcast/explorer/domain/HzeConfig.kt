package lt.petuska.hazelcast.explorer.domain

import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.domain.environment.Environment

@Serializable
data class HzeConfig(
  val title: String,
  val environments: List<Environment>
)