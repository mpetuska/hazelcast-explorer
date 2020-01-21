package lt.petuska.hazelcast.explorer.domain.environment

import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.domain.common.DTO
import lt.petuska.hazelcast.explorer.domain.environment.target.TargetDTO

@Serializable
data class EnvironmentDTO(
  val name: String,
  val displayName: String,
  val readOnly: Boolean,
  val local: Boolean,
  val production: Boolean,
  val targets: List<TargetDTO>
) : DTO() {
  fun idString() = "[$name]"
}