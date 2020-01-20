package lt.petuska.hazelcast.explorer.domain.environment.target

import kotlinx.serialization.Serializable
import lt.petuska.hazelcast.explorer.domain.common.DTO
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.MapDTO
import lt.petuska.hazelcast.explorer.domain.environment.target.entity.TopicDTO

@Serializable
data class TargetDTO(
  val environment: String,
  val name: String,
  val displayName: String,
  val readOnly: Boolean,
  val maps: List<MapDTO>?,
  val topics: List<TopicDTO>?
) : DTO() {
  fun idString() = "[$environment][$name]"
}