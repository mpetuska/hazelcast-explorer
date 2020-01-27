package lt.petuska.hazelcast.explorer.domain.environment

import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.domain.environment.target.Target

@Serializable
data class Environment(
  val id: String,
  val name: String,
  val displayName: String,
  val readOnly: Boolean,
  val targets: List<Target>
)