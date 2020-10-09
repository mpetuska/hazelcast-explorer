package lt.petuska.hazelcast.explorer.domain

import kotlinx.serialization.Serializable

@Serializable
public data class Environment(
  val id: String,
  val name: String,
  val displayName: String,
  val readOnly: Boolean,
  val targets: List<Target>
)
